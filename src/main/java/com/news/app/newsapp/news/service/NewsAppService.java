package com.news.app.newsapp.news.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import com.news.app.newsapp.dao.NewsAppDao;
import com.news.app.newsapp.news.exception.ServiceException;
import com.news.app.newsapp.news.model.Kids;
import com.news.app.newsapp.news.model.NewsDetailModel;
import com.news.app.newsapp.news.model.NewsDetails;
import com.news.app.newsapp.news.model.PrimaryKeyAndComment;
import com.news.app.newsapp.news.model.ResponseModel;
import com.news.app.newsapp.news.model.TextCommentModel;

import io.micrometer.core.instrument.util.StringUtils;


@Service
@Transactional
public class NewsAppService {

	@Autowired
	private NewsAppDao newsAppDao;


	private static final Logger logger = LoggerFactory.getLogger(NewsAppService.class);


	private String rootUrl= "https://hacker-news.firebaseio.com/v0/";

	private Map<Integer, List<NewsDetailModel>> map = new HashMap<>();

	@Cacheable(value="newsDetailList")
	public List<NewsDetailModel> getTopStories() {
		try {
			String topStoriesUrl = rootUrl + "topstories.json?print=pretty";

			logger.info("top stories url being called  "+ topStoriesUrl);
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<Integer[]> result = restTemplate.getForEntity(topStoriesUrl,Integer[].class);
			Integer [] itemIds = result.getBody();
			return getItemsWithItemIds(itemIds);
		}catch(ServiceException e) {
			NewsDetailModel ndm = new NewsDetailModel();

			e.printStackTrace();
			throw new FailureException(e.getMessage());
		}


	}


	public List<NewsDetailModel> getItemsWithItemIds(Integer[] itemIds) {
		RestTemplate restTemplate = new RestTemplate();
		try {
		List<NewsDetailModel> newsDetailList = new ArrayList<>();
		
		logger.info("starting individual calls with item ids");
		for(Integer itemId:itemIds ) {
			
				String itemUrl = rootUrl + "/item/"+itemId + ".json?print=pretty";
				NewsDetailModel newsDetail =restTemplate.getForObject(itemUrl, NewsDetailModel.class);
				newsDetailList.add(newsDetail);
				//System.out.println("number " +i );
				logger.info(newsDetail.toString());
				//System.out.println(newsDetail);
				
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

		//List<Kids> kidList =new ArrayList<>();

		return newsDetailList;
		}catch(ServiceException e) {
			e.printStackTrace();
			throw new FailureException(e.getMessage());

		}
	}		

	@CacheEvict(value = {"newsDetailList"}, allEntries = true)
	@Scheduled(fixedRate = 15*60*1000)
	public void emptyHotelsCache() {
		logger.info("emptying News cache");
	}


	private void saveTopNews(List<NewsDetailModel> newsDetailList) {
		try {
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
		}catch(ServiceException e) {
			e.printStackTrace();
			throw new FailureException(e.getMessage());

		}
	}


	private void saveKids(NewsDetailModel newsDetailList, NewsDetails updatedNews) {
		try {
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
		}catch(ServiceException e ) {
			e.printStackTrace();
			throw new FailureException(e.getMessage());

		}
	}


	public List<ResponseModel> getPastStories() {
		// TODO Auto-generated method stub
		try {
     		return newsAppDao.getPastStories();
		}catch(ServiceException e) {
			throw new FailureException(e.getMessage());

		}
	}


	public List<PrimaryKeyAndComment> getComments() {
		// TODO Auto-generated method stub
		try {
		List<Integer> listOfPrimaryIds = newsAppDao.getPrimaryIdOfLastTenStories();

		for(Integer primaryInt: listOfPrimaryIds) {
			System.out.println(primaryInt);
			List<Kids> storiesModel=	newsAppDao.getComments(primaryInt);
			logger.info("=======================================================================");
			logger.info("The list of primary Ints "+ primaryInt);
			callCommentApi(storiesModel, primaryInt);
		}
		//listOfPrimaryIds.stream().forEach(e->System.out.println(newsAppDao.getComments(e).get(0).getPrimaryId()));
		//return C
		//return null;
		//return null;
		List<PrimaryKeyAndComment> pkac = new ArrayList<PrimaryKeyAndComment>();
		for(Entry<Integer, List<NewsDetailModel>> entry: map.entrySet()) {
			PrimaryKeyAndComment pk = new PrimaryKeyAndComment();
			pk.setPrimaryInt(entry.getKey());
			List<TextCommentModel> tcm = new ArrayList<TextCommentModel>();

			entry.getValue().stream().forEach(e-> {
				TextCommentModel txc = new TextCommentModel();

				txc.setBy(e.getBy());
				txc.setComment(e.getText());
				tcm.add(txc);

			});

			pk.setTextCommentModel(tcm);
			pkac.add(pk);
		}
		return pkac;
		
		
	}catch(ServiceException e) {
		e.printStackTrace();
		throw new FailureException(e.getMessage());

	}
	}

	public void callCommentApi(List<Kids> storiesModel, int primaryInt) {
		try {
			RestTemplate restTemplate = new RestTemplate();
			List<NewsDetailModel> newsDetailList = new ArrayList<>();

			storiesModel.stream().forEach(e-> {
				String itemUrl = rootUrl + "/item/"+e.getKid() + ".json?print=pretty";
				//String itemUrl = rootUrl + "/item/"+itemId + ".json?print=pretty";
				logger.info("comment url =>=>=>=>"+ itemUrl);
				NewsDetailModel newsDetail =restTemplate.getForObject(itemUrl, NewsDetailModel.class);
				newsDetailList.add(newsDetail);
				System.out.println(newsDetail);
			});


			List<NewsDetailModel> sortedList= newsDetailList.stream()
					.filter(e->e!=null && !CollectionUtils.isEmpty(e.getKids()))
					.sorted((a,b)->{
						if(a.getKids().size()>b.getKids().size()) {
							return 1;
						}else if(a.getKids().size()<b.getKids().size()) {
							return -1;
						}
						return 0;
					}	).collect(Collectors.toList());
			map.put(primaryInt, sortedList);

		}catch(ServiceException e) {
			e.printStackTrace();
			throw new FailureException(e.getMessage());

		}
	}}





