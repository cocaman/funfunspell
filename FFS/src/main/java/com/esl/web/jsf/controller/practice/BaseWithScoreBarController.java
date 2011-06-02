package com.esl.web.jsf.controller.practice;

import org.apache.log4j.Logger;

import com.esl.web.jsf.controller.ESLController;
import com.esl.web.model.practice.ScoreBar;

/**
 * A base controller for all practice with scoreBar
 */
public abstract class BaseWithScoreBarController extends ESLController {
	protected static int SCOREBAR_FULLLENGTH = 500;

	private static Logger logger = Logger.getLogger("ESL");

	//	 ============== UI display data ================//
	private ScoreBar scoreBar;

	// ============== Constructor ================//
	public BaseWithScoreBarController() {
		scoreBar = new ScoreBar();
		scoreBar.setFullLength(SCOREBAR_FULLLENGTH);
	}

	//	============== Supporting Function ================//
	public void setScoreBar(int startIdx, int endIdx, int baseIdx) {
		int startLength = (int) ((double)startIdx / (double) baseIdx * SCOREBAR_FULLLENGTH);
		int endLength = (int) ((double)endIdx / (double) baseIdx * SCOREBAR_FULLLENGTH);
		if (startLength < 0) startLength = 0;
		scoreBar.setStartLength(startLength);
		scoreBar.setEndLength(endLength);

		logger.info("setScoreBar: startLength[" + startLength + "], endLength[" + endLength + "]");
	}

	//	 ============== Setter / Getter ================//
	public ScoreBar getScoreBar() {	return scoreBar;}
	public void setScoreBar(ScoreBar scoreBar) {this.scoreBar = scoreBar;}
}
