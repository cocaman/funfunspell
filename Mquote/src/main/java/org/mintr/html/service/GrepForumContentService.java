package org.mintr.html.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mintr.entity.ForumThread;
import org.mintr.html.parser.ForumParser;
import org.mintr.html.parser.UwantsDiscussContentParser;
import org.mintr.mvc.controller.QuoteController;
import org.slf4j.Logger;

public class GrepForumContentService {
	private static Logger log = org.slf4j.LoggerFactory.getLogger(GrepForumContentService.class);
	public enum ContentType {MUSIC, MOVIE}
	
	static String UWANTS_MUSIC = "http://www.uwants.com/forumdisplay.php?fid=472";
	static String DISCUSS_MUSIC = "http://www.discuss.com.hk/forumdisplay.php?fid=101";
	static String UWANTS_DVD = "http://www.uwants.com/forumdisplay.php?fid=231";
	
	static Map<ContentType, String[]> typeToURLlistMap = new HashMap<ContentType, String[]>();
	static Map<String, Class<? extends ForumParser>> urlToParserClassMap = new HashMap<String, Class<? extends ForumParser>>();
	
	static {
		typeToURLlistMap.put(ContentType.MUSIC, new String[] {UWANTS_MUSIC, DISCUSS_MUSIC});
		typeToURLlistMap.put(ContentType.MOVIE, new String[] {UWANTS_DVD});
		
		urlToParserClassMap.put(UWANTS_MUSIC, UwantsDiscussContentParser.class);
		urlToParserClassMap.put(DISCUSS_MUSIC, UwantsDiscussContentParser.class);
		urlToParserClassMap.put(UWANTS_DVD, UwantsDiscussContentParser.class);
	}
	
	public List<ForumThread> grepForumContentByType(String type, int page) {
		log.debug("grepForumContentByType: type='{}', page='{}'", type, page);
		
		String[] urls = typeToURLlistMap.get(type);
		for (String url : urls) {
			
		}
	}
	
	class ContentParserRunner implements Runnable {
		ForumParser parser;
		String url;
		List<ForumThread> results;

		@Override public void run() {
			results = parser.getForumThreads(url);			
		}		
	}
	
}
