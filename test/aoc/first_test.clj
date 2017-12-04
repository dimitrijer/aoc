(ns aoc.first-test
  (:require [clojure.test :refer :all]
            [aoc.first :refer :all]))

(deftest test-first
  (testing "All zeroes"
    (is (= (solve "0000") 0)))

  (testing "No successive digits"
    (is (= (solve "1234") 0)))
  
  (testing "One successive digit"
    (is (= (solve "1123") 1)))
  
  (testing "Successive at beginning and end"
    (is (= (solve "3234563") 3))))
