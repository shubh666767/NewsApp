package com.news.app.newsapp.news.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="kids_associated")
public class Kids {
	
	@Id
	@Column(name="kid_id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int kidId;
	

	@JoinColumn(name="kids")
	private Integer kid;
	
	@ManyToOne(cascade = {
			CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH,
			
	}, fetch =FetchType.LAZY)
	
	@JoinColumn(name="primary_id_kid")
	private NewsDetails newsDetails;

	public int getKidId() {
		return kidId;
	}

	public void setKidId(int kidId) {
		this.kidId = kidId;
	}

	public Integer getKid() {
		return kid;
	}

	public void setKid(Integer kid) {
		this.kid = kid;
	}

	public NewsDetails getNewsDetails() {
		return newsDetails;
	}

	public void setNewsDetails(NewsDetails newsDetails) {
		this.newsDetails = newsDetails;
	}

	@Override
	public String toString() {
		return "Kids [kidId=" + kidId + ", kid=" + kid + ", newsDetails=" + newsDetails + "]";
	}

	

	
	
	

}
