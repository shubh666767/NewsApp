package com.news.app.newsapp.news.model;

public class TextCommentModel {

	private String comment;
	private String by;

	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getBy() {
		return by;
	}
	public void setBy(String by) {
		this.by = by;
	}
	@Override
	public String toString() {
		return "TextCommentModel [comment=" + comment + ", by=" + by + "]";
	}
	
	
	
	
	
	
}
