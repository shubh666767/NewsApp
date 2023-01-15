package com.news.app.newsapp.news.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="top_news")
public class NewsDetails {
	
	@Id
	@Column(name="primary_id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int primaryId;
	
	@Column(name="by_who")
	private String by;
	
	@Column(name="id")
	private String id;
	
	@Column(name="descendants")
	private String descendants;
	
	@OneToMany(cascade = {
			CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH
	}, mappedBy="newsDetails", fetch=FetchType.LAZY)
	private List<Kids> kids;
	
	@Column(name="score")
	private int score;
	
	@Column(name="time")
	private long time;
	
	@Column(name="title")
	private String title;
	
	@Column(name="type")
	private String type;
	
	@Column(name="text")
	private String text;
	
	@Column(name="url")
	private String url;

	public int getPrimaryId() {
		return primaryId;
	}

	public void setPrimaryId(int primaryId) {
		this.primaryId = primaryId;
	}

	public String getBy() {
		return by;
	}

	public void setBy(String by) {
		this.by = by;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescendants() {
		return descendants;
	}

	public void setDescendants(String descendants) {
		this.descendants = descendants;
	}

	public List<Kids> getKids() {
		return kids;
	}

	public void setKids(List<Kids> kids) {
		this.kids = kids;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "NewsDetails [primaryId=" + primaryId + ", by=" + by + ", id=" + id + ", descendants=" + descendants
				+ ", kids=" + kids + ", score=" + score + ", time=" + time + ", title=" + title + ", type=" + type
				+ ", text=" + text + ", url=" + url + "]";
	}
	

	

}
