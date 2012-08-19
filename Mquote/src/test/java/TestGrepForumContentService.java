import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.mintr.entity.ForumThread;
import org.mintr.html.parser.TvboxnowContentParser;
import org.mintr.html.parser.UwantsDiscussContentParser;
import org.mintr.html.service.GrepForumContentService;
import org.mintr.html.service.GrepForumContentService.ContentType;


public class TestGrepForumContentService {
	@BeforeClass
	public static void setup() {
		//		HttpURLConnectionBuilder.setProxyPort(8443);
		//		HttpURLConnectionBuilder.setProxyUrl("proxy.jpmchase.net");
	}

	@Test
	public void grepForumContentServiceFromMusicPage2() {
		GrepForumContentService service = new GrepForumContentService();
		List<ForumThread> threads = service.grepForumContentByType(ContentType.MUSIC, 2);

		assertTrue(threads.size() > 10);
		for (ForumThread f : threads) {
			System.out.println(f);
			assertNotNull(f.getUrl());
			assertNotNull(f.getTitle());
			assertNotNull(f.getDate());
		}
	}

	@Test
	public void readUwantsMusic() {
		String inputUrl = "http://www.uwants.com/forumdisplay.php?fid=472";
		UwantsDiscussContentParser p = new UwantsDiscussContentParser();
		List<ForumThread> results = p.getForumThreads(inputUrl);

		assertTrue(results.size() > 10);
		for (ForumThread f : results) {
			System.out.println(f);
			assertTrue(f.getUrl().startsWith("www.uwants.com/viewthread.php"));
			assertNotNull(f.getTitle());
			assertNotNull(f.getDate());
		}
	}

	@Test
	public void readDiscussMusic() {
		String inputUrl = "http://www.discuss.com.hk/forumdisplay.php?fid=101";
		UwantsDiscussContentParser p = new UwantsDiscussContentParser();
		List<ForumThread> results = p.getForumThreads(inputUrl);

		assertTrue(results.size() > 10);
		for (ForumThread f : results) {
			System.out.println(f);
			assertTrue(f.getUrl().startsWith("www.discuss.com.hk/viewthread.php"));
			assertNotNull(f.getTitle());
			assertNotNull(f.getDate());
		}
	}

	@Test
	public void readTvboxnow() {
		String inputUrl = "http://www.tvboxnow.com/forum-8-1.html";

		TvboxnowContentParser p = new TvboxnowContentParser();
		List<ForumThread> results = p.getForumThreads(inputUrl);

		assertTrue(results.size() > 10);
		for (ForumThread f : results) {
			System.out.println(f);
			assertTrue(f.getUrl().startsWith("www.tvboxnow.com/thread-"));
			assertNotNull(f.getTitle());
			assertNotNull(f.getDate());
		}
	}

}