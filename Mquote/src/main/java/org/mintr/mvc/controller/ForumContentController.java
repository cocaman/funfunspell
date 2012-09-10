package org.mintr.mvc.controller;

import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mintr.BeanUtil;
import org.mintr.entity.ForumThread;
import org.mintr.html.service.GrepForumContentService;
import org.mintr.html.service.GrepForumContentService.ContentType;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/forum")
public class ForumContentController {
	private static Logger log = org.slf4j.LoggerFactory.getLogger(ForumContentController.class);

	@Resource private GrepForumContentService service;

	@RequestMapping(value="/list", method = RequestMethod.GET)
	public String list(@RequestParam(value="type", required = false, defaultValue = "MUSIC") String type,
			@RequestParam(value="page", required=false, defaultValue = "1") String page,
			HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		log.debug("list: type [{}], page [{}]", type, page);

		List<ForumThread> contents = service.grepForumContentByType(ContentType.valueOf(type), Integer.valueOf(page));
		Collections.sort(contents, BeanUtil.getCompare("getDate"));
		Collections.reverse(contents);

		// set response model
		model.put("contents", contents);

		return "forumcontent";
	}
}
