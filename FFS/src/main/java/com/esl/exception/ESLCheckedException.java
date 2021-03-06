package com.esl.exception;

import org.apache.log4j.Logger;

public class ESLCheckedException extends Exception {
	private static Logger logger = Logger.getLogger("ESLException");
	
	protected String errorCode;
	
	public ESLCheckedException() {
		super();
		logger.warn("unknown exception created");		
	}
	
	public ESLCheckedException(String errorCode) {
		this(errorCode, "ESLCheckedException");
	}
	
	public ESLCheckedException(String errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
		logger.warn("exception[" + errorCode + "] created");
	}

	public String getErrorCode() {	return errorCode;	}
	public void setErrorCode(String errorCode) {this.errorCode = errorCode;	}
	
}
