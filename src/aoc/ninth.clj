(ns aoc.ninth
  (:require [instaparse.core :as insta]
            [clojure.walk :refer [prewalk prewalk-demo postwalk postwalk-demo]]))

(def parser
  (insta/parser
    "
    group = <'{'> group-contents <'}'>
    <group-contents> = group-element <','> group-contents | group-element | ε
    <group-element> = group | garbage
    garbage = <'<'> garbage-contents <'>'>
    <garbage-contents> = garbage-element*
    <garbage-element> = ignored-char | !'>' #'[^!]'
    ignored-char = #'!.'
    "))

(defn score
  ([element] (score 0 element))
  ([level element]
   (cond
     (= :group element) level
     (and (vector? element)
          (= :group (first element))) (apply + (map (partial score (inc level)) element))
     :default 0)))

(defn solve [] (-> (slurp "resources/ninth.txt") parser score))
