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
@RequestMapping("/comic")
public class ComicController {
	private static Logger log = org.slf4j.LoggerFactory.getLogger(ComicController.class);

	@Resource private GrepForumContentService service;

	@RequestMapping(value="/package", method = RequestMethod.GET)
	public String list(HttpServletRequest request, HttpServletResponse response, ModelMap model) {		
		
		
		return "Done";
	}
}
