package org.mintr.entity;

import java.util.Date;



public class ForumThread {
	String url;
	String title;
	Date date;
	
	public ForumThread(String url, String title, Date date) {
		super();
		this.url = url;
		this.title = title;
		this.date = date;
	}
	
	public String getUrl() {return url;}
	public void setUrl(String url) {this.url = url;}
	
	public String getTitle() {return title;}
	public void setTitle(String title) {this.title = title;}
	
	public Date getDate() {return date;}
	public void setDate(Date date) {this.date = date;}
	
	@Override
	public String toString() {
		return String.format("ForumThread [url=%s, title=%s, date=%s]", url,
				title, date);
	}	
}
