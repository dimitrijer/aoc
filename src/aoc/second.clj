(ns aoc.second
  (require [clojure.string :refer [split]]))

(defn checksum-row [values]
  (Math/abs (- (apply min values) (apply max values))))

(defn checksum
  ([]
   (let [lines (-> "resources/second.txt" slurp (split #"\n"))
         rows (map #(split % #"\t") lines) 
         parse-int #(Integer/parseInt %)
         int-rows (map #(map parse-int %) rows)]
     (checksum int-rows)))
  ([rows]
   (reduce + (map checksum-row rows))))
