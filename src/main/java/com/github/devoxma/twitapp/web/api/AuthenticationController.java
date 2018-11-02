package com.github.devoxma.twitapp.web.api;

import com.github.devoxma.twitapp.domain.model.User;
import com.github.devoxma.twitapp.security.Authenticated;
import com.github.devoxma.twitapp.security.Security;
import com.github.devoxma.twitapp.web.dto.LoginDto;
import com.github.devoxma.twitapp.web.dto.UserDto;
import com.github.devoxma.twitapp.web.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
public class AuthenticationController {

	private final AuthenticationService authenticationService;

	@Autowired
	public AuthenticationController(AuthenticationService authenticationService) {
		this.authenticationService = authenticationService;
	}

	@GetMapping("/api/me")
	@Security
	public UserDto getMe(@Authenticated User me) {
		return authenticationService.me(me);
	}

	@PostMapping("/api/login")
	public void authenticate(@RequestBody @Valid LoginDto loginDto, HttpServletResponse response) {
		authenticationService.login(loginDto, response);
	}

	@PostMapping("/api/logout")
	@Security
	public void logout(HttpServletResponse response) {
		authenticationService.logout(response);
	}
}
