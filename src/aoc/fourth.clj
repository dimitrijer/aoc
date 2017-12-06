(ns aoc.fourth
  (:require [clojure.string :as str]))

(defn valid?
  [passphrase]
  (let [words (str/split passphrase #"\s+")
        unique-words (into #{} words)]
    (= (count words) (count unique-words))))

(defn count-valid
  []
  (count (filter valid? (str/split (slurp "resources/fourth.txt") #"\n"))))
