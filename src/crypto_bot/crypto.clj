(ns crypto-bot.crypto)

(defn rot13 [txt] (apply str 
    (map #(let [c (int %1)](char (cond 
     (and (>= c (int \a)) (<= c (int \m))) (+ c 13)
     (and (>= c (int \A)) (<= c (int \M))) (+ c 13)
     (and (>= c (int \n)) (<= c (int \z))) (- c 13)
     (and (>= c (int \N)) (<= c (int \Z))) (- c 13)
     :else %1))) (seq txt))))
