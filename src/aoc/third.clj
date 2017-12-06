(ns aoc.third)

(def rotation-matrix [[0 -1]
                      [1 0]])

(defn rotate 
  "Rotates provided direction counter-clockwise."
  [[dx dy]]
  [(+ (* dx (get-in rotation-matrix [0 0])) (* dy (get-in rotation-matrix [0 1])))
   (+ (* dx (get-in rotation-matrix [1 0])) (* dy (get-in rotation-matrix [1 1])))])

(defn next-node
  "Returns next node in provided direction given by dx and dy from node given
  by x, y and val."
  [[dx dy] [x y val]]
  [(+ x dx)
   (+ y dy)
   (inc val)])

(defn gen-nodes
  "Generates n next nodes from node, in given direction."
  [from-node direction n]
  (let [iter-nodes (iterate (partial next-node direction) from-node)]
    ;; Don't return starting node.
    (take n (drop 1 iter-nodes))))

(defn nodes
  ([] (cons [0 0 1] (nodes [0 0 1] [1 0] 1)))
  ([from-node direction n]
   (let [first-part (gen-nodes from-node direction n)
         second-part (gen-nodes (last first-part) (rotate direction) n)]
     (lazy-seq 
       (concat first-part
               second-part
               (nodes (last second-part) (-> direction rotate rotate) (inc n)))))))
