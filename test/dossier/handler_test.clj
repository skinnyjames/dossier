(ns dossier.handler-test
  (:require [clojure.test :refer :all]
            [dossier.migrate :refer :all]
            [ring.mock.request :as mock]
            [dossier.handler :refer :all
             ]))

(defn reset-database [f]
  (migrate)
  (f))

(defn clear-database [f]
  (clear-db)
  (f))

(use-fixtures :once reset-database)
(use-fixtures :each clear-database)

(deftest test-app
  (testing "not-found route"
    (let [response (app (mock/request :get "/invalid"))]
      (is (= (:status response) 404))))

  (testing "unauthorized route with no session"
    (let [response (app (mock/request :get "/user"))]
      (is (= (:status response) 401))
      (is (= (:body response) "Unauthorized" )))))