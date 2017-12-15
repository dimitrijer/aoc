(ns aoc.eight
  (:require [instaparse.core :as insta]))

(def parser (insta/parser
              "
              commands = command*
              command = regid op num <'if'> regid cmp num
              regid = #'[a-z]+'
              num = #'-?[0-9]+'
              op = 'inc' | 'dec'
              cmp = '>' | '<' | '>=' | '<=' | '==' | '!='
              "
              :auto-whitespace :standard))

(defn execute
  [registers [reg op operand-str cmp-reg cmp-op cmp-operand-str]]
  (let [cmp-reg-val (or (get registers cmp-reg) 0)
        cmp-fn (case cmp-op
                 "==" =
                 "!=" not=
                 (-> cmp-op symbol resolve))
        cmp-operand (Integer/parseInt cmp-operand-str)
        operand (Integer/parseInt operand-str)
        cond? (cmp-fn cmp-reg-val cmp-operand)
        op-fn (case op
                "inc" #(+ (or % 0) operand)
                "dec" #(- (or % 0) operand))]
    (update registers reg (if cond? op-fn identity))))

(defn perform [commands] (reduce execute {} commands))

(defn sanitize-commands
  [dirty-commands]
  (letfn [(sanitize-command [dirty-command]
            (apply vector (map second (rest dirty-command))))]
    (map sanitize-command (rest dirty-commands))))

(defn solve
  []
  (->> (slurp "resources/eight.txt")
       parser
       sanitize-commands
       perform
       vals
       (filter some?)
       (apply max)))
