(ns aoc.tenth
  (:require [clojure.string :refer [split trim]]))

(def ^:const SIZE 256)

(defn create-list [size] (vec (range size)))

(defn twist
  [l pos len skip-size]
  (let [l-cycle (cycle l)
        reversed-part (->> l-cycle (drop pos) (take len) reverse)
        rest-part (->> l-cycle (drop (+ pos len)) (take (- (count l) len)))]
    (->> (concat reversed-part rest-part)
         cycle
         (drop (- (count l) pos))
         (take (count l)))))

(defn step-twist
  ([l lengths]
   (step-twist l 0 lengths 0))
  ([l pos [len & rest-len] skip-size]
   (if (some? len)
     (recur (twist l pos len skip-size)
            (mod (+ pos len skip-size) (count l))
            rest-len
            (inc skip-size))
     l)))

(defn solve
  []
  (let [input (split (slurp "resources/tenth.txt") #",")
        lengths (apply vector (map #(Integer/parseInt (trim %)) input))]
    (apply * (take 2(step-twist (create-list 256) lengths )))))
