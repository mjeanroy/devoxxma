package com.github.devoxma.twitapp.web.services;

import com.github.devoxma.twitapp.domain.model.User;
import com.github.devoxma.twitapp.domain.repository.LoginRepository;
import com.github.devoxma.twitapp.security.service.SecurityService;
import com.github.devoxma.twitapp.web.dto.LoginDto;
import com.github.devoxma.twitapp.web.dto.UserDto;
import com.github.devoxma.twitapp.web.exceptions.AuthenticationFailedException;
import com.github.devoxma.twitapp.web.mappers.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;

@Service
public class AuthenticationService {
	private final LoginRepository loginRepository;
	private final UserMapper userMapper;
	private final SecurityService securityService;

	@Autowired
	public AuthenticationService(LoginRepository loginRepository, UserMapper userMapper, SecurityService securityService) {
		this.loginRepository = loginRepository;
		this.userMapper = userMapper;
		this.securityService = securityService;
	}

	@Transactional(readOnly = true)
	public UserDto me(User me) {
		return userMapper.map(me);
	}

	@Transactional(readOnly = true)
	public void login(LoginDto loginDto, HttpServletResponse response) {
		User user = loginRepository.findByLoginAndPassword(loginDto.getLogin(), loginDto.getPassword()).orElseThrow(() ->
				new AuthenticationFailedException(loginDto.getLogin())
		);

		securityService.authenticate(response, user);
	}

	@Transactional(readOnly = true)
	public void logout(HttpServletResponse response) {
		securityService.logout(response);
	}
}
