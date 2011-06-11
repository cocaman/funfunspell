package org.mintr;

import java.util.ArrayList;
import java.util.List;

import org.htmlparser.*;
import org.htmlparser.filters.OrFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.util.NodeList;
import org.mintr.model.RTStockQuote;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

@Service("etNetIndexHandler")
public class ETNETIndexHandler {
	private Logger log = org.slf4j.LoggerFactory.getLogger(Mquote.class);
	private static String ETNET_INDEX_URL = "http://www.etnet.com.hk/www/eng/stocks/indexes_main.php";
	private static List<String> indexNames = new ArrayList<String>();

	static {
		indexNames.add("Hang Seng Index");
		indexNames.add("Shanghai A Share");
		indexNames.add("HS China Enterprises Index");
		indexNames.add("Tokyo Nikkei 225");
		indexNames.add("Dow Jones Industrial Ave");
		indexNames.add("London FT100");
		indexNames.add("Frankfurt DAX");
	}

	public List<RTStockQuote> getGlobalIndexs() {
		log.debug("getGlobalIndexs: START");
		Parser parser;
		ArrayList<RTStockQuote> indexs = new ArrayList<RTStockQuote>();
		try
		{
			parser = new Parser ();
			NodeFilter filterTD = new TagNameFilter ("TD");
			NodeFilter filterA = new TagNameFilter ("A");
			NodeFilter orFilter = new OrFilter(new NodeFilter[] {filterTD, filterA});

			// for a simple dump, use more verbose settings
			parser.setFeedback (Parser.STDOUT);
			parser.setResource(ETNET_INDEX_URL);
			NodeList list = parser.parse (orFilter);
			for (Node n : list.toNodeArray()) {
				if (n.getText().contains("td")) {
					for (String s : indexNames) {
						if (n.getFirstChild()!=null && n.getFirstChild().getText().contains(s)) {
							indexs.add(getIndexQuote(s, n));
						}
					}
				} else if (n.getText().contains("a") && n.getParent().getText().contains("td")) {
					for (String s : indexNames) {
						if (n.getFirstChild()!=null && n.getFirstChild().getText().contains(s)) {
							indexs.add(getIndexQuote(s, n.getParent()));
						}
					}
				}
			}

		}
		catch (Exception e)
		{
			log.warn("Error when retrieve data from ETNET", e);
		}

		log.debug("getGlobalIndexs: END");
		return indexs;
	}

	// input the TD node
	private RTStockQuote getIndexQuote(String indexName, Node node) {
		log.debug("getIndexQuote: input node [" + node + "]");
		RTStockQuote quote = new RTStockQuote(indexName);
		try {
			Node pointingTDNode = node.getNextSibling();
			quote.setPrice(pointingTDNode.getFirstChild().getText());
			pointingTDNode = pointingTDNode.getNextSibling();
			quote.setChange(pointingTDNode.getFirstChild().getText() + "(" + pointingTDNode.getNextSibling().getFirstChild().getText() + ")");
			pointingTDNode = pointingTDNode.getNextSibling().getNextSibling();
			quote.setHigh(pointingTDNode.getFirstChild().getText());
			pointingTDNode = pointingTDNode.getNextSibling();
			quote.setLow(pointingTDNode.getFirstChild().getText());
		} catch (Exception e) {
			log.warn("Error in getting index for " + indexName, e);
		}
		log.debug("getIndexQuote: return quote " + quote);
		return quote;
	}

	public static void main(String[] args) {
		ETNETIndexHandler h = new ETNETIndexHandler();
		for (RTStockQuote q : h.getGlobalIndexs()) {
			System.out.println(q);
		}
	}

}
