--1587123996-reports.up.sql
CREATE TABLE reports(
  id serial PRIMARY KEY,
  application_id INTEGER references applications(id),
  data jsonb NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);