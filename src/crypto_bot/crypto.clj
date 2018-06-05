(ns crypto-bot.crypto)

(defn rot13 [txt] (apply str 
    (map #(let [c (int %)](char (cond 
     (and (>= c (int \a)) (<= c (int \m))) (+ c 13)
     (and (>= c (int \A)) (<= c (int \M))) (+ c 13)
     (and (>= c (int \n)) (<= c (int \z))) (- c 13)
     (and (>= c (int \N)) (<= c (int \Z))) (- c 13)
     :else %1))) txt)))
     
(defn bytes-to-hex [bytes] (apply str (for [b bytes] (format "%02x" b))))

(defn md5 [txt] (let [md5-encoder (java.security.MessageDigest/getInstance "MD5")
                      txt-bytes (.getBytes txt)]
    (.update md5-encoder txt-bytes)
    (bytes-to-hex (.digest md5-encoder)))) 


