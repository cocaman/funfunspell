package test.html.parser;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.mintr.html.parser.IndexConstituentPerformanceParser;
import org.mintr.model.RTStockQuote;

public class TestIndexConstituentPerformanceParser {

	@Test
	public void testGetAllHSIConstituents() {
		List<String> constituents = new IndexConstituentPerformanceParser().getHSIConstituents();

		assertTrue(constituents.contains("00001"));
		assertTrue(constituents.contains("00002"));
		assertTrue(constituents.contains("00005"));
	}

	@Test
	public void testGetAllHCEIConstituents() {
		List<String> constituents = new IndexConstituentPerformanceParser().getHCEIConstituents();

		assertTrue(constituents.contains("00358"));
		assertTrue(constituents.contains("00386"));
		assertTrue(constituents.contains("02628"));
	}

	@Test
	public void testGetAllHCCIConstituents() {
		List<String> constituents = new IndexConstituentPerformanceParser().getHCCIConstituents();

		assertTrue(constituents.contains("00941"));
		assertTrue(constituents.contains("00883"));
		assertTrue(constituents.contains("00762"));
	}

	@Test
	public void testGet883DetailQuote() {
		RTStockQuote quote = new IndexConstituentPerformanceParser().getDetailStockQuote("883");

		assertEquals("883", quote.getStockCode());
		assertNotNull(quote.getChange());
		assertNotNull(quote.getChangeAmount());
		assertNotNull(quote.getHigh());
		assertNotNull(quote.getLastUpdate());
		assertNotNull(quote.getLow());
		assertNotNull(quote.getNAV());
		assertNotNull(quote.getPe());
		assertNotNull(quote.getPrice());
		assertNotNull(quote.getYearHigh());
		assertNotNull(quote.getYearLow());
		assertNotNull(quote.getYield());

		assertFalse("NA".equals(quote.getChange()));
		assertFalse("NA".equals(quote.getChangeAmount()));
		assertFalse("NA".equals(quote.getHigh()));
		assertFalse("NA".equals(quote.getLastUpdate()));
		assertFalse("NA".equals(quote.getLow()));
		assertFalse("NA".equals(quote.getNAV()));
		assertFalse("NA".equals(quote.getPe()));
		assertFalse("NA".equals(quote.getPrice()));
		assertFalse("NA".equals(quote.getYearHigh()));
		assertFalse("NA".equals(quote.getYearLow()));
		assertFalse("NA".equals(quote.getYield()));


	}
}
