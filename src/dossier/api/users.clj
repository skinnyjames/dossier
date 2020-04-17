(ns dossier.api.users
  (:require [dossier.database.queries :as q]
            [environ.core :refer [env]]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [cheshire.core :refer :all]))

(defroutes user-routes
  (GET "/" [] (generate-string 
    (q/get-users (env :database-url)))))