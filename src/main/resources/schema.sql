--
-- Simple DATABASE
--

CREATE TABLE users (
  id varchar(100) PRIMARY KEY,
  creation_date DATE NOT NULL,
  login varchar(500) NOT NULL,
  password varchar(500) NOT NULL
);

CREATE TABLE tweets (
  id varchar(100) PRIMARY KEY ,
  creation_date DATE NOT NULL,
  message varchar(500) NOT NULL,
  user_id varchar(100) NOT NULL
);
