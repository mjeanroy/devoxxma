package com.github.devoxma.twitapp.web.api;

import com.github.devoxma.twitapp.web.dto.TweetDto;
import com.github.devoxma.twitapp.web.services.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/tweets")
public class TweetController {

	private final TweetService tweetService;

	@Autowired
	public TweetController(TweetService tweetService) {
		this.tweetService = tweetService;
	}

	@GetMapping
	public List<TweetDto> fetch() {
		return tweetService.findAll();
	}

	@PostMapping
	public TweetDto create(@RequestBody @Valid TweetDto tweet) {
		return tweetService.create(tweet);
	}
}
