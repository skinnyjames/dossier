(ns dossier.api.authentication
  (:require [dossier.database.queries :as q]
            [dossier.middleware :refer :all]
            [dossier.lib.validators :as validator]
            [environ.core :refer [env]]
            [compojure.core :refer :all]
            [buddy.hashers :as hs]
            [compojure.route :as route]
            [cheshire.core :refer :all]))

(defn set-cookie [session-id, message]
  {:headers {"Content-Type" "Set-Cookie"},
   :cookies {"session" { :value session-id :path "/" }},
   :body  message })

(defn register-user [user-body]
  (let [password (hs/encrypt (get user-body :password))
        user-id-record (q/create-user q/db { :email (get user-body :email-address) :password password })
        uuid-record    (q/create-session q/db { :id (get user-id-record :id) })]
        (get uuid-record :uuid)))

(defn register-user-and-set-cookie [user-body]
  (let [session (register-user user-body)]
    (set-cookie session (str "Thanks! " (get user-body :email-address)))))

(defn email-to-session [email]
  (let [user (q/get-user-by-email q/db { :email email })
        user-id (get user :id)
        _ (q/delete-user-session q/db { :id user-id})
        session (q/create-session q/db { :id user-id})]
    (get session :uuid)))

(defroutes auth-routes
  ; when body is a hashmap or arraymap, return { body: % }
  (POST "/register" { body :body }
    (let [errors (validator/registration body)]
      (if (empty? errors)
        (register-user-and-set-cookie body)
        { :status 400 :body errors })))
  (POST "/login" { body :body }
    (let [errors (validator/login body)]
      (if (empty? errors)
        (set-cookie (email-to-session (get body :email-address)) "You are logged in")
        { :status 400 :body errors })))
  (wrap-current-user
    (GET "/logout" { user :user }
      (q/delete-user-session q/db { :id (get user :id) })
      "You are logged out")))