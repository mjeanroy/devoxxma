package com.github.devoxma.twitapp.web.views;

import com.github.devoxma.twitapp.security.Security;
import com.github.devoxma.twitapp.web.services.AuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletResponse;

@Controller
public class LogoutController {

	private static final Logger log = LoggerFactory.getLogger(LogoutController.class);

	private final AuthenticationService authenticationService;

	@Autowired
	public LogoutController(AuthenticationService authenticationService) {
		this.authenticationService = authenticationService;
	}

	@GetMapping("/logout")
	@PostMapping("/logout")
	@Security
	public String mv(HttpServletResponse response) {
		log.debug("Processing logout");
		authenticationService.logout(response);
		return "redirect:/";
	}
}
