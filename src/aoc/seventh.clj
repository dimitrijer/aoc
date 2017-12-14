(ns aoc.seventh
  (:require [clojure.string :refer [split trim]]))

(defn parse-info
  [info]
  (let [parse-children (fn [children]
                         (when (some? children)
                           (apply vector (map trim (split children #",")))))
        groups (re-matches #"([a-z]*) \(([0-9]*)\)( -> ([a-z, ]+))?" info)]
    (zipmap [:name :weight :children]
            [(nth groups 1) (nth groups 2) (-> groups last parse-children)])))

(defn find-node [nodes name] (first (filter #(= (:name %) name) nodes)))

(defn solve
  []
  (let [input (-> "resources/seventh-real.txt" slurp (split #"\n"))
        puzzle (apply vector (map parse-info input))
        nodes (map #(select-keys % [:name]) puzzle)
        levels (filter some? (map :children puzzle))
        tree-nodes (reduce (fn [{:keys [tree nodes]} level]
                             (let [level-nodes (apply vector (map (partial find-node nodes) level))
                                   remaining-nodes (remove (fn [node] (some #{(:name node)} level)) nodes)]
                               {:tree (conj tree level-nodes)
                                :nodes remaining-nodes}))
                           {:tree [] :nodes nodes} levels)]
    (first (:nodes tree-nodes))))

