package com.github.devoxma.twitapp.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "users")
public class User {

	@Id
	private String id;
	private Date creationDate;
	private String login;

	@JsonIgnore
	private String password;

	private User() {
	}

	private User(String id, Date creationDate, String login, String password) {
		this.id = id;
		this.creationDate = creationDate;
		this.login = login;
		this.password = password;
	}

	public String getId() {
		return id;
	}

	public String getKey() {
		return id;
	}

	public String getLogin() {
		return login;
	}

	public String getPassword() {
		return password;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public static class Builder {
		private String id;
		private Date creationDate;
		private String login;
		private String password;

		public Builder() {
			this.creationDate = new Date();
		}

		public Builder withId(String id) {
			this.id = id;
			return this;
		}

		public Builder withLogin(String login) {
			this.login = login;
			return this;
		}

		public Builder withPassword(String password) {
			this.password = password;
			return this;
		}

		public Builder withCreationDate(Date creationDate) {
			this.creationDate = creationDate;
			return this;
		}

		public User build() {
			return new User(id, creationDate, login, password);
		}
	}
}
