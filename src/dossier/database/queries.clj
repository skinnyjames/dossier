(ns dossier.database.queries 
 (:require [hugsql.core :as hugsql]
           [environ.core :refer [env]]))

(defn log-sqlvec [sqlvec]
  (println (->> sqlvec
              (map #(clojure.string/replace (or % "") #"\n" ""))
              (clojure.string/join " ; "))))

(defn log-command-fn [this db sqlvec options]
  (log-sqlvec sqlvec)
  (condp contains? (:command options)
    #{:!} (hugsql.adapter/execute this db sqlvec options)
    #{:? :<!} (hugsql.adapter/query this db sqlvec options)))

(defmethod hugsql.core/hugsql-command-fn :! [sym] `log-command-fn)
(defmethod hugsql.core/hugsql-command-fn :<! [sym] `log-command-fn)
(defmethod hugsql.core/hugsql-command-fn :? [sym] `log-command-fn)

(def db (env :database-url))
(hugsql/def-sqlvec-fns "dossier/database/queries/queries.sql" {:quoting :ansi})
(hugsql/def-db-fns "dossier/database/queries/queries.sql" {:quoting :ansi})