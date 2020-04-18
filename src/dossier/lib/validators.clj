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

