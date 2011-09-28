package com.esl.util;

public interface SourceChecker {
	/**
	 * Return true if all content found
	 */
	public boolean parse();
	
	/**
	 * Return URL of checked source
	 */
	public String getSourceLink();
	
	/**
	 * Return all content parsed
	 */
	public String getParsedContent();
}
