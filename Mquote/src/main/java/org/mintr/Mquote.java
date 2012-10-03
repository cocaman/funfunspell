package org.mintr;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import javax.annotation.Resource;

import org.htmlparser.Parser;
import org.mintr.html.parser.IndexConstituentPerformanceParser;
import org.mintr.model.RTStockQuote;
import org.mintr.web.quote.ETNETIndexHandler;
import org.mintr.web.quote.ETNETQuoteHandler;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

@Service("mQuoteService")
public class Mquote {
	private static Logger log = org.slf4j.LoggerFactory.getLogger(Mquote.class);

	public static final String CODE_SEPARATOR = ",";

	@Resource private ETNETIndexHandler indexHandler;

	private ExecutorService threadPool;

	// set the connection for all parser when start
	static {
		setParserConnection();
	}

	public List<RTStockQuote> getGlobalIndexs() {
		return indexHandler.getGlobalIndexs();
	}

	public List<RTStockQuote> getRTStockQuoteList(String codeList) {
		if (codeList == null || codeList.equals("")) {
			log.warn("getRTStockQuoteList: No code list input");
			return null;
		}

		List<RTStockQuote> quotes = new ArrayList<RTStockQuote>();
		List<Future<RTStockQuote>> futures = new ArrayList<Future<RTStockQuote>>();
		String[] codes = codeList.split(CODE_SEPARATOR);
		log.debug("getRTStockQuoteList: codes length [" + codes.length + "]");
		threadPool = Executors.newFixedThreadPool(codes.length);

		for (String code : codes) {
			futures.add(threadPool.submit(new GetDetailStockQuoteRunner(code)));
		}
		for (Future<RTStockQuote> f : futures) {
			RTStockQuote q;
			try {
				quotes.add(f.get());
			} catch (Exception e) {
				log.warn("Error when getting future.get", e);
			}
		}
		threadPool.shutdown();
		return quotes;
	}

	public RTStockQuote getRTStockQuote(String code) {
		log.debug("getRTStockQuote: START");
		if (code == null || code.equals("")) {
			log.warn("getRTStockQuote: No code input");
			return null;
		}

		ETNETQuoteHandler quoteHandler = new ETNETQuoteHandler(new RTStockQuote(code.trim()));

		// run as single thread
		quoteHandler.run();

		log.debug("getRTStockQuote: END");
		log.debug("getRTStockQuote: return quote [" + quoteHandler.getQuote() + "]");
		return quoteHandler.getQuote();
	}

	public Future<ETNETQuoteHandler> getRTStockQuoteMT(String code) {
		log.debug("getRTStockQuoteMT: START");
		if (code == null || code.equals("")) {
			log.warn("getRTStockQuoteMT: No code input");
			return null;
		}
		ETNETQuoteHandler quoteHandler = new ETNETQuoteHandler(new RTStockQuote(code.trim()));
		return threadPool.submit(quoteHandler, quoteHandler);
	}

	class GetDetailStockQuoteRunner implements Callable<RTStockQuote> {
		String code;
		GetDetailStockQuoteRunner(String code) {this.code = code;}
		@Override public RTStockQuote call() {
			return IndexConstituentPerformanceParser.getDetailStockQuote(code);
		}
	}

	private static void setParserConnection() {
		log.debug("setParserConnection: START");
		Parser.getConnectionManager().setRedirectionProcessingEnabled (true);
		Parser.getConnectionManager().setCookieProcessingEnabled (true);
		/*
        Parser.getConnectionManager().setProxyHost("inet-proxy-a.appl.swissbank.com");
        Parser.getConnectionManager().setProxyPort(8080);
        Parser.getConnectionManager().setProxyUser("43303183");
        Parser.getConnectionManager().setProxyPassword("asdASD1");*/
	}
}
