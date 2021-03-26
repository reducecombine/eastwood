(defproject jonase/eastwood "0.3.14"
  :description "A Clojure lint tool"
  :url "https://github.com/jonase/eastwood"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :global-vars {*warn-on-reflection* true
                ;;*unchecked-math* :warn-on-boxed
                }
  :source-paths ["src" "copied-deps"]
  :dependencies [[org.clojure/clojure "1.10.2" :scope "provided"]
                 [org.clojars.brenton/google-diff-match-patch "0.1"]
                 [org.ow2.asm/asm-all "5.2"]]
  :profiles {:dev {:dependencies [[org.clojure/tools.macro "0.1.5"]
                                  [jafingerhut/dolly "0.1.0"]]}
             :test {:dependencies [[commons-io "2.4" #_"Needed for issue-173-test"]]
                    :resource-paths ["test-resources"
                                     ;; if wanting the `cases` to be available during development / the default profile,
                                     ;; please simply add `with-profile +test` to your CLI invocation.
                                     "cases"]}
             :1.7 {:dependencies [[org.clojure/clojure "1.7.0"]]}
             :1.8 {:dependencies [[org.clojure/clojure "1.8.0"]]}
             :1.9 {:dependencies [[org.clojure/clojure "1.9.0"]]}
             :1.10 {:dependencies [[org.clojure/clojure "1.10.2"]]}}
  :aliases {"test-all" ["with-profile"
                        "-dev,+test,+1.7:-dev,+test,+1.8:-dev,+test,+1.9:-dev,+test,+1.10"
                        "test"]}
  :eastwood {:source-paths ["src"]
             :test-paths ["test"]
             :only-modified true
             :debug #{}}
  :plugins [[net.assum/lein-ver "1.2.0"]]
  :lein-ver {:version-file "resources/EASTWOOD_VERSION"}
  ;; Eastwood may work with earlier Leiningen versions, but this is
  ;; close to the earliest version that it was most tested with.
  :min-lein-version "2.3.0"
  :resource-paths ["resource" "resources"]
  :eval-in ~(case (System/getenv "EVAL_IN_LEININGEN")
              ("1" "true") :leiningen
              :subprocess))
