package com.news.app.newsapp.news.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.news.app.newsapp.news.exception.ServiceException;
import com.news.app.newsapp.news.model.NewsDetailModel;
import com.news.app.newsapp.news.model.NewsDetails;
import com.news.app.newsapp.news.model.PrimaryKeyAndComment;
import com.news.app.newsapp.news.model.ResponseModel;
import com.news.app.newsapp.news.service.ControllerException;
import com.news.app.newsapp.news.service.FailureException;
import com.news.app.newsapp.news.service.NewsAppService;

@RestController
public class NewsController {
	
	@Autowired
	private NewsAppService newsAppService;
	

	@GetMapping(value="/top-stories")
	private List<NewsDetailModel> shopping() {
		try {
		return newsAppService.getTopStories();
		}catch(FailureException e) {
			throw new ControllerException("There was some issue while execution of top stories");
		}
	}
	
	
	@GetMapping(value="/past-stories")
	private List<ResponseModel> pastStories() {	
		try {
		return newsAppService.getPastStories();
		}catch(FailureException e) {
			throw new ControllerException("There was some issue while execution of past stories");
		}
		
	}
	
	@GetMapping(value="/comments")
	private List<PrimaryKeyAndComment> getComments() {	
		try {
		return newsAppService.getComments();
		}catch(FailureException e) {
			throw new ControllerException("There was some issue while execution of getComments");
		}
		
	}


}
