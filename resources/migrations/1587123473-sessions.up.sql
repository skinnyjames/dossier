--1587123473-sessions.up.sql
CREATE EXTENSION "uuid-ossp";

CREATE TABLE sessions(
  uuid uuid DEFAULT uuid_generate_v4(),
  user_id INTEGER REFERENCES users(id),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP 
);