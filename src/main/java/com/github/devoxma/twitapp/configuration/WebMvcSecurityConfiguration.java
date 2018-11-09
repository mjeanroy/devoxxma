package com.github.devoxma.twitapp.configuration;

import com.github.devoxma.twitapp.security.interceptors.SecurityInterceptor;
import com.github.devoxma.twitapp.security.resolver.AuthenticatedUserArgumentResolver;
import com.github.devoxma.twitapp.security.interceptors.PrincipalInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

@Configuration
public class WebMvcSecurityConfiguration extends WebMvcConfigurerAdapter {

	private static final Logger log = LoggerFactory.getLogger(WebMvcSecurityConfiguration.class);

	private final PrincipalInterceptor principalInterceptor;
	private final SecurityInterceptor securityInterceptor;
	private final AuthenticatedUserArgumentResolver authenticatedUserArgumentResolver;

	@Autowired
	public WebMvcSecurityConfiguration(
			SecurityInterceptor securityInterceptor,
			PrincipalInterceptor principalInterceptor,
			AuthenticatedUserArgumentResolver authenticatedUserArgumentResolver) {

		this.principalInterceptor = principalInterceptor;
		this.securityInterceptor = securityInterceptor;
		this.authenticatedUserArgumentResolver = authenticatedUserArgumentResolver;
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		log.info("Adding security resolvers to the MVC Registry");
		argumentResolvers.add(authenticatedUserArgumentResolver);
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		log.info("Adding security interceptors to the MVC Registry");
		registry.addInterceptor(principalInterceptor);
		registry.addInterceptor(securityInterceptor);
	}
}
