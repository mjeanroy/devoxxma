package com.github.devoxma.twitapp.web.mappers;

import com.github.devoxma.twitapp.domain.model.Tweet;
import com.github.devoxma.twitapp.web.dto.TweetDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TweetMapper {

	public List<TweetDto> map(List<Tweet> tweets) {
		return tweets.stream().map(this::map).collect(Collectors.toList());
	}

	public TweetDto map(Tweet tweet) {
		TweetDto dto = new TweetDto();
		dto.setId(tweet.getId());
		dto.setCreationDate(tweet.getCreationDate());
		dto.setMessage(tweet.getMessage());
		dto.setLogin(tweet.getUser().getLogin());
		return dto;
	}
}
