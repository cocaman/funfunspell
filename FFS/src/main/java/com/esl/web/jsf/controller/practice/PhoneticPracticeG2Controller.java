package com.esl.web.jsf.controller.practice;

import java.util.*;

import javax.annotation.Resource;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.esl.dao.*;
import com.esl.exception.ESLSystemException;
import com.esl.model.*;
import com.esl.service.practice.IPhoneticPracticeService;
import com.esl.service.practice.ITopResultService;
import com.esl.web.jsf.controller.ESLController;
import com.esl.web.jsf.controller.member.MemberWordController;
import com.esl.web.model.practice.PhoneticQuestionHistory;
import com.esl.web.model.practice.ScoreBar;
import com.esl.web.util.LanguageUtil;

@Controller
@Scope("session")
public class PhoneticPracticeG2Controller extends ESLController {
	public static int MAX_HISTORY = 10;
	public static int SCOREBAR_FULLLENGTH = 500;

	private static Logger logger = Logger.getLogger("ESL");
	private final String bundleName = "messages.practice.PhoneticPractice";
	private String practiceView = "/practice/phoneticpracticeG2/practice";
	private String resultView = "/practice/phoneticpracticeG2/result";

	// UI Data
	private String answer = "";
	private TopResult scoreRanking;
	private TopResult rateRanking;
	private PracticeResult currentGradeResult;
	private PracticeResult allGradeResult;
	private Grade currentGrade;
	private boolean isLevelUp = false;
	private boolean topLevel = false;
	private PhoneticQuestion question;
	private List<PhoneticQuestionHistory> history;
	private int totalMark;
	private int totalFullMark;
	private ScoreBar scoreBar;

	// Supporting classes
	@Resource private IGradeDAO gradeDAO;
	@Resource private IMemberDAO memberDAO;
	@Resource private IPhoneticPracticeService phoneticPracticeService;
	@Resource private IPracticeResultDAO practiceResultDAO;
	@Resource private ITopResultService topResultService;
	@Resource private IPhoneticQuestionDAO phoneticQuestionDAO;
	@Resource private PhoneticPracticeController phoneticPracticeController;
	@Resource private MemberWordController memberWordController;

	// ============== Constructor ================//
	public PhoneticPracticeG2Controller() {
		totalFullMark = 1;
		history = new ArrayList<PhoneticQuestionHistory>();
		scoreBar = new ScoreBar();
		scoreBar.setFullLength(SCOREBAR_FULLLENGTH);
	}

	// ============== Setter / Getter ================//
	@Value("${PhoneticPracticeG2.MaxHistory}") public void setMaxHistory(int max) {this.MAX_HISTORY = max; }

	public void setGradeDAO(IGradeDAO gradeDAO) {this.gradeDAO = gradeDAO;}
	public void setMemberDAO(IMemberDAO memberDAO) {this.memberDAO = memberDAO; }
	public void setPhoneticPracticeService(IPhoneticPracticeService phoneticPracticeService) {this.phoneticPracticeService = phoneticPracticeService;}
	public void setPracticeResultDAO(IPracticeResultDAO practiceResultDAO) {this.practiceResultDAO = practiceResultDAO;	}
	public void setTopResultService(ITopResultService topResultService) {this.topResultService = topResultService; }
	public void setPhoneticQuestionDAO(IPhoneticQuestionDAO phoneticQuestionDAO) {this.phoneticQuestionDAO = phoneticQuestionDAO; }
	public void setPhoneticPracticeController(PhoneticPracticeController controller) {this.phoneticPracticeController = controller;}
	public void setScoreBarFullLength(int length) {this.SCOREBAR_FULLLENGTH = length; }
	public void setMemberWordController(MemberWordController memberWordController) {this.memberWordController = memberWordController; }

	public String getAnswer() {	return answer;	}
	public void setAnswer(String answer) {this.answer = answer;}

	public boolean isLevelUp() {return isLevelUp;}
	public void setLevelUp(boolean isLevelUp) {	this.isLevelUp = isLevelUp;}

	public TopResult getRateRanking() {	return rateRanking;}
	public void setRateRanking(TopResult rateRanking) {	this.rateRanking = rateRanking;	}

	public TopResult getScoreRanking() {return scoreRanking;}
	public void setScoreRanking(TopResult scoreRanking) {this.scoreRanking = scoreRanking;	}

	public PracticeResult getCurrentGradeResult() {	return currentGradeResult;	}
	public void setCurrentGradeResult(PracticeResult currentGradeResult) {	this.currentGradeResult = currentGradeResult;}

	public PhoneticQuestion getQuestion() {	return question;}
	public void setQuestion(PhoneticQuestion question) {this.question = question;}

	public Grade getCurrentGrade() {return currentGrade;}
	public void setCurrentGrade(Grade currentGrade) {this.currentGrade = currentGrade;}

	public int getTotalMark() {	return totalMark;}
	public void setTotalMark(int totalMark) {this.totalMark = totalMark;}

	public int getTotalFullMark() {	return totalFullMark;}
	public void setTotalFullMark(int totalFullMark) {this.totalFullMark = totalFullMark;}

	public List<PhoneticQuestionHistory> getHistory() {	return history;	}
	public void setHistory(List<PhoneticQuestionHistory> history) {	this.history = history;	}
	public int getHistorySize() { return history.size(); }

	public boolean isTopLevel() {return topLevel;}
	public void setTopLevel(boolean topLevel) {this.topLevel = topLevel;}

	public ScoreBar getScoreBar() {	return scoreBar;}
	public void setScoreBar(ScoreBar scoreBar) {this.scoreBar = scoreBar;}

	//	 ============== Getter Functions ================//
	/**
	 * Use for jsp, To refresh all UI string to new language in result.jsp
	 */
	public String getInitResultLanguage() {
		logger.info("getInitResultLanguage: START");
		Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
		logger.info("getInitResultLanguage: Format obj for :" + locale);

		LanguageUtil.formatGradeDescription(currentGrade, locale).getDescription();
		if (userSession.getMember() != null) LanguageUtil.formatGradeDescription(userSession.getMember().getGrade(), locale);
		return "";
	}

	// ============== Functions ================//
	public String start() {
		logger.info("start: selectedGrade: " + phoneticPracticeController.getSelectedGrade());

		// clear all existing objects
		clearController();

		// get selected grade
		currentGrade = gradeDAO.getGradeByTitle(phoneticPracticeController.getSelectedGrade());
		if (currentGrade == null) return errorView;

		// get practice result
		currentGradeResult = practiceResultDAO.getPracticeResult(userSession.getMember(), currentGrade, PracticeResult.PHONETICPRACTICE);
		allGradeResult = practiceResultDAO.getPracticeResult(userSession.getMember(), null, PracticeResult.PHONETICPRACTICE);
		if (currentGradeResult == null) {
			// create a new result if not exist
			logger.warn("start: practice result not found, create a new one.");
			currentGradeResult = new PracticeResult(userSession.getMember(), currentGrade, PracticeResult.PHONETICPRACTICE);
			practiceResultDAO.makePersistent(currentGradeResult);
		}
		topLevel = currentGrade.equals(userSession.getMember().getGrade());

		// get a random question
		getRandomQuestion();

		// set scoreBar
		if (topLevel)
			setScoreBar(0, currentGradeResult.getMark());
		else
			setScoreBar(0, 0);

		return practiceView;
	}

	public String submitAnswer() {
		FacesContext facesContext = FacesContext.getCurrentInstance();

		// Check practice have been create or not, if not created, call start
		if (currentGrade == null) {
			logger.info("submitAnswer: cannot find current grade");
			return start();
		}

		// Check answer
		logger.info("submitAnswer: word[" + question.getWord() + "], answer[" + answer + "]");
		int mark = 0;
		PhoneticQuestionHistory questionG2 = new PhoneticQuestionHistory();
		questionG2.setAnswer(answer);
		questionG2.setQuestion(question);
		if (answer.trim().toLowerCase().equals(question.getWord().toLowerCase())) {
			mark = 1;
			questionG2.setCorrect(true);
		}
		totalMark += mark;
		totalFullMark += 1;
		answer = "";			// Clear answer field
		history.add(0, questionG2);
		if (history.size() > MAX_HISTORY) history.remove(history.size() - 1);		// remove too many history

		// update practice result
		logger.info("submitAnswer: update practice result");
		currentGradeResult.setMark(currentGradeResult.getMark() + mark);
		allGradeResult.setMark(allGradeResult.getMark() + mark);
		practiceResultDAO.makePersistent(currentGradeResult);
		practiceResultDAO.makePersistent(allGradeResult);

		// update scoreCard
		if (userSession.getMember() != null && mark > 0) {
			phoneticPracticeService.updateScoreCard(userSession.getMember(), new java.sql.Date((new Date()).getTime()), true, question);
		}

		// Check isLevelup
		if (topLevel && currentGrade.equals(userSession.getMember().getGrade()) && currentGradeResult.getMark() >= currentGrade.getPhoneticPracticeLvUpRequire()) {
			Grade upperGrade = gradeDAO.getGradeByLevel(currentGrade.getLevel() + 1);
			userSession.setMember(memberDAO.getMemberById(userSession.getMember().getId()));
			logger.info("submitAnswer: LEVEL_UP: new grade:" + upperGrade);
			if (upperGrade != null) {
				userSession.getMember().setGrade(upperGrade);
				memberDAO.makePersistent(userSession.getMember());
				isLevelUp = true;
				logger.info("submitAnswer: Member[" + userSession.getMember().getUserId() + "] level up to grade[" + userSession.getMember().getGrade() + "]");
			}
			isLevelUp = true;
			return completePractice();
		}

		getRandomQuestion();
		if (topLevel)
			setScoreBar(currentGradeResult.getMark()-mark, currentGradeResult.getMark());
		else
			setScoreBar(totalMark - mark, totalMark);


		return practiceView;
	}


	public String submitAndEnd() {
		FacesContext facesContext = FacesContext.getCurrentInstance();

		// Check answer
		logger.info("submitAnswer: word[" + question.getWord() + "], answer[" + answer + "]");
		int mark = 0;
		PhoneticQuestionHistory questionG2 = new PhoneticQuestionHistory();
		questionG2.setAnswer(answer);
		questionG2.setQuestion(question);
		if (answer.trim().toLowerCase().equals(question.getWord().toLowerCase())) {
			mark = 1;
			questionG2.setCorrect(true);
		}
		totalMark += mark;
		answer = "";			// Clear answer field
		history.add(0, questionG2);
		if (history.size() > MAX_HISTORY) history.remove(history.size() - 1);		// remove too many history

		// update practice result
		logger.info("submitAnswer: update practice result");
		currentGradeResult.setMark(currentGradeResult.getMark() + mark);
		allGradeResult.setMark(allGradeResult.getMark() + mark);
		practiceResultDAO.makePersistent(currentGradeResult);
		practiceResultDAO.makePersistent(allGradeResult);

		// update scoreCard
		if (userSession.getMember() != null && mark > 0) {
			phoneticPracticeService.updateScoreCard(userSession.getMember(), new java.sql.Date((new Date()).getTime()), true, question);
		}

		// Check isLevelup
		if (topLevel && currentGrade.equals(userSession.getMember().getGrade()) && currentGradeResult.getMark() >= currentGrade.getPhoneticPracticeLvUpRequire()) {
			Grade upperGrade = gradeDAO.getGradeByLevel(currentGrade.getLevel() + 1);
			userSession.setMember(memberDAO.getMemberById(userSession.getMember().getId()));
			logger.info("submitAnswer: LEVEL_UP: new grade:" + upperGrade);
			if (upperGrade != null) {
				userSession.getMember().setGrade(upperGrade);
				memberDAO.makePersistent(userSession.getMember());
				isLevelUp = true;
				logger.info("submitAnswer: Member[" + userSession.getMember().getUserId() + "] level up to grade[" + userSession.getMember().getGrade() + "]");
			}
			isLevelUp = true;
		}
		return completePractice();
	}

	// process when completing the practice
	public String completePractice() {
		logger.info("completePractice: START");

		Member member = userSession.getMember();
		// retrieve ranking of the practiced grade
		scoreRanking = topResultService.getResultListByMemberGrade(TopResult.OrderType.Score, PracticeResult.PHONETICPRACTICE, member, currentGrade);
		rateRanking = topResultService.getResultListByMemberGrade(TopResult.OrderType.Rate, PracticeResult.PHONETICPRACTICE, member, currentGrade);

		if (topLevel)
			setScoreBar(0, currentGradeResult.getMark());
		else
			setScoreBar(0, totalMark);

		return resultView;
	}

	// ============== Supporting Functions ================//
	private void clearController() {
		answer = "";
		history.clear();
		isLevelUp = false;
		totalFullMark = 1;
		totalMark = 0;
		memberWordController.setSavedQuestion(new HashMap<PhoneticQuestion, Boolean>());
	}

	private void getRandomQuestion() {
		List<PhoneticQuestion> questions = phoneticQuestionDAO.getRandomQuestionsByGrade(currentGrade, 1, true);
		if (questions == null || questions.size() < 1) {throw new ESLSystemException("getRandomQuestion: cannot get any question","getRandomQuestion: cannot get any question");}
		question = questions.get(0);
		logger.info("getRandomQuestion: a random question: word[" + question.getWord() + "]");

		// add full mark in practice result
		currentGradeResult.setFullMark(currentGradeResult.getFullMark() + 1);
		allGradeResult.setFullMark(allGradeResult.getFullMark() + 1);
		practiceResultDAO.makePersistent(currentGradeResult);
		practiceResultDAO.makePersistent(allGradeResult);
		logger.info("getRandomQuestion: add full mark for practice result by 1");

		// set member word unsaved in controller map
		memberWordController.getSavedQuestion().put(question, false);
	}

	private void setScoreBar(int startIdx, int endIdx) {
		int base;
		if (topLevel)
			base = currentGrade.getPhoneticPracticeLvUpRequire();
		else
			base = totalFullMark;
		int startLength = (int) ((double)startIdx / (double)base * SCOREBAR_FULLLENGTH);
		int endLength = (int) ((double)endIdx / (double)base * SCOREBAR_FULLLENGTH);
		if (startLength < 0) startLength = 0;
		scoreBar.setStartLength(startLength);
		scoreBar.setEndLength(endLength);
		logger.info("setScoreBar: startLength[" + startLength + "], endLength[" + endLength + "]");
	}
}

