package com.esl.web.jsf.controller.dictation;

import java.util.*;

import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.esl.dao.IMemberDAO;
import com.esl.dao.dictation.IDictationDAO;
import com.esl.entity.dictation.Dictation;
import com.esl.exception.BusinessValidationException;
import com.esl.model.group.MemberGroup;
import com.esl.service.dictation.IDictationManageService;
import com.esl.web.jsf.controller.ESLController;
import com.esl.web.util.SelectItemUtil;

@Controller
@Scope("session")
public class DictationEditController extends ESLController {
	private static Logger logger = Logger.getLogger("ESL");
	private static String bundleName = "messages.member.Dictation";
	private static String editView = "/member/dictation/edit";
	private static String successView = "/member/dictation/edit2";
	private static final String selfDictationInputView = "/practice/selfdictation/input";

	//	 Supporting instance
	@Resource private IDictationManageService manageService;
	@Resource private IMemberDAO memberDAO;
	@Resource private IDictationDAO dictationDAO;

	//	 ============== UI display data ================//
	private Dictation editDictation;
	private List<SelectItem> accessibleMemberGroups;
	private List<String> selectedGroups = new ArrayList<String>();
	private String vocabs;
	private boolean requirePassword = false;
	private String password;
	private String confirmedPassword;

	//============== Functions ================//

	/**
	 * Open create new dictation page
	 */
	public String launchCreate() {
		final String logTitle = "launchCreate: ";
		logger.info(logTitle + "START");

		if (userSession.getMember() == null) {
			logger.info(logTitle + "Member is null, direct to self dictation input page");
			FacesContext facesContext = FacesContext.getCurrentInstance();
			ResourceBundle bundle = ResourceBundle.getBundle(bundleName, facesContext.getViewRoot().getLocale());
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("signUp"), null));
			return selfDictationInputView;
		}

		editDictation = new Dictation();
		prepareDisplayObjects();
		return editView;
	}

	/**
	 * Open edit dictation page
	 */
	public String launchEdit() {
		final String logTitle = "launchEdit: ";
		logger.info(logTitle + "START");
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ResourceBundle bundle = ResourceBundle.getBundle(bundleName, facesContext.getViewRoot().getLocale());

		if (editDictation == null) return errorView;
		if (!manageService.allowEdit(editDictation, userSession.getMember())) {
			logger.info(logTitle + "[" + userSession.getMember() + "] cannot edit dictation created by [" + editDictation.getCreator().getUserId() + "]");
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("notAllowEdit"), null));
			return null;
		}
		dictationDAO.attachSession(editDictation);
		prepareDisplayObjects();
		vocabs = editDictation.getVocabsString();
		requirePassword = editDictation.isRequirePassword();
		return editView;
	}

	/**
	 * Create / edit form submit
	 */
	public String submit() {
		final String logTitle = "submit: ";
		logger.info(logTitle + "START");
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ResourceBundle bundle = ResourceBundle.getBundle(bundleName, facesContext.getViewRoot().getLocale());

		// Check PIN with confirmed PIN
		if (requirePassword) {
			if (confirmedPassword == null || password == null || !confirmedPassword.equals(password)) {
				logger.info(logTitle + "input PIN different");
				facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("pwdNotMatch"), null));
				return null;
			}
		}

		setAccessibleGroups();

		// set password
		if (requirePassword) {
			if (password != null && !password.equals("")) editDictation.setPassword(password);
		}
		else
			editDictation.setPassword("");

		editDictation.setCreator(userSession.getMember());
		editDictation.setLastModifyDate(new Date());

		try {
			// set vocabs into dictation
			manageService.setVocabs(editDictation, vocabs);
			manageService.saveDictation(editDictation);
			logger.info(logTitle + "Total vocab added [" + editDictation.getVocabs().size() + "]");
			logger.info(logTitle + "Total accesible group [" + editDictation.getAccessibleGroups().size() + "]");
			return successView;
		} catch (BusinessValidationException e) {
			logger.info(logTitle + "BV Exception:" + e.getMessage());
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString(e.getErrorCode()), null));
			return null;
		}
	}

	//	============== Getter Function ================//

	// Return all grades available
	public List<SelectItem> getAvailableAgeGroups() {
		return SelectItemUtil.getAvailableAgeGroups();
	}

	/**
	 * Getter return max allowed words
	 */
	public int getMaxVocabs() { return manageService.getMaxVocabs();}

	/**
	 * Getter return the separator of words
	 */
	public String getSeparator() {
		return Dictation.SEPARATOR;
	}

	//	============== Supporting Function ================//
	private void prepareDisplayObjects() {
		memberDAO.attachSession(userSession.getMember());
		accessibleMemberGroups = SelectItemUtil.getAvailableMemberGroups(userSession.getMember().getGroups());
		selectedGroups.clear();
		for (MemberGroup g : editDictation.getAccessibleGroups()) {
			selectedGroups.add(g.getId().toString());
		}
	}

	private void setAccessibleGroups() {
		final String logTitle = "setAccessibleGroups: ";
		logger.info(logTitle + "START");

		List<MemberGroup> groups = userSession.getMember().getGroups();
		editDictation.getAccessibleGroups().clear();
		for (String s : selectedGroups) {
			logger.info(logTitle + "get string [" + s + "]");
			for (MemberGroup g : groups) {
				if (g.getId().toString().equals(s)) {
					editDictation.addAccessibleGroup(g);
				}
			}
		}
	}

	//	 ============== Setter / Getter ================//
	public void setManageService(IDictationManageService manageService) {this.manageService = manageService;}
	public void setMemberDAO(IMemberDAO memberDAO) {this.memberDAO = memberDAO;}
	public void setDictationDAO(IDictationDAO dictationDAO) {this.dictationDAO = dictationDAO;}

	public Dictation getEditDictation() {return editDictation;}
	public void setEditDictation(Dictation editDictation) {this.editDictation = editDictation;}

	public List<SelectItem> getAccessibleMemberGroups() { return accessibleMemberGroups; }
	public void setAccessibleMemberGroups(List<SelectItem> accessibleMemberGroups) {this.accessibleMemberGroups = accessibleMemberGroups;}

	public String getVocabs() {	return vocabs;}
	public void setVocabs(String vocabs) {	this.vocabs = vocabs;}

	public List<String> getSelectedGroups() {return selectedGroups;}
	public void setSelectedGroups(List<String> selectedGroups) {this.selectedGroups = selectedGroups;}

	public String getConfirmedPassword() {return confirmedPassword;}
	public void setConfirmedPassword(String confirmedPassword) {this.confirmedPassword = confirmedPassword;}

	public boolean isRequirePassword() {return requirePassword;}
	public void setRequirePassword(boolean requirePassword) {this.requirePassword = requirePassword;}

	public String getPassword() {return password;}
	public void setPassword(String password) {this.password = password;}


}
