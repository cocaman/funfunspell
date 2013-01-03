package test.html.parser;

import org.junit.BeforeClass;
import org.junit.Test;
import org.mintr.html.parser.HistoryQuoteParser;

public class TestHistoryQuoteParser {
	@BeforeClass
	public static void setup() {
//		HttpURLConnectionBuilder.setProxyPort(8443);
//		HttpURLConnectionBuilder.setProxyUrl("proxy.jpmchase.net");
	}

	@Test
	public void testGetQuoteURL() {
		System.out.println(HistoryQuoteParser.getQuoteURL("2800", 3));
	}
	
	@Test
	public void testHistoryQuoteParser() {
		double value = HistoryQuoteParser.getPreviousYearQuote("2800", 3);
		System.out.println(value);
		
	}
}
