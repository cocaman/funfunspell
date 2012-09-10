package test.html.parser;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.mintr.html.parser.IndexConstituentPerformanceParser;

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
}
