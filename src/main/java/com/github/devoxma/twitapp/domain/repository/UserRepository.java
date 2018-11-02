package com.github.devoxma.twitapp.domain.repository;

import com.github.devoxma.twitapp.domain.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface UserRepository extends PagingAndSortingRepository<User, String> {

	User findByLogin(String login);
}
