package com.esl.web.model;

import java.io.Serializable;
import java.util.Locale;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.esl.model.Member;

@Controller
@Scope("session")
public class UserSession implements Serializable {

	private Member member;
	private boolean showView = false;
	private Locale locale = null;

	public UserSession() {}

	public Member getMember() {return member;}
	public void setMember(Member member) {this.member = member;}

	public Locale getLocale() {return locale;}
	public void setLocale(Locale locale) {this.locale = locale;}

	public void setShowView(boolean show) { this.showView = show; }
	public String getShowView() throws Throwable {
		if (!showView) {
			Logger.getLogger("ESL").warn("getShowView: Invalid direct URL access");
			throw new Exception("Invalid direct URL access!");
		}
		showView = false;
		return "";
	}

	public boolean isLogined() {
		return (member != null);
	}

	public String getLocaleString() {
		return (locale==null) ? FacesContext.getCurrentInstance().getViewRoot().getLocale().toString() : locale.toString();
	}


}
