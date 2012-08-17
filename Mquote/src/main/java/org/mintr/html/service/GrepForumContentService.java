package org.mintr.html.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.mintr.ETNETQuoteHandler;
import org.mintr.entity.ForumThread;
import org.mintr.html.parser.ForumParser;
import org.mintr.html.parser.UwantsDiscussContentParser;
import org.mintr.model.RTStockQuote;
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
		List<Future<List<ForumThread>>> futures = new ArrayList<Future<List<ForumThread>>>();		
		ExecutorService executor = Executors.newFixedThreadPool(urls.length);
		List<ForumThread> results = new ArrayList<ForumThread>();
		
		// create thread and run
		for (String url : urls) {
			ContentParserRunner runner = new ContentParserRunner();
			futures.add(executor.submit(runner));
		}
		
		// Getting result
		for (Future<List<ForumThread>> future : futures) {
			try {
				results.addAll(future.get());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		executor.shutdown();
		return results;
	}
	
	class ContentParserRunner implements Callable<List<ForumThread>> {
		ForumParser parser;
		String url;		

		@Override public List<ForumThread> call() {
			return parser.getForumThreads(url);			
		}		
	}
	
}
