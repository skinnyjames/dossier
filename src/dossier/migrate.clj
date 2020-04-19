(ns dossier.migrate
  (:require [environ.core :refer [env]]
            [hugsql.core :as hugsql]
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
    (repl/migrate (load-config)))

(defn rollback []
    (repl/rollback (load-config)))

(hugsql/def-db-fns "dossier/database/queries/operations.sql" {:quoting :ansi})

; test case handling

(defn clear-db []
  (clear-reports (env :database-url))
  (clear-applications (env :database-url))
  (clear-sessions (env :database-url))
  (clear-users (env :database-url)))