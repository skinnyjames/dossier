(ns dossier.data-test
  (:require [dossier.database.queries :as q]
            [dossier.api.authentication :as auth]))

(def registration
  { :email-address "bob@xyz.com" :password "Hello" :password-confirmation "Hello" })

(def login
  { :email-address "bob@xyz.com" :password "Hello" })

(defn register-user-and-return-session []
  (auth/register-user registration))