package com.news.app.newsapp.news.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.news.app.newsapp.news.model.NewsDetails;




@Service
public class NewsAppService {
	
	private static final Logger logger = LoggerFactory.getLogger(NewsAppService.class);

	
	private String rootUrl= "https://hacker-news.firebaseio.com/v0/";
	
	@Cacheable(value="newsDetailList")
	public List<NewsDetails> getTopStories() {
		String topStoriesUrl = rootUrl + "topstories.json?print=pretty";
		RestTemplate restTemplate = new RestTemplate();
	    ResponseEntity<Integer[]> result = restTemplate.getForEntity(topStoriesUrl,Integer[].class);
	    Integer [] itemIds = result.getBody();
	    return getItemsWithItemIds(itemIds);

	    
		
	}
	
	
	private List<NewsDetails> getItemsWithItemIds(Integer[] itemIds) {
		RestTemplate restTemplate = new RestTemplate();
		
		List<NewsDetails> newsDetailList = new ArrayList<>();
		int i=0;
		System.out.println("starting");
		for(Integer itemId:itemIds ) {
			if(i<20) {
			String itemUrl = rootUrl + "/item/"+itemId + ".json?print=pretty";
			NewsDetails newsDetail =restTemplate.getForObject(itemUrl, NewsDetails.class);
			newsDetailList.add(newsDetail);
			System.out.println("number " +i );
			System.out.println(newsDetail);
			i++;
			}else {
				break;
			}
		}
//		for(int i=0;i<itemIds.length;i++) {
//		String itemUrl = rootUrl + "/item/"+itemIds[i] + ".json?print=pretty";
//		NewsDetails newsDetail =restTemplate.getForObject(itemUrl, NewsDetails.class);
//		System.out.println(newsDetail);
		
		Comparator<NewsDetails> comp = (a,b)->{
			if(a.getScore()>b.getScore()) {
				return 1;
			}else if(a.getScore()<b.getScore()) {
				return -1;
			}
			return 0;
		};		
		
		newsDetailList = newsDetailList.stream()
		.filter(item-> (System.currentTimeMillis() / 1000L)- item.getTime()<15*60*1000)
		.sorted(comp)
		.limit(10)
		.collect(Collectors.toList());
		
		System.out.println(newsDetailList);
//		}
		return newsDetailList;
	}		
		
	@CacheEvict(value = {"newsDetailList"}, allEntries = true)
	@Scheduled(fixedRate = 2*60*1000)
	public void emptyHotelsCache() {
	    logger.info("emptying News cache");
	}
		
		
		
		
		
		
		
		
	}





