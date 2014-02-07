(ns hearst.core-test
  (:require [midje.sweet :refer :all]
            [hearst.core :refer :all]))

(fact "conj"
  (conj [1 2] 3) => [1 2 3])
