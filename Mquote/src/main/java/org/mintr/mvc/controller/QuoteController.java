package org.mintr.mvc.controller;

import java.io.*;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.*;

import org.mintr.Mquote;
import org.mintr.html.parser.IndexConstituentPerformanceParser;
import org.mintr.html.service.IndexConstituentPerformanceService;
import org.mintr.model.RTStockQuote;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class QuoteController {
	final static String defaultCodeList = "2828,3046";
	final String FILE_PATH = "/WEB-INF/quotes.txt";
	final String ACTION_SAVE = "save";
	final String ACTION_LOAD = "load";

	private static Logger log = org.slf4j.LoggerFactory.getLogger(QuoteController.class);

	@Resource private Mquote quoteService;
	@Resource private IndexConstituentPerformanceService performaceService;

	@RequestMapping(value="/indexstockperformance")
	public String indexConstituentPerformance(ModelMap model) {
		model.put("hcei", IndexConstituentPerformanceParser.getDetailStockQuote("2828"));
		model.put("quotes", performaceService.getOrderedIndexContituents());
		return "constituentperformance";
	}

	@RequestMapping(value="/list", method = RequestMethod.GET)
	public String list(@RequestParam(value="codeList", required = false, defaultValue = "") String reqCodeList,
			@RequestParam(value="action", required=false) String action,
			@CookieValue(value="codeList", required=false) String cookieCodeList,
			HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		log.debug("list: reqCodeList [{}]", reqCodeList);
		if (!StringUtils.hasText(reqCodeList)) {
			log.debug("override by cookieCodeList [{}]", cookieCodeList);
			reqCodeList = cookieCodeList;
		}
		if (!StringUtils.hasText(reqCodeList)) {
			log.debug("override by defaultCodeList [{}]", defaultCodeList);
			reqCodeList = defaultCodeList;
		}

		try {
			if (ACTION_LOAD.equals(action.toLowerCase())) {
				File f = new File(request.getRealPath(FILE_PATH));
				FileInputStream fis = new FileInputStream(f);
				DataInputStream dis = new DataInputStream(fis);
				reqCodeList = dis.readLine();
			}

			if (ACTION_SAVE.equals(action.toLowerCase())) {
				//				File f = new File(request.getRealPath(FILE_PATH));
				//				f.createNewFile();
				//				FileWriter fw = new FileWriter(f);
				//				fw.write(reqCodeList);
				//				fw.flush();
				//				fw.close();
				//				f.exists();
			}
		} catch (Exception e) {}

		Cookie c = new Cookie("codeList",reqCodeList);
		c.setMaxAge(60*60*24*365);
		response.addCookie(c);

		List<RTStockQuote> quotes = quoteService.getRTStockQuoteList(reqCodeList);
		List<RTStockQuote> indexs = quoteService.getGlobalIndexs();

		// set response model
		model.put("codeList", reqCodeList);
		model.put("quotes", quotes);
		model.put("indexs", indexs);

		return "quote";
	}

	@RequestMapping(value="/single", method = RequestMethod.GET)
	@ResponseBody
	public RTStockQuote single(@RequestParam(value = "code", required = false) String reqCode, ModelMap model) {
		log.debug("single: reqCode [{}]", reqCode);
		if (!StringUtils.hasText(reqCode)) return null;

		List<RTStockQuote> quotes = quoteService.getRTStockQuoteList(reqCode);
		return quotes.get(0);
	}
}
