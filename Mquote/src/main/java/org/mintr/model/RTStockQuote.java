package org.mintr.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RTStockQuote {
	private String stockCode = "";
	private String lastUpdate = "NA";
	private String price = "NA";
	private String high = "NA";
	private String low = "NA";
	private String change = "NA";
	private String changeAmount = "NA";
	private String pe = "NA";
	private String yield = "NA";
	private String NAV = "NA";
	private String yearLow = "NA";
	private String yearHigh = "NA";
	private Double yearHighPercentage = null;
	private Double lastYearPercentage = null;
	private Double last2YearPercentage = null;
	private Double last3YearPercentage = null;

	private Map<Integer, Double> previousPriceMap = new HashMap<Integer, Double>();

	public RTStockQuote() {}

	public RTStockQuote(String code) {
		this.stockCode = code;
	}

	public String getHigh() {
		return high;
	}
	public void setHigh(String high) {
		this.high = high;
	}
	public String getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(String lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	public String getLow() {
		return low;
	}
	public void setLow(String low) {
		this.low = low;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		yearHighPercentage = null;
		this.price = price;
	}
	public String getStockCode() {
		return stockCode;
	}
	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}
	public String getChange() {
		return change;
	}
	public void setChange(String change) {
		this.change = change;
	}


	@Override
	public String toString() {
		StringBuilder sbuilder = new StringBuilder();
		sbuilder.append("[");
		sbuilder.append(this.stockCode);
		sbuilder.append("] ");
		sbuilder.append(this.price);
		sbuilder.append(",");
		sbuilder.append(this.change);
		sbuilder.append(",");
		sbuilder.append(this.low);
		sbuilder.append("-");
		sbuilder.append(this.high);
		sbuilder.append(" (");
		sbuilder.append(this.lastUpdate);
		sbuilder.append(")");
		return sbuilder.toString();
	}

	public String getPe() {
		return pe;
	}

	public void setPe(String pe) {
		this.pe = pe;
	}

	public String getYield() {
		return yield;
	}

	public void setYield(String yield) {
		this.yield = yield;
	}

	public String getNAV() {
		return NAV;
	}

	public void setNAV(String nAV) {
		NAV = nAV;
	}

	public String getYearLow() {
		return yearLow;
	}

	public void setYearLow(String yearLow) {
		this.yearLow = yearLow;
	}

	public String getYearHigh() {
		return yearHigh;
	}

	public void setYearHigh(String yearHigh) {
		yearHighPercentage = null;
		this.yearHigh = yearHigh;
	}

	public String getChangeAmount() {
		return changeAmount;
	}

	public void setChangeAmount(String changeAmount) {
		this.changeAmount = changeAmount;
	}
	public double getYearHighPercentage() {
		try {
			if (yearHighPercentage == null) {
				double yearHighValue = Double.valueOf(yearHigh);
				double realPrice = Double.valueOf(price);
				yearHighPercentage = new BigDecimal(((realPrice - yearHighValue) / yearHighValue) * 100).setScale(2,RoundingMode.HALF_UP).doubleValue();

			}
		} catch (Exception e) {
			e.printStackTrace();
			yearHighPercentage = 0.0;
		}
		return yearHighPercentage;
	}

	public void setPreviousPrice(int previousYear, double price) {
		previousPriceMap.put(previousYear, price);
	}

	public Double getPreviousPrice(int previousYear) {
		return previousPriceMap.get(previousYear);
	}

	public double getPreviouYearPercentage(int previousYear) {
		double percentage = 0;
		try {
			double previousPrice = getPreviousPrice(previousYear);
			double realPrice = Double.valueOf(price);
			percentage = new BigDecimal(((realPrice - previousPrice) / previousPrice) * 100).setScale(2,RoundingMode.HALF_UP).doubleValue();
		} catch (Exception e) {
			e.printStackTrace();
			percentage = 0.0;
		}
		return percentage;
	}

	public Double getLastYearPercentage() {
		if (lastYearPercentage == null) {
			lastYearPercentage = getPreviouYearPercentage(1);
		}
		return lastYearPercentage;
	}


	public Double getLast2YearPercentage() {
		if (last2YearPercentage == null) {
			last2YearPercentage = getPreviouYearPercentage(2);
		}
		return last2YearPercentage;
	}


	public Double getLast3YearPercentage() {
		if (last3YearPercentage == null) {
			last3YearPercentage = getPreviouYearPercentage(3);
		}
		return last3YearPercentage;
	}


}
