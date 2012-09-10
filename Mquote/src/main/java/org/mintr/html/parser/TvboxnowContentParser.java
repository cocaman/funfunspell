package org.mintr.html.parser;

import java.net.HttpURLConnection;
import java.text.SimpleDateFormat;
import java.util.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.mintr.entity.ForumThread;
import org.mintr.util.HttpURLConnectionBuilder;

public class TvboxnowContentParser implements ForumParser {

	@Override
	public List<ForumThread> getForumThreads(String url) {
		List<ForumThread> results = new ArrayList<ForumThread>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");

		try
		{
			HttpURLConnection connection = new HttpURLConnectionBuilder().setURL(url).createConnection();
			Document doc = Jsoup.parse(connection.getInputStream(), "UTF-8", connection.getURL().getPath());

			Elements tbodys = doc.select("tbody[id*=thread_]");				// each thread row have that id
			Iterator<Element> tbIterator = tbodys.iterator();

			while (tbIterator.hasNext()) {
				Element e = tbIterator.next();
				results.add(new ForumThread(connection.getURL().getHost() + "/" + e.select("span[id^=thread_] a[href^=thread-]").attr("href"),
						e.select("span[id^=thread] a[href^=thread-]").text(),
						sdf.parse(e.select("td.author em").text())));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return results;
	}

}
