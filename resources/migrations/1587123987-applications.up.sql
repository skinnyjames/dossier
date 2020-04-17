--1587123987-applications.up.sql
CREATE TYPE frameworks AS ENUM(
  'RubyCucumber'
);

CREATE TABLE applications (
  id serial PRIMARY KEY,
  user_id INTEGER REFERENCES users(id),
  framework frameworks,
  name VARCHAR(100) NOT NULL,
  token VARCHAR(100) NOT NULL
);