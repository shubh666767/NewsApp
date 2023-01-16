package com.news.app.NewsApp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.util.Arrays;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.news.app.newsapp.news.model.NewsDetailModel;
import com.news.app.newsapp.news.service.NewsAppService;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class NewsAppServiceTest {

	@InjectMocks
	private NewsAppService newsAppService = new NewsAppService();
	@Mock
	private RestTemplate restTemplate;

	@Test
	public void getTopStories() {

//		Integer[] arrOfInt = { 2, 4, 6 };
//		Mockito.when(restTemplate.getForEntity("https://hacker-news.firebaseio.com/v0/topstories.json?print=pretty",
//				Integer[].class)).thenReturn(new ResponseEntity(arrOfInt, HttpStatus.OK));
//		List<NewsDetailModel> list = new ArrayList<>();
//		NewsDetailModel n = new NewsDetailModel();
//		n.setBy("abc");
//		n.setScore(50);
//		list.add(n);
//		Mockito.verify(newsAppService.getTopStories()).size();

	}

}
