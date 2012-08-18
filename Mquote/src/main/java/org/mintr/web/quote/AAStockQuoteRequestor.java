package org.mintr.web.quote;

import java.net.HttpURLConnection;
import java.net.URL;

import org.htmlparser.*;
import org.htmlparser.filters.OrFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.util.NodeList;
import org.mintr.Mquote;
import org.mintr.model.RTStockQuote;
import org.slf4j.Logger;

public class AAStockQuoteRequestor implements Runnable, WebQuoteRequestor {
	private Logger log = org.slf4j.LoggerFactory.getLogger(Mquote.class);
	private static String QUOTE_URL = "http://www.aastocks.com/tc/ltp/rtquote.aspx?symbol=";
	private RTStockQuote quote;

	public AAStockQuoteRequestor(RTStockQuote quote) {
		this.quote = quote;
	}

	public void run() {
		log.debug("run: START");
		if (quote == null) {
			log.warn("run: input quote is null");
			return;
		}

		log.debug("run: retrieve data for code[" + quote.getStockCode() + "]");
		Parser parser;
		try
		{
			parser = new Parser ();
			NodeFilter filterTD = new TagNameFilter ("TD");
			NodeFilter filterSPAN = new TagNameFilter ("SPAN");
			NodeFilter orFilter = new OrFilter(new NodeFilter[] {filterTD, filterSPAN});

			HttpURLConnection connection;
			URL url = new URL (QUOTE_URL + quote.getStockCode());
			connection = (HttpURLConnection)url.openConnection ();
			connection.setRequestMethod ("POST");
			connection.setRequestProperty ("User-Agent", "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.0; Trident/5.0)");

			// for a simple dump, use more verbose settings
			parser.setFeedback (Parser.STDOUT);
			parser.setConnection(connection);

			NodeList list = parser.parse (orFilter);
			for (Node n : list.toNodeArray()) {
				if (n.getText().contains("Quote")) {
					if (n.getFirstChild()!= null) {
						// Get last update time
						if (n.getFirstChild().getText().contains("現價")) {
							quote.setLastUpdate(n.getChildren().elementAt(2).getFirstChild().getText());
						}

						// Get Real price, chg and % chg
						if (n.getFirstChild().getText().contains("src=\"../../common/images/arrow")) {
							quote.setPrice(n.getChildren().elementAt(1).getText());
							quote.setChange(n.getNextSibling().getNextSibling().getNextSibling().getFirstChild().getText());
						}
						// Get Day High
						if (n.getChildren().size() > 3 && n.getFirstChild().getText().contains("High") && n.getChildren().elementAt(2).getText().contains("RT")) {
							Node child = n.getParent().getParent().getNextSibling().getNextSibling().getChildren().elementAt(1).getFirstChild().getFirstChild();
							while (child != null) {
								try {
									Double.parseDouble(child.getText());
									quote.setHigh(child.getText());
									break;
								} catch (NumberFormatException e) {
									log.error("Not number [" + child.getText() + "]", e);
									child = child.getFirstChild();
								}
							}
						}
						// Get Day Low
						if (n.getChildren().size() > 3 && n.getFirstChild().getText().contains("Low") && n.getChildren().elementAt(2).getText().contains("RT")) {
							Node child = n.getParent().getParent().getNextSibling().getNextSibling().getChildren().elementAt(1).getFirstChild().getFirstChild();
							while (child != null) {
								try {
									Double.parseDouble(child.getText());
									quote.setLow(child.getText());
									break;
								} catch (NumberFormatException e) {
									log.error("Not number [" + child.getText() + "]", e);
									child = child.getFirstChild();
								}
							}
						}
					}
				}
			}

		}
		catch (Exception e)
		{
			log.warn("Error when retrieve data from ETNET", e);
		}
		log.debug("run: END");
	}

	/* (non-Javadoc)
	 * @see org.mintr.WebQuoteRequest#getQuote()
	 */
	@Override
	public RTStockQuote getQuote() {return quote;}
	/* (non-Javadoc)
	 * @see org.mintr.WebQuoteRequest#setQuote(org.mintr.model.RTStockQuote)
	 */
	@Override
	public void setQuote(RTStockQuote quote) {this.quote = quote;}
}
