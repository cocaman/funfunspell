package test.mintr.file.service;

import java.util.regex.Pattern;

import org.junit.Test;

public class TestComicZipService {

	@Test
	public void testGetCommonChars() {
		String s = "中文 123";
		String t = "中文 129";
		
		String commonChars = s.replaceAll("[^"+Pattern.quote(t)+"]","");
		System.out.println(commonChars);
	}
}
