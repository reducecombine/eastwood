(ns testcases.core-async-go-macro.positive
  "Exercises https://github.com/jonase/eastwood/issues/330.
  
  Should trigger 3 warnings (namely: before, inside, and after the `go` macro occurrence)."
  (:require
   [clojure.core.async :as async]
   [com.stuartsierra.component :refer [Lifecycle]]))

clojure.set/difference

(defrecord Old-OM [om-config client-id access]
  Lifecycle
  (start [component]
    (async/go
      clojure.set/map-invert
      (prn "FASF")))
  (stop [component]))

clojure.set/intersection
