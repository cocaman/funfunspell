package com.esl.web.lifecycle;

import java.util.Locale;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.slf4j.LoggerFactory;

import com.esl.web.jsf.controller.LanguageController;
import com.esl.web.model.UserSession;
import com.sun.faces.application.view.MultiViewHandler;

public class ESLViewHandler extends MultiViewHandler {
	private static org.slf4j.Logger logger = LoggerFactory.getLogger("ESL");


	@Override
	public Locale calculateLocale(FacesContext context)
	{
		if (context.getViewRoot() != null && context.getViewRoot().getLocale() != null)
			return context.getViewRoot().getLocale();

		ExternalContext extContext = context.getExternalContext();

		Map<String, String>	paramsMap = extContext.getRequestParameterMap();
		for (String key : paramsMap.keySet()) {
			if (LanguageController.LOCALE_PARAM.equals(key)) {
				logger.debug("LOCALE_PARAM [{}]", paramsMap.get(key));
				Locale locale = new Locale(paramsMap.get(key));
				return locale;
			}
		}

		if (extContext.getSessionMap().containsKey("userSession")) {
			UserSession u = (UserSession) extContext.getSessionMap().get("userSession");
			if (u.getLocale() != null) return u.getLocale();
		}

		return super.calculateLocale(context);
	}
}
