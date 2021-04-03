(ns testcases.faulty-defmacro-consumer
  (:require
   [testcases.faulty-defmacro]))

(def x (testcases.faulty-defmacro/dubious 2))
