(ns dossier.api.users
  (:require [dossier.database.queries :as q]
            [environ.core :refer [env]]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [cheshire.core :refer :all]))

(defroutes user-routes
  (GET "/" { user :user } user)
  (GET "/test" request {:body (str request)})
  (GET "/:id/apps" response
    (let [user_id (get-in response [:params :id])
          current_user_id (get-in response [:user :id])]
          (if (= (str user_id) (str current_user_id))
            (generate-string (q/get-applications q/db {:id current_user_id }))
            { :status 401 :body "Unauthorized Fool" }))))