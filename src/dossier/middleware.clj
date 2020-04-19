(ns dossier.middleware
  (:require [dossier.database.queries :as q]))

(defn user-does-not-exist [id]
  (empty? (q/get-user-from-session q/db {:id id})))

(defn add-user-to-request [request, id]
  (assoc request :user (q/get-user-from-session q/db {:id id } {:quoting :ansi})))

(defn wrap-current-user [handler]
  (fn [request]
    (let [id (get-in request [:cookies "session" :value])]
      (if (or (nil? id) (user-does-not-exist id))
        (-> { :status 401 :body "Unauthorized" })
        (handler (add-user-to-request request id))))))
