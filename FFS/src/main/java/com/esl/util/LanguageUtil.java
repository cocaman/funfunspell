package com.esl.util;



public class LanguageUtil extends ConfigurationUtil {
	// Default constructor
	public LanguageUtil(String fileName) {
		super();
		//loadDocument(fileName);
	}

	public static String getMessages(String key, String locale) {
		String message = "";
		if ("zh".equals(locale))
			message = getValueByTagName(key + "_zh");
		else if ("zh-cn".equals(locale))
			message = getValueByTagName(key + "_zh-cn");
		else
			message = getValueByTagName(key);
		return message;
	}

	public static String getPage(String key, String locale) {
		String page = key;
		if ("zh".equals(locale))
			page = key + "_zh";
		else if ("zh-cn".equals(locale))
			page = key + "_zh-cn";

		return page;
	}
}
