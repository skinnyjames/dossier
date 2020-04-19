(defproject dossier "0.1.0-SNAPSHOT"
  :description "Dossier Server"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :main dossier.handler
  :source-paths ["src"]
  ;:jvm-opts ["-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5010"]
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [compojure "1.6.1"]
                 [buddy "2.0.0"]
                 [cheshire "5.10.0"]
                 [environ "1.1.0"]
                 [ragtime "0.8.0"]
                 [ring-logger "1.0.1"]
                 [metis "0.3.3"]
                 [ring/ring-devel "1.8.0"]
                 [irresponsible/tentacles "0.6.6"]
                 [com.layerware/hugsql "0.5.1"]
                 [ring/ring-jetty-adapter "1.6.3"]
                 [org.postgresql/postgresql "42.2.12.jre7"]
                 [ring/ring-defaults "0.3.2"]
                 [ring/ring-json "0.5.0"]]
  :plugins [[lein-ring "0.12.5"] [lein-environ "1.1.0"]]
  :aliases {"migrate"  ["run" "-m" "dossier.migrate/migrate"]
            "rollback" ["run" "-m" "dossier.migrate/rollback"]
            "mcreate"  ["run" "-m" "dossier.migrate/create-migration"]
            "clear"    ["run" "-m" "dossier.migrate/clear-db"]}
  :ring { :handler dossier.handler/app 
          :auto-reload? true
          :auto-refresh? true}
  :profiles
  {:dev [:project/dev :profiles/dev]
   :test [:project/test :profiles/test]
   :profiles/dev {}
   :profiles/test {}
   :project/dev
      {:dependencies [[javax.servlet/servlet-api "2.5"]
                      [ring/ring-mock "0.3.2"]]}
   :project/test {}})
