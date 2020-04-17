(defproject dossier "0.1.0-SNAPSHOT"
  :description "Dossier Server"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [compojure "1.6.1"]
                 [cheshire "5.10.0"]
                 [environ "1.1.0"]
                 [ragtime "0.8.0"]
                 [irresponsible/tentacles "0.6.6"]
                 [com.layerware/hugsql "0.5.1"]
                 [org.postgresql/postgresql "42.2.12.jre7"]
                 [ring/ring-defaults "0.3.2"]]
  :plugins [[lein-ring "0.12.5"] [lein-environ "1.1.0"]]
  :ring {:handler dossier.handler/app}
  :aliases {"migrate"  ["run" "-m" "dossier.migrate/migrate"]
            "rollback" ["run" "-m" "dossier.migrate/rollback"]
            "mcreate"  ["run" "-m" "dossier.migrate/create-migration"]}
  :profiles
  {:dev [:project/dev :profiles/dev]
   :test [:project/test :profiles/test]
   :profiles/dev {}
   :profiles/test {}
   :project/dev 
      {:dependencies [[javax.servlet/servlet-api "2.5"]
                      [ring/ring-mock "0.3.2"]]}
   :project/test {}})
