(defproject org.clojure/core.async "0.4.475-SNAPSHOT"
  :description "Facilities for async programming and communication in Clojure"
  :url "https://github.com/clojure/core.async"
  :license {:name "Eclipse Public License"
            :url "https://www.eclipse.org/legal/epl-v10.html"}
  :parent [org.clojure/pom.contrib "0.2.2"]
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/tools.analyzer.jvm "0.7.0"]
                 [org.clojure/clojurescript "0.0-2311" :scope "provided"]]
  :global-vars {*warn-on-reflection* true}
  :source-paths ["src/main/clojure"]
  :test-paths ["src/test/clojure"]
  :jvm-opts ^:replace ["-Xmx1g" "-server"]
  :java-source-paths ["src/main/java"]
  :profiles {:dev {:source-paths ["examples"]}
             :1.6 {:dependencies [[org.clojure/clojure "1.6.0"]]}
             :1.7 {:dependencies [[org.clojure/clojure "1.7.0"]]}
             :1.8 {:dependencies [[org.clojure/clojure "1.8.0"]]}
             :1.9 {:dependencies [[org.clojure/clojure "1.9.0"]]}
             :1.10 {:dependencies [[org.clojure/clojure "1.10.0-master-SNAPSHOT"]]}
             }

  :plugins [[lein-cljsbuild "1.1.2"]]

  :clean-targets ["tests.js" "tests.js.map"
                  "out" "out-simp" "out-simp-node"
                  "out-adv" "out-adv-node"]

  :cljsbuild
  {:builds
   [{:id "dev"
     :source-paths ["src/test/cljs" "src/main/clojure/cljs"]
     :compiler {:main cljs.core.async.test-runner
                :asset-path "../out"
                :optimizations :none
                :output-to "tests.js"
                :output-dir "out"}}
    {:id "simple"
     :source-paths ["src/test/cljs" "src/main/clojure/cljs"]
     :compiler {:optimizations :simple
                :pretty-print true
                :static-fns true
                :output-to "tests.js"
                :output-dir "out-simp"}}
    {:id "simple-node"
     :source-paths ["src/test/cljs" "src/main/clojure/cljs"]
     :notify-command ["node" "tests.js"]
     :compiler {:optimizations :simple
                :target :nodejs
                :pretty-print true
                :static-fns true
                :output-to "tests.js"
                :output-dir "out-simp-node"}}
    {:id "adv"
     :source-paths ["src/test/cljs" "src/main/clojure/cljs"]
     :compiler {:optimizations :advanced
                :pretty-print false
                :output-dir "out-adv"
                :output-to "tests.js"
                :source-map "tests.js.map"}}
    {:id "adv-node"
     :source-paths ["src/test/cljs" "src/main/clojure/cljs"]
     :compiler {:optimizations :advanced
                :target :nodejs
                :pretty-print false
                :output-dir "out-adv-node"
                :output-to "tests.js"
                :source-map "tests.js.map"}}]})