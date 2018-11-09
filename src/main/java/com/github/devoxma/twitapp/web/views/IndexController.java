package com.github.devoxma.twitapp.web.views;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {

	private static final Logger log = LoggerFactory.getLogger(IndexController.class);

	@GetMapping("/")
	public ModelAndView index() {
		log.debug("Render index view");
		return new ModelAndView("login");
	}
}
