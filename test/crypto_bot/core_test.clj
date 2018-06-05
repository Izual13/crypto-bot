(ns crypto-bot.core-test
  (:require [clojure.test :refer :all]
            [crypto-bot.core :refer :all]))

(deftest parse-command-test
  (is (= "/asd" (parse-command "/asd")))
  (is (= "/asd" (parse-command "/asd dsfhjksdf"))))


    

(deftest recognize-command-test
  (is (recognize-command? "/asd"))
  (is (parse-command "/asd dsfhjksdf")))

