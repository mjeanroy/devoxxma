package com.github.devoxma.twitapp.security.service;

import com.github.devoxma.twitapp.domain.model.User;
import com.github.devoxma.twitapp.domain.repository.LoginRepository;
import com.github.devoxma.twitapp.security.parsers.TokenParser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class SecurityService {
	private final List<TokenParser> tokenParsers;
	private final LoginRepository loginRepository;

	public SecurityService(List<TokenParser> tokenParsers, LoginRepository loginRepository) {
		this.tokenParsers = tokenParsers;
		this.loginRepository = loginRepository;
	}

	public void authenticate(HttpServletResponse response, User user) {
		String login = user.getLogin();
		tokenParsers.forEach(tokenParser -> tokenParser.put(response, login, false));
	}

	public void logout(HttpServletResponse response) {
		tokenParsers.forEach(tokenParser -> tokenParser.remove(response));
	}

	public Optional<User> login(HttpServletRequest request) {
		return tokenParsers.stream()
				.map(tokenParser -> tokenParser.parse(request))
				.filter(Objects::nonNull)
				.map(loginRepository::findByLogin)
				.filter(Optional::isPresent)
				.map(Optional::get)
				.findFirst();
	}
}

