(ns testcases.faulty-defmacro)

(defmacro dubious
  "A macro that will cause a linter fault"
  [x]
  `(-> ~x))
