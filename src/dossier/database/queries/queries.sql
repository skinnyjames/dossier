-- :name get-users :? :*
-- :doc get a list of users
select * from users

-- :name delete-user-session :!
delete from sessions as s
where s.user_id = :id

-- :name get-session-from-email :? :1
select s.uuid from sessions as s
inner join users as u
on u.id = s.user_id
where u.email = :email

-- :name get-user-from-session :? :1
-- :doc get a single user by it's session value
select users.email, users.id from users
inner join sessions
on users.id = sessions.user_id
where sessions.uuid = :id limit 1

-- :name get-user-by-email :? :1
-- :doc fetches a user by it's email
select id from users
where email = :email
limit 1

-- :name get-password-by-email :? :1
-- :doc grabs a pass from an email
select password from users
where email = :email
limit 1

-- :name create-user :<! :1
-- :doc create user
insert into users (email, password)
values (:email, :password) returning id

-- :name create-session :<! :1
-- :doc creates a session and returns the uuid
insert into sessions (user_id) values (:id) returning uuid;

-- :name get-applications :? :n
-- :doc returns all the applications for a given user
select a.* from applications as a
where a.user_id = :id

-- :name create-application :<! :1
-- :doc creates an application and returns the id
insert into applications (user_id, name, framework)
values (:id, :name, :framework) returning id

-- :name get-reports :? :n
-- :doc returns reports for a given application
select * from reports
where application_id = :id

