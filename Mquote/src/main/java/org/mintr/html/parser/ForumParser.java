package org.mintr.html.parser;

import java.util.List;

import org.mintr.entity.ForumThread;

public interface ForumParser {
	public List<ForumThread> getForumThreads(String url);
}
