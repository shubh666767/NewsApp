package com.news.app.newsapp.news.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.news.app.newsapp.dao.NewsAppDao;
import com.news.app.newsapp.news.model.Kids;
import com.news.app.newsapp.news.model.NewsDetailModel;
import com.news.app.newsapp.news.model.NewsDetails;
import com.news.app.newsapp.news.model.ResponseModel;

import io.micrometer.core.instrument.util.StringUtils;


@Service
@Transactional
public class NewsAppService {
	
	@Autowired
	private NewsAppDao newsAppDao;
	
	@Autowired
	private SessionFactory sessionFactory;
	
	private static final Logger logger = LoggerFactory.getLogger(NewsAppService.class);

	
	private String rootUrl= "https://hacker-news.firebaseio.com/v0/";
	
	@Cacheable(value="newsDetailList")
	public List<NewsDetailModel> getTopStories() {
		String topStoriesUrl = rootUrl + "topstories.json?print=pretty";
		
		logger.info("top stories url being called  "+ topStoriesUrl);
		RestTemplate restTemplate = new RestTemplate();
	    ResponseEntity<Integer[]> result = restTemplate.getForEntity(topStoriesUrl,Integer[].class);
	    Integer [] itemIds = result.getBody();
	    return getItemsWithItemIds(itemIds);

	    
		
	}
	
	
	private List<NewsDetailModel> getItemsWithItemIds(Integer[] itemIds) {
		RestTemplate restTemplate = new RestTemplate();
		
		List<NewsDetailModel> newsDetailList = new ArrayList<>();
		int i=0;
		logger.info("starting individual calls with item ids");
		for(Integer itemId:itemIds ) {
			if(i<20) {
			String itemUrl = rootUrl + "/item/"+itemId + ".json?print=pretty";
			NewsDetailModel newsDetail =restTemplate.getForObject(itemUrl, NewsDetailModel.class);
			newsDetailList.add(newsDetail);
			System.out.println("number " +i );
			System.out.println(newsDetail);
			i++;
			}else {
				break;
			}
		}

		
		
		newsDetailList = newsDetailList.stream()
		.filter(item-> (System.currentTimeMillis() / 1000L)- item.getTime()<15*60*1000)
		.sorted( (a,b)->{
			if(a.getScore()>b.getScore()) {
				return 1;
			}else if(a.getScore()<b.getScore()) {
				return -1;
			}
			return 0;
		}		
		)
		.limit(10)
		.collect(Collectors.toList());
		
		//newsDetailList.stream().
		
		
	    saveTopNews(newsDetailList);
			
		List<Kids> kidList =new ArrayList<>();
		
		return newsDetailList;
	}		
		
	@CacheEvict(value = {"newsDetailList"}, allEntries = true)
	@Scheduled(fixedRate = 2*60*1000)
	public void emptyHotelsCache() {
	    logger.info("emptying News cache");
	}
	
	
	private void saveTopNews(List<NewsDetailModel> newsDetailList) {
		List<NewsDetails> newsDetails = new ArrayList<>();
		for(int i=0;i<newsDetailList.size();i++) {
			NewsDetails news = new NewsDetails();
			news.setBy(newsDetailList.get(i).getBy());
			news.setDescendants(newsDetailList.get(i).getDescendants());
			news.setId(newsDetailList.get(i).getId());
			news.setScore(newsDetailList.get(i).getScore());
			news.setText(newsDetailList.get(i).getText());
			news.setTime(newsDetailList.get(i).getTime());
			news.setType(newsDetailList.get(i).getType());
			news.setUrl(newsDetailList.get(i).getUrl());
				
			logger.info("saving the top news");
			NewsDetails updatedNews = newsAppDao.save(news);
			
			logger.info("saved top news, about to save kids" );
			saveKids(newsDetailList.get(i),updatedNews);
			
	}
	}
	
		
	 private void saveKids(NewsDetailModel newsDetailList, NewsDetails updatedNews) {
		 logger.info("=======================================================================");
		 logger.info(newsDetailList.toString());
		 if(newsDetailList.getKids()!=null && !newsDetailList.getKids().isEmpty()) {
		        for(int j=0; j<newsDetailList.getKids().size();j++) {
				
				Kids kid = new Kids();
				kid.setKid(newsDetailList.getKids().get(j));
				kid.setNewsDetails(updatedNews);
				newsAppDao.save(kid);

			}
			
			
			}else {
				Kids kid = new Kids();
				kid.setNewsDetails(updatedNews);
				newsAppDao.save(kid);
				

			}
		 logger.info("kids saved......" );
		 
		}


	public List<ResponseModel> getPastStories() {
		// TODO Auto-generated method stub
		return newsAppDao.getPastStories();
	}


	public List<ResponseModel> getComments() {
		// TODO Auto-generated method stub
		return newsAppDao.getComments();
	}
		
		
		
		
		
		
		
	}





