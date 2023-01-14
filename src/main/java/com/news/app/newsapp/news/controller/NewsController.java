package com.news.app.newsapp.news.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.news.app.newsapp.news.service.NewsAppService;

@RestController
public class NewsController {
	
	private NewsAppService newsAppService;
	
	@GetMapping(value="/top-stories")
	private void shopping() {
		newsAppService.getTopStories();
		long unixTime = System.currentTimeMillis() / 1000L;
		System.out.println("hdhdhdh"+ unixTime);
		
	}

}
