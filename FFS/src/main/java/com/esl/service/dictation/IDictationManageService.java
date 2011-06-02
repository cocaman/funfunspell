package com.esl.service.dictation;

import java.util.*;
import com.esl.model.*;
import com.esl.entity.dictation.*;
import com.esl.model.group.MemberGroup;
import com.esl.web.model.dictation.DictationSummary;
import com.esl.web.model.practice.VocabPracticeSummary;
import com.esl.exception.BusinessValidationException;

public interface IDictationManageService {
	public List<Dictation> getDictationsByMember(Member member);
	public List<Dictation> getDictationsByGroup(MemberGroup group, int maxResult);
	public List<MemberDictationHistory> getDictationsHistoriesByMember(Member member, int maxResult);
	public List<DictationHistory> getDictationsHistoriesByDictation(Dictation dictation, int maxResult);
	public boolean saveDictation(Dictation dictation) throws BusinessValidationException;
	public boolean setVocabs(Dictation dictation, String vocabs) throws BusinessValidationException;
	public boolean allowEdit(Dictation dictation, Member user);
	public boolean allowView(Dictation dictation, Member user);
	public boolean rateDictation(Dictation dictation, int rating);
	
	/**
	 * Get the model contain data of dictation for member summary page
	 */
	public DictationSummary getDictationSummary(Member member);
	
	/**
	 * Getter return the maximum number of words allowed
	 */
	public int getMaxVocabs();
}
