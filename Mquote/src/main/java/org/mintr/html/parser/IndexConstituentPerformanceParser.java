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
	public static String MSCIChinaConstituentsURL = "http://hk.ishares.com/product_info/fund/holdings/SEHK/2801.htm?periodCd=d";

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
					results.add(Integer.valueOf(e.html()).toString());
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return results;
	}

	public static List<String> getMSCIChinaConstituents() {
		List<String> results = new ArrayList<String>();
		String[] constituents1 = {"941","939","1398","883","700","3988","857","2628","386","2318","1088","688","1880","1288","151","3968","2601","728","762","135","1044","992","322","1109","1988","3328","914","291","836","1800"};
		String[] constituents2 = {"1898","3323","489","998","6808","358","2328","144","392","2688","2333","2319","2883","1171","902","1114","813","3333","960","1099","384","390","1199","2899","1186","3311","3377","2007","270","6030","2338"};
		String[] constituents3 = {"3383","1193","1157","2238","148","175","1766","2600","363","267","1066","168","3308","410","123","2777","3898","916","119","272","1818","966","753","220","3800","2689","694","165","691","552","1211"};
		String[] constituents4 = {"1313","210","6837","1169","639","1919","2607","2314","1833","2727","817","576","3618","3983","991","177","2866","1025"};
		String[] constituents5 = {"493","3368","3360","763","2009","336","656","2168","3998","2357","1055","1828","606","506","347","460","881","338","1138","2020","1208","1072","682"};

		results.addAll(Arrays.asList(constituents1));
		results.addAll(Arrays.asList(constituents2));
		results.addAll(Arrays.asList(constituents3));
		results.addAll(Arrays.asList(constituents4));
		results.addAll(Arrays.asList(constituents5));

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
