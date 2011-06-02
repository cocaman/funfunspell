package com.esl.web.jsf.controller.practice;

import java.util.*;

import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.esl.model.PhoneticPractice;
import com.esl.model.PhoneticQuestion;
import com.esl.service.practice.*;
import com.esl.web.jsf.controller.ESLController;
import com.esl.web.model.practice.ScoreBar;
import com.esl.web.util.LanguageUtil;

@Controller
@Scope("session")
public class SelfDictationController extends ESLController {
	public static int SCOREBAR_FULLLENGTH = 500;

	private static Logger logger = Logger.getLogger("ESL");
	private final String bundleName = "messages.practice.SelfDictation";
	private final String viewPrefix = "/practice/selfdictation/";
	private final String inputView = viewPrefix + "input";
	private final String practiceView = viewPrefix + "practice";
	private final String resultView = viewPrefix + "result";

	// UI Data
	private List<String> inputVocab = new ArrayList<String>(20);
	private PhoneticPractice practice;
	private String answer;
	private boolean withIPA = false;
	private boolean withRandomCharacters = false;
	private ScoreBar scoreBar;

	// Supporting classes
	@Resource private ISelfDictationService selfDictationService;
	@Resource private IPhoneticPracticeService phoneticPracticeService;

	// UI Component
	//private HtmlCommandButton practiceCommand;

	// ============== Setter / Getter ================//
	public void setSelfDictationService(ISelfDictationService selfDictationService) {this.selfDictationService = selfDictationService;}
	public void setPhoneticPracticeService(IPhoneticPracticeService phoneticPracticeService) {this.phoneticPracticeService = phoneticPracticeService;}

	public String getAnswer() {return answer;}
	public void setAnswer(String answer) {this.answer = answer;}

	public boolean isWithIPA() { return withIPA; }
	public void setWithIPA(boolean withIPA)  { this.withIPA = withIPA; }

	public boolean isWithRandomCharacters()	{return withRandomCharacters;}
	public void setWithRandomCharacters(boolean withRandomCharacters) {	this.withRandomCharacters = withRandomCharacters;}

	public PhoneticPractice getPractice() {	return practice;}
	public void setPractice(PhoneticPractice practice) {this.practice = practice;}

	public ScoreBar getScoreBar() {	return scoreBar;}
	public void setScoreBar(ScoreBar scoreBar) {this.scoreBar = scoreBar;}

	public String[] getInputVocab() {
		while (inputVocab.size()<selfDictationService.getMaxQuestions()) {
			inputVocab.add("");
		}
		return inputVocab.toArray(new String[]{});
	}
	public void setInputVocab(List<String> inputVocab) {	this.inputVocab = inputVocab;}

	public int getTotalInput() {return selfDictationService.getMaxQuestions();	}

	// ============== Constructor ================//
	public SelfDictationController() {
		scoreBar = new ScoreBar();
		scoreBar.setFullLength(SCOREBAR_FULLLENGTH);
	}


	// ============== Functions ================//

	// Generate the dictation
	public String start() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		Locale locale = facesContext.getViewRoot().getLocale();
		ResourceBundle bundle = ResourceBundle.getBundle(bundleName, locale);

		// retrieve vocabs
		inputVocab = getInputVocab((HttpServletRequest)facesContext.getExternalContext().getRequest());

		practice = selfDictationService.generatePractice(null, inputVocab, (ServletContext) facesContext.getExternalContext().getContext());

		if (practice == null || practice.getQuestions().size() <= 0)
		{
			logger.info("start: NoVocabularyFound: no question can be added");
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("NoVocabularyFound"), null));
		}
		else
		{
			// Set unavailable IPA
			for (PhoneticQuestion question : practice.getQuestions()) {
				LanguageUtil.formatIPA(question, locale);
			}

			setScoreBar(0, 0); 			// set scoreBar
			return practiceView;
		}
		return inputView;
	}

	public String submitAnswer() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ResourceBundle bundle = ResourceBundle.getBundle(bundleName, facesContext.getViewRoot().getLocale());

		// Check practice have been create or not, if not created, call start
		if (practice == null) {
			logger.warn("submitAnswer: cannot find practice");
			return inputView;
		}

		String result = phoneticPracticeService.checkAnswer(practice, answer);
		logger.info("submitAnswer: phoneticPracticeService.checkAnswer returned code: " + result);

		// update score bar
		if (IPhoneticPracticeService.CORRECT_ANSWER.equals(result))
			setScoreBar(practice.getMark()-1, practice.getMark());
		else
			setScoreBar(practice.getMark(), practice.getMark());

		answer = "";			// Clear answer field

		if (PhoneticPracticeService.INVALID_INPUT.equals(result))
			return practiceView;
		else if (PhoneticPracticeService.SYSTEM_ERROR.equals(result))
		{
			// Need to set errorPage title and description

			return errorView;
		}

		// Logic flow for practice completed
		if (practice.isFinish())
		{
			logger.info("submitAnswer: Finish Practice");
			selfDictationService.completedPractice(practice.getQuestions(), (ServletContext) facesContext.getExternalContext().getContext());
			setScoreBar(0, practice.getMark());
			return resultView;
		}

		// Continue Practice
		return practiceView;
	}

	public String retry() {
		// Check practice have been create or not, if not created, call start
		if (practice == null) {
			logger.info("retry: cannot find practice");
			return inputView;
		}

		// reset the practice
		practice.setAnswers(null);
		practice.setCorrects(null);
		practice.setMark(0);
		practice.setCurrentQuestion(0);
		logger.info("retry");
		return practiceView;
	}

	// ============== Supporting Functions ================//
	private List<String> getInputVocab(HttpServletRequest request) {
		List<String> vocabs = new ArrayList<String>();
		for (int i=0; i <= selfDictationService.getMaxQuestions(); i++) {
			String str = request.getParameter("vocab" + i);
			if (str != null && !"".equals(str)) vocabs.add(str);
		}
		logger.info("getInputVocab: returned list size[" + vocabs.size() + "]");
		return vocabs;
	}

	private void setScoreBar(int startIdx, int endIdx) {
		int startLength = (int) ((double)startIdx / (double)practice.getTotalQuestions() * SCOREBAR_FULLLENGTH);
		int endLength = (int) ((double)endIdx / (double)practice.getTotalQuestions() * SCOREBAR_FULLLENGTH);
		if (startLength < 0) startLength = 0;
		scoreBar.setStartLength(startLength);
		scoreBar.setEndLength(endLength);

		logger.info("setScoreBar: startLength[" + startLength + "], endLength[" + endLength + "]");
	}
}
