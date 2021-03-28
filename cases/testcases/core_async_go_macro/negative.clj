(ns testcases.core-async-go-macro.negative
  "Exercises https://github.com/jonase/eastwood/issues/330.
  
  Should trigger no warnings and throw no exceptions,
  even in face of ocurrences of the  the `go` macro."
  (:require
   [clojure.core.async :as async]
   [com.stuartsierra.component :refer [Lifecycle]]))

(defn simple []
  (async/go
    (prn "FASF")))

(defn large
  "Exercises a `go` block that is less trivial than a one-liner."
  []
  (async/go
    (loop [i 100]
      (doseq [j [i i i]]
        (letfn [(f [n]
                  [f f])]
          (case (f j)
            :foo :bar
            :baz :quux
            (prn "FASF")))))))

;; The original https://github.com/jonase/eastwood/issues/330 report
(defrecord Old-OM [om-config client-id access]
  Lifecycle
  (start [component]
    (async/go
      (prn "FASF")))
  (stop [component]))

(defprotocol P
  (x [p]))

;; An example of something that would make Eastwood choke. Found on core.async's test suite
(defrecord R [z]
  P
  (x [this]
    (go
      (loop []
        (if (zero? (rand-int 3))
          [z (.z this)]
          (recur))))))
