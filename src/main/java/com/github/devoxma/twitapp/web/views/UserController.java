package com.github.devoxma.twitapp.web.views;

import com.github.devoxma.twitapp.web.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {

	private final UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/users")
	public ModelAndView showUsers() {
		ModelAndView mv = new ModelAndView("users");
		mv.addObject("users", userService.findAll());
		return mv;
	}
}
