package com.esl.web.model;

import java.io.Serializable;
import java.util.Locale;

import javax.faces.context.FacesContext;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.esl.model.Member;

@Controller
@Scope("session")
public class UserSession implements Serializable {

	private Member member;
	private boolean showGoogleImage = true;
	private Locale locale = null;

	public UserSession() {}

	public Member getMember() {return member;}
	public void setMember(Member member) {this.member = member;}

	public Locale getLocale() {return locale;}
	public void setLocale(Locale locale) {this.locale = locale;}
	
	public boolean isShowGoogleImage() {return showGoogleImage;}
	public void setShowGoogleImage(boolean showGoogleImage) {this.showGoogleImage = showGoogleImage;}

	public boolean isLogined() {
		return (member != null);
	}

	public String getLocaleString() {
		return (locale==null) ? FacesContext.getCurrentInstance().getViewRoot().getLocale().toString() : locale.toString();
	}

	public String stopShowGoogleImage() {
		showGoogleImage = false;
		return "";
	}
	
	public String resumeShowGoogleImage() {
		showGoogleImage = true;
		return "";
	}
}
