(ns dossier.lib.validators
  (:require [metis.core :refer :all]
            [buddy.hashers :as hs]
            [dossier.database.queries :as q]))

(defn not-duplicate-email [map key _]
  (let [val (get map key)
        exists? (q/get-user-by-email q/db { :email val })]
    (when exists? "Email already exists")))

(defvalidator registration
  [:email-address :email]
  [:email-address :not-duplicate-email]
  [:password :presence]
  [:password :confirmation { :confirm :password-confirmation }])


(defn valid-password [map, _ _]
  (let [email (get map :email-address)
        pass-for-email (get (q/get-password-by-email q/db { :email email }) :password)
        correct? (hs/check (get map :password) pass-for-email)]
    (when-not correct? "Incorrect password")))

(defn user-exists [map, key _]
  (let [val (get map key)
        exists? (q/get-user-by-email q/db { :email val })]
    (when-not exists? "Email doesn't exist")))


(defvalidator login
  [:email-address :presence]
  [:email-address :user-exists]
  [:password :presence]
  [:password :valid-password])

