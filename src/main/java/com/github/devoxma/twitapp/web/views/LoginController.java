package com.github.devoxma.twitapp.web.views;

import com.github.devoxma.twitapp.security.Security;
import com.github.devoxma.twitapp.web.dto.LoginDto;
import com.github.devoxma.twitapp.web.services.AuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
public class LoginController {

	private static final Logger log = LoggerFactory.getLogger(LoginController.class);

	private final AuthenticationService authenticationService;

	@Autowired
	public LoginController(AuthenticationService authenticationService) {
		this.authenticationService = authenticationService;
	}

	@GetMapping("/login")
	public ModelAndView mv() {
		return new ModelAndView("login");
	}

	@PostMapping("/login")
	@Security
	public String doLogin(@Valid LoginDto loginDto, HttpServletResponse response) {
		log.debug("Processing login authentication");
		authenticationService.login(loginDto, response);
		return "redirect:/tweets";
	}
}
