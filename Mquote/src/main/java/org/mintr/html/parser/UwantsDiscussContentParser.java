package org.mintr.html.parser;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.mintr.entity.ForumThread;
import org.mintr.util.HttpURLConnectionBuilder;

public class UwantsDiscussContentParser implements ForumParser {

	@Override
	public List<ForumThread> getForumThreads(String url) {
		List<ForumThread> results = new ArrayList<ForumThread>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
		
		try
		{		
			HttpURLConnection connection = new HttpURLConnectionBuilder().setURL(url).createConnection();
			Document doc = Jsoup.parse(connection.getInputStream(), connection.getContentEncoding(), connection.getURL().getPath());
			Elements tbodys = doc.select("tbody[id^=normal]");				// each normal thread row have that id
			Iterator<Element> tbIterator = tbodys.iterator();
						
			while (tbIterator.hasNext()) {
				Element e = tbIterator.next();
				results.add(new ForumThread(e.select("span[id^=thread] a[href^=viewthread.php]").attr("href"),
						e.select("span[id^=thread] a[href^=viewthread.php]").text(),
						sdf.parse(e.select("td.author em").text())));				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return results;
	}

}
