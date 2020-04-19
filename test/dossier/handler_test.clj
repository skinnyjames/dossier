(ns dossier.handler-test
  (:require [clojure.test :refer :all]
            [dossier.migrate :refer :all]
            [cheshire.core :refer :all]
            [ring.mock.request :as mock]
            [dossier.handler :refer :all]))

(defn reset-database [f]
  (migrate)
  (f))

(defn clear-database [f]
  (clear-db)
  (f))

(defn setup-user [])

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
      (is (= (get (parse-string (:body response2) true) :email-address) ["Email already exists"]))))

  (testing "fetching applications wip"
    (let [response (app (mock/request :get "/user/1/apps"))]
      (is (= (parse-string (:body response) true) []))))

  (testing "creating application wip"
    (let [request-body { :name "Ruby Test Suite" :framework "RubyCucumber"}
          ; setup user
          response (app (-> (mock/request :post "/user/1/apps")
                          (mock/json-body request-body)))]
      (is (= (:status response) 200))))

  (testing "deleting application wip"
    ; setup user and application
    (let [response (app (mock/request :delete "/user/1/apps/1"))]
      (is (= (:status response) 200))))

  (testing "sending ruby cucumber report"
    ; setup user and application
    (let [cucumber-report {}
          response (app (-> (mock/request :post "/reports/:api-key")
                              (mock/json-body cucumber-report)))]
      (is (= (:status response) 200)))))