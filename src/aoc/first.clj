(ns aoc.first)

(defn sum-chars [puzzle]
  (map-indexed (fn [idx curr-char]
                 (let [next-idx (mod (inc idx) (count puzzle))
                       next-char (get puzzle next-idx)]
                   (if (= curr-char next-char)
                     (Integer/parseInt (str curr-char))
                     0))) puzzle))
                      
(defn solve [puzzle] (reduce + (sum-chars puzzle)))

