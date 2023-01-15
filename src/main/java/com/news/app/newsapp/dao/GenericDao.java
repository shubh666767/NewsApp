package com.news.app.newsapp.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

@Repository
public class GenericDao {
	
	@PersistenceContext
	protected EntityManager entityManager;

	
	public <T> T save(Object obj) {
	T updatedObj=(T)entityManager.merge(obj);
	return updatedObj;
	}
	
	public <T> T fetchById(Class<T> clazz ,Object id) {
		T obj = entityManager.find(clazz, id);
		return obj;
	}

}
