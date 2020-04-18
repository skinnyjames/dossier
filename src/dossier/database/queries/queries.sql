-- :name get-users :? :*
-- :doc get a list of users
select * from users

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

-- :name create-user :<! :1
-- :doc create user
insert into users (email, password)
values (:email, :password) returning id

-- :name create-session :<! :1
-- :doc creates a session and returns the uuid
insert into sessions (user_id) values (:id) returning uuid;