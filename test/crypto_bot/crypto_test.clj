(ns crypto-bot.crypto-test
  (:require [clojure.test :refer :all]
            [crypto-bot.crypto :refer :all]))

(deftest rot13-test
  (is (= "NOPQRSTUVWXYZABCDEFGHIJKLMnopqrstuvwxyzabcdefghijklm" (rot13 "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz")))
  (is (= "123" (rot13 "123"))))


(deftest md5-test
  (is (= "098f6bcd4621d373cade4e832627b4f6" (md5 "test")))
  (is (= "fb469d7ef430b0baf0cab6c436e70375" (md5 "098f6bcd4621d373cade4e832627b4f6"))))
