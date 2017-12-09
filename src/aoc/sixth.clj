(ns aoc.sixth
  (:require [clojure.string :refer [split]]))

(defn select-redistribution-bank
  [banks]
  (let [max-blocks (apply max (map second banks))
        tied-banks (filter #(= (second %) max-blocks) banks)]
    (first (sort-by first tied-banks))))

(defn next-bank-idx [banks idx] (mod (inc idx) (count banks)))

(defn create-banks [& blocks] (vec (map-indexed vector blocks)))

(defn redistribute-blocks
  ([banks curr-bank-idx blocks-left]
   (if (pos? blocks-left)
     (recur (update-in banks [curr-bank-idx 1] inc)
            (next-bank-idx banks curr-bank-idx)
            (dec blocks-left))
     banks))
  ([banks]
   (let [[bank-idx blocks] (select-redistribution-bank banks)]
     (redistribute-blocks (assoc-in banks [bank-idx 1] 0)
                          (next-bank-idx banks bank-idx)
                          blocks))))

(defn count-steps
  ([banks]
   (let [steps (iterate redistribute-blocks banks)
         reduction (fn [states new-state]
                     (let [next-states (conj states new-state)]
                       (if (contains? states new-state)
                         (reduced next-states)
                         next-states)))]
     (count (reduce reduction #{} steps))))
  ([]
   (let [blocks (map #(Integer/parseInt %)
                     (split (slurp "resources/sixth.txt") #"\s+"))]
     (count-steps (apply create-banks blocks) ))))
