package com.github.devoxma.twitapp.web.api;

import com.github.devoxma.twitapp.domain.model.User;
import com.github.devoxma.twitapp.security.Authenticated;
import com.github.devoxma.twitapp.security.Security;
import com.github.devoxma.twitapp.web.dto.TweetDto;
import com.github.devoxma.twitapp.web.exceptions.InvalidTweetLoginException;
import com.github.devoxma.twitapp.web.services.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/tweets")
public class TweetApiController {

	private final TweetService tweetService;

	@Autowired
	public TweetApiController(TweetService tweetService) {
		this.tweetService = tweetService;
	}

	@GetMapping
	public List<TweetDto> fetch() {
		return tweetService.findAll();
	}

	@PostMapping
	@Security
	public TweetDto create(@Authenticated User principal, @RequestBody @Valid TweetDto tweet) {
		if (!principal.getLogin().equals(tweet.getLogin())) {
			throw new InvalidTweetLoginException();
		}

		return tweetService.create(tweet);
	}
}
