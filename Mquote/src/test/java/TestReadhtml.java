import static org.junit.Assert.*;

import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;

import javax.validation.constraints.AssertTrue;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.OrFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.util.NodeList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mintr.entity.ForumThread;
import org.mintr.html.parser.UwantsDiscussContentParser;
import org.mintr.util.HttpURLConnectionBuilder;


public class TestReadhtml {
	@BeforeClass
	public static void setup() {
		HttpURLConnectionBuilder.setProxyPort(8443);
		HttpURLConnectionBuilder.setProxyUrl("proxy.jpmchase.net");
	}
	
	@Test
	public void readUwantsMusic() {
		String inputUrl = "http://www.uwants.com/forumdisplay.php?fid=472";
		UwantsDiscussContentParser p = new UwantsDiscussContentParser();
		List<ForumThread> results = p.getForumThreads(inputUrl);
		
		assertTrue(results.size() > 0);
		for (ForumThread f : results) {
			System.out.println(f);
			assertTrue(f.getUrl().startsWith("viewthread.php"));
			assertNotNull(f.getTitle());
			assertNotNull(f.getDate());
		}		
	}
	
	@Test
	public void readDiscussMusic() {
		String inputUrl = "http://www.discuss.com.hk/forumdisplay.php?fid=101";
		UwantsDiscussContentParser p = new UwantsDiscussContentParser();
		List<ForumThread> results = p.getForumThreads(inputUrl);
		
		assertTrue(results.size() > 0);
		for (ForumThread f : results) {
			System.out.println(f);
			assertTrue(f.getUrl().startsWith("viewthread.php"));
			assertNotNull(f.getTitle());
			assertNotNull(f.getDate());
		}		
	}

}