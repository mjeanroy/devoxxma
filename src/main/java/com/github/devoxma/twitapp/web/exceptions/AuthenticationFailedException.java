package com.github.devoxma.twitapp.web.exceptions;

public class AuthenticationFailedException extends RuntimeException {

	private final String login;

	public AuthenticationFailedException(String login) {
		super(createMessage());
		this.login = login;
	}

	public String login() {
		return login;
	}

	private static String createMessage() {
		return "Authentication failed, please check your credentials";
	}
}
