package org.mintr.html.parser;

import java.net.HttpURLConnection;
import java.text.MessageFormat;
import java.util.Calendar;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.mintr.util.HttpURLConnectionBuilder;

public class HistoryQuoteParser {
	private static String YAHOO_HISTORY_QUOTE_URL = "http://hk.finance.yahoo.com/q/hp?s={0}.HK&a={1,number,#}&b={2,number,#}&c={3,number,#}&d={4,number,#}&e={5,number,#}&f={6,number,#}&g=d";

	public static Double getPreviousYearQuote(String stock, int previousYear) {
		Double value = null;
		try
		{
			HttpURLConnection connection = new HttpURLConnectionBuilder().setURL(getQuoteURL(stock,previousYear)).createConnection();
			Document doc = Jsoup.parse(connection.getInputStream(), "UTF-8", connection.getURL().getPath());

			Elements tds = doc.select("td[class^=yfnc_tabledata1]");
			value = Double.parseDouble(tds.get(6).html());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}

	public static String getQuoteURL(String stock, int previousYear) {
		Calendar now = Calendar.getInstance();
		int fromDay, fromMonth, fromYear;
		int toDay, toMonth, toYear;

		now.add(Calendar.YEAR, 0-previousYear);
		fromDay = now.get(Calendar.DAY_OF_MONTH);
		fromMonth = now.get(Calendar.MONTH);
		fromYear = now.get(Calendar.YEAR);
		now.add(Calendar.DATE, 7);
		toDay = now.get(Calendar.DAY_OF_MONTH);
		toMonth = now.get(Calendar.MONTH);
		toYear = now.get(Calendar.YEAR);

		while (stock.length() < 4) {
			stock = "0" + stock;
		}

		return MessageFormat.format(YAHOO_HISTORY_QUOTE_URL, stock, fromMonth, fromDay, fromYear, toMonth, toDay, toYear);
	}
}
