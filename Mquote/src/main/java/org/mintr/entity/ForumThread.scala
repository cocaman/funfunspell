package org.mintr.entity
import java.util.Date
import scala.reflect.BeanProperty

class ForumThread(inputUrl:String, inputTitle:String, inputDate:Date) {
	@scala.reflect.BeanProperty var url = inputUrl	
	@scala.reflect.BeanProperty var title = inputTitle
	@scala.reflect.BeanProperty var date = inputDate
		
	@Override
	override def toString = {
		String.format("ForumThread [url=%s, title=%s, date=%s]", url, title, date)
	}	
}