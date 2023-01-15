package com.news.app.newsapp.news.model;

public class ResponseModel {

	private String title;
	
	private String url;
	
	private int score;
	
	private long time;
	
	private String by;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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

	public String getBy() {
		return by;
	}

	public void setBy(String by) {
		this.by = by;
	}

	@Override
	public String toString() {
		return "ResponseModel [title=" + title + ", url=" + url + ", score=" + score + ", time=" + time + ", by=" + by
				+ "]";
	}
	
	
}
