--1587123473-sessions.up.sql
CREATE EXTENSION "uuid-ossp";

CREATE TABLE sessions(
  uuid varchar(80) DEFAULT uuid_generate_v4() NOT NULL,
  user_id INTEGER REFERENCES users(id) NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP 
);