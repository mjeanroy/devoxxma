package com.github.devoxma.twitapp.security.interceptors;

import com.github.devoxma.twitapp.domain.model.User;
import com.github.devoxma.twitapp.security.Security;
import com.github.devoxma.twitapp.security.service.SecurityService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.springframework.core.annotation.AnnotationUtils.findAnnotation;

public class SecurityInterceptor extends HandlerInterceptorAdapter {

	private static final Set<String> METHOD_REQUIRES_AUTH = new HashSet<String>() {{
		add("GET");
		add("PUT");
		add("POST");
		add("PATCH");
		add("DELETE");
	}};

	private final SecurityService securityService;

	public SecurityInterceptor(SecurityService securityService) {
		this.securityService = securityService;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		if (!METHOD_REQUIRES_AUTH.contains(request.getMethod().toUpperCase())) {
			return true;
		}

		if (shouldAuthenticate(handler)) {
			Optional<User> user = securityService.login(request);
			boolean isAuthenticated = user.isPresent();

			if (!isAuthenticated) {
				if (isApiCall(handler)) {
					response.setStatus(HttpStatus.UNAUTHORIZED.value());
				} else {
					response.setStatus(HttpStatus.TEMPORARY_REDIRECT.value());
					response.setHeader("Location", "/login");
				}
			}

			return isAuthenticated;
		}

		return true;
	}

	private static boolean isApiCall(Object handler) {
		if (isHandlerMethod(handler)) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			Method method = handlerMethod.getMethod();
			Class<?> klass = handlerMethod.getBeanType();
			return findAnnotation(method, ResponseBody.class) != null || findAnnotation(klass, ResponseBody.class) != null;
		}

		return false;
	}

	private static boolean shouldAuthenticate(Object handler) {
		if (!isHandlerMethod(handler)) {
			return false;
		}

		HandlerMethod handlerMethod = (HandlerMethod) handler;
		return handlerMethod.hasMethodAnnotation(Security.class) || handlerMethod.getBean().getClass().isAnnotationPresent(Security.class);
	}

	private static boolean isHandlerMethod(Object handler) {
		return handler instanceof HandlerMethod;
	}
}
