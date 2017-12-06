(ns aoc.fifth
  (:require [clojure.string :as str]))

(defn jump
  [[mem pc step]]
  (let [offset (get mem pc)]
    [(assoc mem pc (inc offset))
     (+ pc offset)
     (inc step)]))

(defn count-steps
  []
  (let [mem-str (str/split (slurp "resources/fifth.txt") #"\n")
        mem (vec (map #(Integer/parseInt %) mem-str))]
    (last (take-while (fn [[_ pc _]] (and (<= 0 pc) (> (count mem) pc)))
                      (iterate jump [mem 0 1])))))
