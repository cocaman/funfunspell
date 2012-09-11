package org.mintr.html.parser
import org.mintr.entity.ForumThread
import java.util.ArrayList
import java.text.SimpleDateFormat
import java.util.List
import java.net.HttpURLConnection
import org.mintr.util.HttpURLConnectionBuilder
import org.jsoup.Jsoup
import scala.collection.JavaConversions._

class TvboxnowContentParser extends ForumParser {

	override def getForumThreads(url : String) : List[ForumThread] = {
		val results = new ArrayList[ForumThread]
		val sdf = new SimpleDateFormat("yyyy-M-d");

		try
		{
			val connection = new HttpURLConnectionBuilder().setURL(url).createConnection();
			val doc = Jsoup.parse(connection.getInputStream(), "UTF-8", connection.getURL().getPath());

			val tbodys = doc.select("tbody[id*=thread_]");				// each thread row have that id
			for (e <- tbodys.iterator()) {				
				results.add(new ForumThread(connection.getURL().getHost() + "/" + e.select("span[id^=thread_] a[href^=thread-]").attr("href"),
						e.select("span[id^=thread] a[href^=thread-]").text(),
						sdf.parse(e.select("td.author em").text())));
			}	
			
		} catch {
			case ex : Exception => ex.printStackTrace()
		}
		results
	}

}