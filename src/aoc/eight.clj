(ns aoc.eight
  (:require [instaparse.core :as insta]))

(def parser (insta/parser
              "
              commands = command*
              command = regid op num <'if'> regid cmp num
              regid = #'[a-z]+'
              num = #'-?[0-9]+'
              op = 'inc' | 'dec'
              cmp = '>' | '<' | '>=' | '<=' | '=='
              "
              :auto-whitespace :standard))

(defn execute
  [registers [_ [_ reg] [_ op] [_ operand] [_ cmp-reg] [_ cmp-op] [_ cmp-operand]]]
  (let [cmp-reg-val (or (get registers cmp-reg) 0)
        cmp-fn (if (= cmp-op "==") = (symbol cmp-op))
        cond? (cmp-fn cmp-reg-val cmp-operand)
        op-fn (case op
                "inc" #(+ (or % 0) operand)
                "dec" #(- (or % 0) operand)
                :default (throw (IllegalArgumentException.)))]
    (update registers reg (if cond? op-fn identity) operand) ))

(defn perform
  [commands]
  (reduce execute {} commands))
