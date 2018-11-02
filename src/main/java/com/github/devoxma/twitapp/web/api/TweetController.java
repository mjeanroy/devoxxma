package com.github.devoxma.twitapp.web.api;

import com.github.devoxma.twitapp.domain.model.Tweet;
import com.github.devoxma.twitapp.domain.model.User;
import com.github.devoxma.twitapp.domain.repository.TweetRepository;
import com.github.devoxma.twitapp.domain.repository.UserRepository;
import com.github.devoxma.twitapp.web.dto.TweetDto;
import com.github.devoxma.twitapp.web.mappers.TweetMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/tweets")
public class TweetController {
	private static final Logger log = LoggerFactory.getLogger(TweetController.class);

	private final TweetRepository tweetRepository;
	private final UserRepository userRepository;
	private final TweetMapper tweetMapper;

	@Autowired
	public TweetController(UserRepository userRepository, TweetRepository tweetRepository, TweetMapper tweetMapper) {
		this.userRepository = userRepository;
		this.tweetRepository = tweetRepository;
		this.tweetMapper = tweetMapper;
	}

	@GetMapping
	public List<TweetDto> fetch() {
		log.info("Fetch Tweets");
		List<Tweet> tweets = tweetRepository.findAll();
		return tweetMapper.map(tweets);
	}

	@PostMapping
	public TweetDto create(@RequestBody @Valid TweetDto tweet) {
		log.info("Save new tweet: {}", tweet);

		String login = tweet.getLogin();
		User user = userRepository.findByLogin(login);
		Tweet result = tweetRepository.save(user, tweet.getMessage());
		return tweetMapper.map(result);
	}
}
