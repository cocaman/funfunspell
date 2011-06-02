package com.esl.service;

import java.util.Locale;
import java.util.ResourceBundle;

import com.esl.model.Member;
import com.esl.web.model.*;

public interface IMailService {
	// Use for all function
	public static final String CONTACT_US_SYSTEM_ERROR = "CONTACT_US_SYSTEM_ERROR";
	public static final String CONTACT_US_EMAIL_SENT = "CONTACT_US_EMAIL_SENT";
		
	// Main function
	public String contactUs(ContactUsForm form);	
	public Member forgetPassword(String userId, Locale locale);
}
