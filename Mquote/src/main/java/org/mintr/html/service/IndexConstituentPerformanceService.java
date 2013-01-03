package org.mintr.html.service;

import java.util.*;
import java.util.concurrent.*;

import org.mintr.BeanUtil;
import org.mintr.html.parser.HistoryQuoteParser;
import org.mintr.html.parser.IndexConstituentPerformanceParser;
import org.mintr.model.RTStockQuote;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

@Service("indexConstituentPerformanceService")
public class IndexConstituentPerformanceService {
	private static Logger log = org.slf4j.LoggerFactory.getLogger(IndexConstituentPerformanceService.class);

	public RTStockQuote getHceiETF() {
		return IndexConstituentPerformanceService.getDetailStockQuoteWith3PreviousYearPrice("2828");
	}

	public List<RTStockQuote> getIndexContituents() {
		Set<String> stockCodes = new HashSet<String>();
		stockCodes.addAll(IndexConstituentPerformanceParser.getHSIConstituents());
		stockCodes.addAll(IndexConstituentPerformanceParser.getHCCIConstituents());
		stockCodes.addAll(IndexConstituentPerformanceParser.getHCEIConstituents());

		List<RTStockQuote> quotes = new ArrayList<RTStockQuote>();
		ExecutorService executor = Executors.newFixedThreadPool(10);
		List<Future<RTStockQuote>> futures = new ArrayList<Future<RTStockQuote>>();
		for (String code : stockCodes) {
			futures.add(executor.submit(new GetDetailStockQuoteRunner(code)));
		}
		// Getting result
		for (Future<RTStockQuote> future : futures) {
			try {
				quotes.add(future.get());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return quotes;
	}

	public List<RTStockQuote> getOrderedIndexContituents() {
		List<RTStockQuote> quotes = getIndexContituents();
		Collections.sort(quotes, BeanUtil.getCompare("getLastYearPercentage"));
		return quotes;
	}

	public static RTStockQuote getDetailStockQuoteWith3PreviousYearPrice(String code) {
		RTStockQuote quote = IndexConstituentPerformanceParser.getDetailStockQuote(code);
		for (int i = 1; i < 4; i++) {
			Double price = HistoryQuoteParser.getPreviousYearQuote(code, i);
			if (price != null) quote.setPreviousPrice(i, price);
		}
		return quote;
	}

	class GetDetailStockQuoteRunner implements Callable<RTStockQuote> {
		String code;
		GetDetailStockQuoteRunner(String code) {this.code = code;}
		@Override public RTStockQuote call() {
			return IndexConstituentPerformanceService.getDetailStockQuoteWith3PreviousYearPrice(code);
		}
	}

}
