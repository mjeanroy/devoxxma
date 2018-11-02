package com.github.devoxma.twitapp.web.mappers;

import com.github.devoxma.twitapp.domain.model.User;
import com.github.devoxma.twitapp.web.dto.UserDto;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

	public UserDto map(User user) {
		UserDto dto = new UserDto();
		dto.setId(user.getId());
		dto.setLogin(user.getLogin());
		return dto;
	}
}
