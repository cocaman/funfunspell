package org.mintr.html.parser;

import java.net.HttpURLConnection;
import java.util.*;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.mintr.model.RTStockQuote;
import org.mintr.util.HttpURLConnectionBuilder;

public class IndexConstituentPerformanceParser {
	public static String HSIConstituentsURL = "http://www.etnet.com.hk/www/tc/stocks/indexes_detail.php?subtype=HSI";
	public static String HCEIConstituentsURL = "http://www.etnet.com.hk/www/tc/stocks/indexes_detail.php?subtype=cei";
	public static String HCCIConstituentsURL= "http://www.etnet.com.hk/www/tc/stocks/indexes_detail.php?subtype=cci";
	public static String AAStockDetailQuote = "http://www.aastocks.com/en/stock/detailquote.aspx?symbol=";

	public static List<String> getHSIConstituents() {
		return getEtnetIndexConstituents(HSIConstituentsURL);
	}

	public static List<String> getHCEIConstituents() {
		return getEtnetIndexConstituents(HCEIConstituentsURL);
	}

	public static List<String> getHCCIConstituents() {
		return getEtnetIndexConstituents(HCCIConstituentsURL);
	}

	public static List<String> getEtnetIndexConstituents(String url) {
		List<String> results = new ArrayList<String>();

		try
		{
			HttpURLConnection connection = new HttpURLConnectionBuilder().setURL(url).createConnection();
			Document doc = Jsoup.parse(connection.getInputStream(), "UTF-8", connection.getURL().getPath());

			Elements divs = doc.select("a[href^=realtime/quote.php?code=]");				// each thread row have that id
			Iterator<Element> divsIterator = divs.iterator();

			while (divsIterator.hasNext()) {
				Element e = divsIterator.next();
				if (StringUtils.isNumeric(e.html())) {
					results.add(e.html());
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return results;
	}

	public static RTStockQuote getDetailStockQuote(String code) {
		if (!StringUtils.isNumeric(code)) return null;

		RTStockQuote quote = new RTStockQuote(code);

		try
		{
			HttpURLConnection connection = new HttpURLConnectionBuilder().setURL(AAStockDetailQuote + code).createConnection();
			Document doc = Jsoup.parse(connection.getInputStream(), null, connection.getURL().getPath());

			// price
			quote.setPrice(doc.select("ul.UL1 li.LI1:containsOwn(Last)").first().parent().nextElementSibling().child(0).child(0).html());

			// change
			Elements divs = doc.select("ul.UL1.W1 li.LI1:contains(Chg)");
			boolean isDown = divs.get(0).parent().nextElementSibling().select("img").first().attr("src").contains("downarrow");
			quote.setChangeAmount(isDown?"-" + divs.get(0).parent().nextElementSibling().select("span.bold").first().html():"+" + divs.get(0).parent().nextElementSibling().select("span.bold").first().html());
			quote.setChange(isDown?"-" + divs.get(1).parent().nextElementSibling().select("span.bold").first().html():"+" + divs.get(1).parent().nextElementSibling().select("span.bold").first().html());

			// day high day low
			quote.setHigh(doc.select("ul.UL1.W2 li.LI1:containsOwn(High)").get(0).parent().nextElementSibling().child(0).html());
			quote.setLow(doc.select("ul.UL1.W2 li.LI1:containsOwn(Low)").get(0).parent().nextElementSibling().child(0).html());

			// PE
			quote.setPe(doc.select("td.font12_grey:containsOwn(P/E Ratio)").get(0).nextElementSibling().html());
			// yield
			quote.setYield(doc.select("td.font12_grey:containsOwn(Yield)").get(0).nextElementSibling().html());
			// NAV
			quote.setNAV(doc.select("td.font12_grey:containsOwn(NAV)").get(0).nextElementSibling().html());

			// 52 high low
			String[] yearHighLow = doc.select("ul.UL2 li.LI3:containsOwn(52 Week Range)").get(0).parent().nextElementSibling().child(0).html().split(" - ");
			quote.setYearLow(yearHighLow[0]);
			quote.setYearHigh(yearHighLow[1]);

			// last update
			quote.setLastUpdate(doc.select("font.font12_white:containsOwn(Last Update:)").get(0).nextElementSibling().html());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return quote;
	}

}
