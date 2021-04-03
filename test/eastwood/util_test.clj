(ns eastwood.util-test
  (:require
   [eastwood.util :as sut]
   [clojure.test :refer [are deftest is join-fixtures testing use-fixtures]]))

(defn wrap-macro [x]
  {:macro x})

(deftest omit-unrelated-warning?
  (are [desc lint-foreign? project-sources-set? project-namespace-strings enclosing-macros expected]
      (testing desc
        (is (= expected
           (sut/omit-unrelated-warning? lint-foreign?
                                        project-sources-set?
                                        project-namespace-strings
                                            (map wrap-macro enclosing-macros)))))
    #_lint-foreign? #_project-sources-set? #_project-namespace-strings #_enclosing-macros               #_expected
    
    "If no `enclosing-macros` were detected, `false` is returned out of caution
(because it would be an unexpected condition)"
    false           true                   #{"foo.bar"}                '()                              false

    "A macro inside the project is not omitted (only member)"
    false           true                   #{"foo.bar"}                '(foo.bar/macro)                 false

    "A macro inside the project is not omitted (last member)"
    false           true                   #{"foo.bar"}                '(unrelated/macro foo.bar/macro) false

    "A macro inside the project is not omitted (first member)"
    false           true                   #{"foo.bar"}                '(foo.bar/macro unrelated/macro) false

    "A macro outside the project is omitted"
    false           true                   #{"foo.bar"}                '(unrelated/macro)               true

    "A macro outside the project not omitted if `project-namespace-strings` is empty"
    false           true                   #{}                         '(unrelated/macro)               false

    "A macro outside the project is not omitted if `lint-foreign?` is true"
    true            true                   #{"foo.bar"}                '(unrelated/macro)               false

    "A macro outside the project is not omitted if `project-sources-set?` is false"
    false           false                  #{"foo.bar"}                '(unrelated/macro)               false))
