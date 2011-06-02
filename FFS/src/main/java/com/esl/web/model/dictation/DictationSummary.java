package com.esl.web.model.dictation;

import java.io.Serializable;

import com.esl.entity.dictation.*;
import com.esl.model.*;

/**
 * Use for member summary page
 */
public class DictationSummary implements Serializable {	
	private static final long serialVersionUID = 6150890243727577112L;
	
	private int dictationCreated;
	private int totalAttempted;
	private Dictation mostAttemptedDictation;
	private Dictation highestRatingDictation;
	private DictationHistory lastHistory;
	
	public int getDictationCreated() {
		return dictationCreated;
	}
	public void setDictationCreated(int dictationCreated) {
		this.dictationCreated = dictationCreated;
	}
	public int getTotalAttempted() {
		return totalAttempted;
	}
	public void setTotalAttempted(int totalAttempted) {
		this.totalAttempted = totalAttempted;
	}
	public Dictation getMostAttemptedDictation() {
		return mostAttemptedDictation;
	}
	public void setMostAttemptedDictation(Dictation mostAttemptedDictation) {
		this.mostAttemptedDictation = mostAttemptedDictation;
	}
	public Dictation getHighestRatingDictation() {
		return highestRatingDictation;
	}
	public void setHighestRatingDictation(Dictation highestRatingDictation) {
		this.highestRatingDictation = highestRatingDictation;
	}
	public DictationHistory getLastHistory() {
		return lastHistory;
	}
	public void setLastHistory(DictationHistory lastHistory) {
		this.lastHistory = lastHistory;
	}
	
	
	
}
