package org.mintr.web.quote;

import org.mintr.model.RTStockQuote;

public interface WebQuoteRequestor {

	public abstract RTStockQuote getQuote();

	public abstract void setQuote(RTStockQuote quote);

}