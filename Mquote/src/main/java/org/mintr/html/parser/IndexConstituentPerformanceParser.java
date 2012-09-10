package org.mintr.html.parser;

import java.net.HttpURLConnection;
import java.util.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.mintr.util.HttpURLConnectionBuilder;

public class IndexConstituentPerformanceParser {
	public static String HSIConstituentsURL = "http://www.etnet.com.hk/www/tc/stocks/indexes_detail.php?subtype=HSI";
	public static String HCEIConstituentsURL = "http://www.etnet.com.hk/www/tc/stocks/indexes_detail.php?subtype=cei";
	public static String HCCIConstituentsURL= "http://www.etnet.com.hk/www/tc/stocks/indexes_detail.php?subtype=cci";

	public List<String> getHSIConstituents() {
		return getEtnetIndexConstituents(HSIConstituentsURL);
	}

	public List<String> getHCEIConstituents() {
		return getEtnetIndexConstituents(HCEIConstituentsURL);
	}

	public List<String> getHCCIConstituents() {
		return getEtnetIndexConstituents(HCCIConstituentsURL);
	}

	public List<String> getEtnetIndexConstituents(String url) {
		List<String> results = new ArrayList<String>();

		try
		{
			HttpURLConnection connection = new HttpURLConnectionBuilder().setURL(url).createConnection();
			Document doc = Jsoup.parse(connection.getInputStream(), "UTF-8", connection.getURL().getPath());

			Elements divs = doc.select("a[href^=realtime/quote.php?code=].Number");				// each thread row have that id
			Iterator<Element> divsIterator = divs.iterator();

			while (divsIterator.hasNext()) {
				Element e = divsIterator.next();
				results.add(e.html());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return results;
	}

}
