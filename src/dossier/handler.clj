(ns dossier.handler
  (:require [dossier.api.users :refer :all]
            [cheshire.core :refer :all]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

(defroutes app-routes
  (GET "/api" [] (generate-string {:hello "world"}))
  (context "/api/user" [] (-> user-routes))
  (route/not-found "Not Found"))

(def app
  (wrap-defaults app-routes site-defaults))
