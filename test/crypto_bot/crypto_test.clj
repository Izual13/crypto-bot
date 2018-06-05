(ns crypto-bot.crypto-test
  (:require [clojure.test :refer :all]
            [crypto-bot.crypto :refer :all]))

(deftest rot13-test
  (is (= "NOPQRSTUVWXYZABCDEFGHIJKLMnopqrstuvwxyzabcdefghijklm" (rot13 "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz")))
  (is (= "123" (rot13 "123"))))


  
  
