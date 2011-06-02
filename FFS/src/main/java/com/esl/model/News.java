package com.esl.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

public class News implements Serializable {
	private Long id = null;
	private String title;
	private String htmlURL;
	private String shortDescription;
	private String type;
	private Date createdDate;
	private Date deadline;
	private String locale;	
	private boolean showNewImage;
		
	// ********************** Constructors ********************** //
	public News() {}
	
	// ********************** Getter Methods ********************** //
	public String getFormattedCreatedDate() {
		if (createdDate == null) return "";
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return sdf.format(createdDate);
	}
	
	// ********************** Accessor Methods ********************** //
	public Date getCreatedDate() {	return createdDate;}
	public void setCreatedDate(Date createdDate) {this.createdDate = createdDate;}

	public Date getDeadline() {return deadline;}
	public void setDeadline(Date deadline) {this.deadline = deadline;}

	public String getHtmlURL() {return htmlURL;}
	public void setHtmlURL(String htmlURL) {this.htmlURL = htmlURL;}

	public Long getId() {return id;}
	public void setId(Long id) {this.id = id;}

	public String getLocale() {	return locale;}
	public void setLocale(String locale) {this.locale = locale;}

	public String getShortDescription() {return shortDescription;}
	public void setShortDescription(String shortDescription) {this.shortDescription = shortDescription;	}

	public String getTitle() {return title;}
	public void setTitle(String title) {this.title = title;}

	public String getType() {return type;}
	public void setType(String type) {this.type = type;}
	
	public boolean isShowNewImage() {
		return showNewImage;
	}

	public void setShowNewImage(boolean showNewImage) {
		this.showNewImage = showNewImage;
	}

	// ********************** Common Methods ********************** //
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null) return false;		
		if (!(o instanceof News)) return false;

		final News n = (News) o;		
		return this.id.equals(n.getId());
	}
	
	public int hashCode()
	{
		return id==null ? System.identityHashCode(this) : id.hashCode();
	}
	
	public String toString() {
		return  "News (" + getId() + "), " +
				"title[" + getTitle() + "] " +
				"HTML URL[" + getHtmlURL() + "] " +
				"Short Description[" + getShortDescription()  + "] " +
				"Type[" + getType() + "] " +
				"Locale[" + getLocale() + "] " +
				"Created Date[" + getCreatedDate() + "] " +
				"Deadline[" + getDeadline() + "] ";
	}
}
