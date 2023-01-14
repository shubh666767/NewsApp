package com.news.app.newsapp.news.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.news.app.newsapp.news.model.NewsDetails;
import com.news.app.newsapp.news.service.NewsAppService;

@RestController
public class NewsController {
	
	@Autowired
	private NewsAppService newsAppService;
	

	@GetMapping(value="/top-stories")
	private List<NewsDetails> shopping() {
		
		long unixTime = System.currentTimeMillis() / 1000L;
		System.out.println("hdhdhdh"+ unixTime);
		return newsAppService.getTopStories();
		
	}

}
