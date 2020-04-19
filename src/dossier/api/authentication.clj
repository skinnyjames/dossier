(ns dossier.api.authentication
  (:require [dossier.database.queries :as q]
            [dossier.lib.validators :as validator]
            [environ.core :refer [env]]
            [compojure.core :refer :all]
            [buddy.hashers :as hs]
            [compojure.route :as route]
            [cheshire.core :refer :all]))

(defn register-user [user-body]
  (let [password (hs/encrypt (get user-body :password))
        user-id-record (q/create-user q/db { :email (get user-body :email-address) :password password })
        uuid-record    (q/create-session q/db { :id (get user-id-record :id) })]
        (get uuid-record :uuid)))

(defn register-user-and-set-cookie [user-body]
  (let [session (register-user user-body)]
       {:headers {"Content-Type" "Set-Cookie"},
        :cookies {"session" { :value session :path "/" }},
        :body (str "Thanks! " (get user-body :email-address)) }))

(defroutes auth-routes
  ; when body is a hashmap or arraymap, return { body: % }
  (POST "/" { body :body }
    (let [errors (validator/registration body)]
      (if (empty? errors)
        (register-user-and-set-cookie body)
        { :status 400 :body errors }))))