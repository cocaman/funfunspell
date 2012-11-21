package test.mintr.file.service;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mintr.file.service.ComicZipService;

public class TestComicZipService {

	@Test
	public void testAggregateSingleFileName() {
		String s = "中文 [123] abb";
		List<String> strings = new ArrayList<String>();
		strings.add(s);
		
		ComicZipService service = new ComicZipService();
		assertEquals(service.aggregateFileName(strings), s);	
	}
	
	@Test
	public void testAggregateFileNameNormal1() {
		String s1 = "中文 [123] abb";
		String s2 = "中文 [124] cdb";
		String s3 = "中文 [125] ggg";
		String result = "中文  [123, 124, 125]";
		List<String> strings = new ArrayList<String>();
		strings.add(s1); strings.add(s2); strings.add(s3);
		
		ComicZipService service = new ComicZipService();
		assertEquals(result, service.aggregateFileName(strings));	
	}
}
