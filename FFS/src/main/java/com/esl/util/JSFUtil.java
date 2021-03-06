package com.esl.util;

import java.io.IOException;

import javax.faces.FacesException;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

public class JSFUtil {
	private static Logger logger = Logger.getLogger("ESL");

	/**
	 * Redirect a request (no matter ajax or not) to new page
	 */
	public static String redirect(String link) {
        FacesContext ctx = FacesContext.getCurrentInstance();

        ExternalContext extContext = ctx.getExternalContext();
        if (!link.endsWith(".xhtml")) link = link.concat(".xhtml");
        String url = extContext.encodeActionURL(ctx.getApplication().getViewHandler().getActionURL(ctx, link));
        try {
            extContext.redirect(url);
        } catch (IOException ioe) {
            throw new FacesException(ioe);

        }
        return null; 
    }
}
