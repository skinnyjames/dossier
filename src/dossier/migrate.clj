(ns dossier.migrate
  (:require [environ.core :refer [env]]
            [ragtime.jdbc :as jdbc]
            [ragtime.repl :as repl]))

(defn load-config []
  {:datastore  (jdbc/sql-database (env :database-url))
   :migrations (jdbc/load-resources "migrations")})

(defn migration-name [name, pos]
  (str (quot (System/currentTimeMillis) 1000) "-" name "." pos ".sql"))

(defn create-migration [name]
  (dorun (map #(let [n (migration-name name %)]
    (spit (str "resources/migrations/" n) (str "--" n))) ["up" "down"])))

(defn migrate []
  (print (env :database-url))
  (repl/migrate (load-config)))

(defn rollback []
  (repl/rollback (load-config)))
