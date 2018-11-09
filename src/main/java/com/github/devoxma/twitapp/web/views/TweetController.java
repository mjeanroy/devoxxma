package com.github.devoxma.twitapp.web.views;

import com.github.devoxma.twitapp.domain.model.User;
import com.github.devoxma.twitapp.security.Authenticated;
import com.github.devoxma.twitapp.web.dto.TweetDto;
import com.github.devoxma.twitapp.web.exceptions.InvalidTweetLoginException;
import com.github.devoxma.twitapp.web.services.TweetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class TweetController {

	private static final Logger log = LoggerFactory.getLogger(TweetController.class);

	private final TweetService tweetService;

	@Autowired
	public TweetController(TweetService tweetService) {
		this.tweetService = tweetService;
	}

	@GetMapping("/tweets")
	public ModelAndView tweets() {
		log.debug("Render tweets");

		ModelAndView mv = new ModelAndView("tweets");
		mv.addObject("tweets", tweetService.findAll());
		return mv;
	}

	@PostMapping("/tweets")
	public String postTweet(@Authenticated User principal, @Valid TweetDto tweet) {
		if (!principal.getLogin().equals(tweet.getLogin())) {
			throw new InvalidTweetLoginException();
		}

		tweetService.create(tweet);
		return "redirect:/tweets";
	}
}
