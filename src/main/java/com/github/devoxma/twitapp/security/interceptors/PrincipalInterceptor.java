package com.github.devoxma.twitapp.security.interceptors;

import com.github.devoxma.twitapp.security.service.SecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class PrincipalInterceptor extends HandlerInterceptorAdapter {

	private static final Logger log = LoggerFactory.getLogger(PrincipalInterceptor.class);

	private final SecurityService securityService;

	@Autowired
	public PrincipalInterceptor(SecurityService securityService) {
		this.securityService = securityService;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
		log.debug("Processing request: {}", request.getRequestURI());

		if (!isTemplateView(modelAndView)) {
			log.debug("Skipping interceptor since rendered view is not a template, probably a simple API call");
			return;
		}

		log.debug("Checking for authenticated user");

		modelAndView.addObject("principal", null);

		securityService.login(request).ifPresent(principal -> {
			log.debug("Injecting principal to rendered view");
			modelAndView.addObject("principal", principal);
		});
	}

	private static boolean isTemplateView(ModelAndView mv) {
		if (mv == null) {
			return false;
		}

		String viewName = mv.getViewName();
		return viewName != null && !viewName.isEmpty();
	}
}
