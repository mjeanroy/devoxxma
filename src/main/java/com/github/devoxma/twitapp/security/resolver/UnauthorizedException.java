package com.github.devoxma.twitapp.security.resolver;

class UnauthorizedException extends RuntimeException {

	UnauthorizedException() {
		super("Cannot perform action");
	}
}
