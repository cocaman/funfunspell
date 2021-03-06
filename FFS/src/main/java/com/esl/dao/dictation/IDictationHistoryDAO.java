package com.esl.dao.dictation;

import java.util.*;

import com.esl.dao.IESLDao;
import com.esl.model.*;
import com.esl.entity.dictation.*;

public interface IDictationHistoryDAO extends IESLDao<DictationHistory> {
	public List<DictationHistory> listByDictation(Dictation dictation);
	public List<DictationHistory> listByDictation(Dictation dictation, int maxResult);
	public List<DictationHistory> listByMember(Member member, int maxResult);
	public List<DictationHistory> listAnnoymousHistoryByDictation(Dictation dictation, int maxResult);
	
	public int removeByDictation(Dictation dictation);
	
	/**
	 * Get the lastest history of all the dictation created by the member
	 */
	public DictationHistory getLastestOfAllDictationByMember(Member member);
}
