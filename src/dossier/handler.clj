(ns dossier.handler
  (:require [dossier.api.users :refer :all]
            [dossier.api.authentication :refer :all]
            [dossier.middleware :refer :all]
            [cheshire.core :refer :all]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [dossier.database.queries :as q]
            [environ.core :refer [env]]
            [ring.logger :as logger]
            [ring.adapter.jetty]
            [ring.middleware.reload :refer [wrap-reload]]
            [ring.middleware.cookies :refer :all]
            [ring.middleware.json :refer [wrap-json-body wrap-json-response]]
            [ring.middleware.defaults :refer [wrap-defaults api-defaults]]))


(defroutes app-routes
  (GET "/api" request (generate-string (str request)))
  (context "/auth" [] (-> auth-routes))
  (context "/user" [] (-> user-routes (wrap-current-user)))
  (route/not-found "Not Found"))

(def app
  (-> app-routes
      (wrap-reload '(dossier.handler view))
      logger/wrap-log-request-start
      wrap-cookies
      (wrap-json-body { :keywords? true })
      wrap-json-response))

(defn -main [] 
  (ring.adapter.jetty/run-jetty (app) {:port 3000 :join? false}))