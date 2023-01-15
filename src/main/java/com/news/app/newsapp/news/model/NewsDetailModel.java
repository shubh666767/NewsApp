package com.news.app.newsapp.news.model;

import java.util.List;


public class NewsDetailModel {

	private String id;
	

	private String by;

	private String descendants;
	

	private List<Integer> kids;
	
	
	private int score;
	

	private long time;
	
	
	private String title;
	

	private String type;
	
	
	private String text;
	
	
	private String url;
	
	public String getBy() {
		return by;
	}
	public void setBy(String by) {
		this.by = by;
	}
	public String getDescendants() {
		return descendants;
	}
	public void setDescendants(String descendants) {
		this.descendants = descendants;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<Integer> getKids() {
		return kids;
	}
	public void setKids(List<Integer> kids) {
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
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	@Override
	public String toString() {
		return "NewsDetails [id=" + id + ", by=" + by + ", descendants=" + descendants + ", kids=" + kids + ", score="
				+ score + ", time=" + time + ", title=" + title + ", type=" + type + ", text=" + text + ", url=" + url
				+ "]";
	}
	
	
	

}
