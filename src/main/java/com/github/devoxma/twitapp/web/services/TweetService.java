package com.github.devoxma.twitapp.web.services;

import com.github.devoxma.twitapp.domain.model.Tweet;
import com.github.devoxma.twitapp.domain.model.User;
import com.github.devoxma.twitapp.domain.repository.TweetRepository;
import com.github.devoxma.twitapp.domain.repository.UserRepository;
import com.github.devoxma.twitapp.web.dto.TweetDto;
import com.github.devoxma.twitapp.web.mappers.TweetMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

@Service
public class TweetService {

	private static final Logger log = LoggerFactory.getLogger(TweetService.class);

	private final UserRepository userRepository;
	private final TweetRepository tweetRepository;
	private final TweetMapper tweetMapper;

	@Autowired
	public TweetService(UserRepository userRepository, TweetRepository tweetRepository, TweetMapper tweetMapper) {
		this.userRepository = userRepository;
		this.tweetMapper = tweetMapper;
		this.tweetRepository = tweetRepository;
	}

	public List<TweetDto> findAll() {
		log.info("Fetch Tweets");
		List<Tweet> tweets = tweetRepository.findAll();
		return tweetMapper.map(tweets);
	}

	public TweetDto create(@RequestBody @Valid TweetDto tweet) {
		log.info("Save new tweet: {}", tweet);

		String login = tweet.getLogin();
		User user = userRepository.findByLogin(login);
		Tweet result = tweetRepository.save(user, tweet.getMessage());
		return tweetMapper.map(result);
	}
}
