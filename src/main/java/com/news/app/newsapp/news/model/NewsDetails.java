package com.news.app.newsapp.news.model;

import java.util.List;

public class NewsDetails {
	
	private String by;
	private String descendants;
	private int id;
	private List<Integer> kids;
	private int score;
	private long time;
	private String title;
	private String type;
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
	public int getId() {
		return id;
	}
	public void setId(int id) {
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
	@Override
	public String toString() {
		return "NewsDetails [by=" + by + ", descendants=" + descendants + ", id=" + id + ", kids=" + kids + ", score="
				+ score + ", time=" + time + ", title=" + title + ", type=" + type + ", url=" + url + "]";
	}
	
	

}
