package com.github.devoxma.twitapp.domain.repository;

import com.github.devoxma.twitapp.domain.model.Tweet;
import com.github.devoxma.twitapp.domain.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class TweetRepository {

	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public TweetRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<Tweet> findAll() {
		String sql =
				"SELECT " +
						"t.id as id, " +
						"t.creation_date as creation_date, " +
						"t.message as message, " +
						"u.id as user_id, " +
						"u.login as user_login " +
						"FROM tweets t " +
						"INNER JOIN users u ON t.user_id = u.id " +
						"ORDER BY t.creation_date DESC";

		return jdbcTemplate.query(sql, TWEET_ROW_MAPPER);
	}

	public Tweet findById(Long id) {
		String sql =
				"SELECT " +
						"t.id as id, " +
						"t.creation_date as creation_date, " +
						"t.message as message, " +
						"u.id as user_id, " +
						"u.login as user_login " +
						"FROM tweets t " +
						"INNER JOIN users u ON t.user_id = u.id " +
						"WHERE t.id = " + id;

		return jdbcTemplate.queryForObject(sql, TWEET_ROW_MAPPER);
	}

	public Tweet save(User user, String message) {
		Tweet tweet = new Tweet.Builder()
				.withGeneratedId()
				.withCreationDate(new Date())
				.withUser(user)
				.withMessage(message)
				.build();

		String sql =
				"INSERT INTO tweets(id, creation_date, message, user_id) " +
						"VALUES ('" + tweet.getId() + "', " + tweet.getCreationDate().getTime() + ", '" + tweet.getMessage() + "', " + tweet.getUser().getId() + ")";

		jdbcTemplate.update(sql);

		return tweet;
	}

	private static final RowMapper<Tweet> TWEET_ROW_MAPPER = (rs, rowNum) -> new Tweet.Builder()
			.withId(rs.getString("id"))
			.withCreationDate(rs.getTimestamp("creation_date"))
			.withMessage(rs.getString("message"))
			.withUser(new User.Builder()
					.withId(rs.getString("user_id"))
					.withLogin(rs.getString("user_login"))
					.build())
			.build();
}