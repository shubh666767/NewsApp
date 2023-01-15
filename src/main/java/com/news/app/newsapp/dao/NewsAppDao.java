package com.news.app.newsapp.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.news.app.newsapp.news.model.Kids;
import com.news.app.newsapp.news.model.NewsDetailModel;
import com.news.app.newsapp.news.model.NewsDetails;
import com.news.app.newsapp.news.model.ResponseModel;


@Repository
public class NewsAppDao extends GenericDao{

	@PersistenceContext
	private  EntityManager entityManager;

	public List<ResponseModel> getPastStories() {
		
		return (List<ResponseModel>)entityManager
					.createQuery("select n.title, n.url, n.score, n.time, n.by from NewsDetails n order by n.primaryId desc")
					.setMaxResults(10)
					.getResultList();
	}

	public List<Integer> getPrimaryIdOfLastTenStories(){
		return entityManager
				.createQuery("select n.primaryId from NewsDetails n order by n.primaryId desc")
				.setMaxResults(10)
				.getResultList();

	}
	
	public List<Kids> getComments(int primaryId){
		return entityManager
				.createQuery("select k from Kids k join k.newsDetails n where n.primaryId=:primaryId")
				.setParameter("primaryId", primaryId)
				.setMaxResults(10)
				.getResultList();

	}

	
	

}
