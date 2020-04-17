(ns dossier.database.queries 
 (:require [hugsql.core :as hugsql]
           [environ.core :refer [env]]))

 (hugsql/def-db-fns "dossier/database/queries/queries.sql")