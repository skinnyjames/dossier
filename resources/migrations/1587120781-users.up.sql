--1587120781-users.up.sql
CREATE TABLE users(
  id serial PRIMARY KEY,
  email VARCHAR (50) UNIQUE NOT NULL,
  password_hash VARCHAR (50) NOT NULL,
  password_salt VARCHAR (50) NOT NULL,
  created_on TIMESTAMP NOT NULL
);