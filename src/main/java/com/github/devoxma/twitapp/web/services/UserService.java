package com.github.devoxma.twitapp.web.services;

import com.github.devoxma.twitapp.domain.model.User;
import com.github.devoxma.twitapp.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

@Service
public class UserService {

	private final UserRepository userRepository;

	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public List<User> findAll() {
		return newArrayList(userRepository.findAll());
	}
}
