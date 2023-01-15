package com.news.app.newsapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication()
@EnableCaching
@EnableScheduling
public class NewsAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(NewsAppApplication.class, args);
	}
	

	  @Bean
	    public CacheManager cacheManager() {
	        return new ConcurrentMapCacheManager("newsDetailList");
	    }

}
