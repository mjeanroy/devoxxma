package com.github.devoxma.twitapp.configuration;

import com.github.devoxma.twitapp.domain.repository.LoginRepository;
import com.github.devoxma.twitapp.security.encoders.Base64TokenEncoder;
import com.github.devoxma.twitapp.security.encoders.TokenEncoder;
import com.github.devoxma.twitapp.security.interceptors.SecurityInterceptor;
import com.github.devoxma.twitapp.security.parsers.AuthHeaderTokenParser;
import com.github.devoxma.twitapp.security.parsers.CookieTokenParser;
import com.github.devoxma.twitapp.security.parsers.TokenParser;
import com.github.devoxma.twitapp.security.resolver.AuthenticatedUserArgumentResolver;
import com.github.devoxma.twitapp.security.service.SecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SecurityConfiguration {

	private static final String SECURITY_HEADER = "Mix-Twitt";
	private static final String SECURITY_COOKIE = "Mix-Twitt";

	private static final Logger log = LoggerFactory.getLogger(SecurityConfiguration.class);

	@Bean
	public TokenEncoder base64TokenEncoder() {
		log.info("Create token encoder");
		return new Base64TokenEncoder();
	}

	@Bean
	public TokenParser headerTokenParser(TokenEncoder encoder) {
		log.info("Create token parser");
		return new AuthHeaderTokenParser(SECURITY_HEADER, encoder);
	}

	@Bean
	public TokenParser cookieTokenParser(TokenEncoder encoder) {
		log.info("Create token parser");
		return new CookieTokenParser(SECURITY_COOKIE, encoder);
	}

	@Bean
	public SecurityService securityService(List<TokenParser> tokenParsers, LoginRepository loginRepository) {
		log.info("Create security service");
		return new SecurityService(tokenParsers, loginRepository);
	}

	@Bean
	public AuthenticatedUserArgumentResolver authenticatedUserArgumentResolver(SecurityService securityService) {
		return new AuthenticatedUserArgumentResolver(securityService);
	}

	@Bean
	public SecurityInterceptor securityInterceptor(SecurityService securityService) {
		log.info("Create security interceptor");
		return new SecurityInterceptor(securityService);
	}
}
