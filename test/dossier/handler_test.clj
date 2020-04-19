(ns dossier.handler-test
  (:require [clojure.test :refer :all]
            [dossier.migrate :refer :all]
            [cheshire.core :refer :all]
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

(deftest test-handler
  (testing "not-found route"
    (let [response (app (mock/request :get "/invalid"))]
      (is (= (:status response) 404))))

  (testing "unauthorized route with no session"
    (let [response (app (mock/request :get "/user"))]
      (is (= (:status response) 401))
      (is (= (:body response) "Unauthorized" ))))

  (testing "duplicate registration"
    (let [request-body  {:email-address "bob@xyz.com", :password "hello" :password-confirmation "hello"}
          response1 (app (-> (mock/request :post "/auth")
                            (mock/json-body request-body)))
          response2 (app (-> (mock/request :post "/auth")
                            (mock/json-body request-body)))]
      (is (= (:status response1) 200))
      (is (= (:status response2) 400))
      (is (= (get (parse-string (:body response2) true) :email-address) ["Email already exists"])))))