package com.esl.web.jsf.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.faces.context.FacesContext;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.esl.dao.INewsDAO;
import com.esl.model.News;

@Controller
@Scope("session")
public class NewsController extends ESLController {
	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger("ESL");

	private static String singleNewsView = "/public/news/singlenews";

	//	 Supporting instance
	@Resource private INewsDAO newsDAO;

	// ============== UI display data ================//
	private News selectedNews;
	private Long selectedNewsId;
	private String selectedNewsContent;

	// ============== Setter / Getter ================//
	public void setNewsDAO(INewsDAO dao) {this.newsDAO = dao; }

	public News getSelectedNews() {	return selectedNews;}
	public void setSelectedNews(News selectedNews) {this.selectedNews = selectedNews;}

	public Long getSelectedNewsId() {return selectedNewsId;	}
	public void setSelectedNewsId(Long selectedNewsId) {this.selectedNewsId = selectedNewsId;}

	// ============== Getter Function ================//
	public List<News> getOrderedNews() {
		logger.info("getOrderedNews: START");
		String locale = FacesContext.getCurrentInstance().getViewRoot().getLocale().toString();
		logger.info("getOrderedNews: locale[" + locale + "]");
		List<News> l =newsDAO.listOrderedNews(locale);
		logger.info("getOrderedNews: returned news size[" + l.size() + "]");

		return l;
	}

	public List<News> getLatestNews() {
		logger.info("getLatestNews: START");
		String locale = FacesContext.getCurrentInstance().getViewRoot().getLocale().toString();
		logger.info("getLatestNews: locale[" + locale + "]");
		List<News> l =newsDAO.listLatestNews(locale);
		logger.info("getLatestNews: returned news size[" + l.size() + "]");
		return l;
	}

	public String getSelectedNewsContent() {
		return selectedNewsContent;
	}

	// ============== Functions ================//

	/**
	 * Request by url get for display single news
	 */
	public String viewSingleNews() {
		logger.info("viewSingleNews: START");
		logger.info("viewSingleNews: input new id[" + selectedNewsId + "]");

		selectedNews = newsDAO.getById(selectedNewsId);
		logger.info("viewSingleNews: selected news [" + selectedNews + "]");

		String filePath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/public/news/html/" + selectedNews.getHtmlURL());
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(filePath));
			String line = reader.readLine();
			selectedNewsContent = "";
			while (line != null) {
				selectedNewsContent += line;
				line = reader.readLine();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (selectedNews == null)
			return errorView;
		else
			return singleNewsView;
	}
}
