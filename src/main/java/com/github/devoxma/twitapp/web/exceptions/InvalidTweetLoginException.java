package com.github.devoxma.twitapp.web.exceptions;

public class InvalidTweetLoginException extends RuntimeException {

	public InvalidTweetLoginException() {
		super(createMessage());
	}

	private static String createMessage() {
		return "Login is invalid and does not match authenticated user";
	}
}
