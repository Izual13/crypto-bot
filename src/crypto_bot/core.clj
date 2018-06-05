(ns crypto-bot.core
  (:require [org.httpkit.client :as http]
            [clojure.tools.nrepl.server :as nrepl.server]
            [clojure.data.json :as json]
            [cider.nrepl :as cider]
            [crypto-bot.crypto :as crpt])
  (:gen-class))

(def commands "/echo \n/rot13 \n/md5")

(def sessions (atom {}))

(defn session-put [id command] (
  if (not (contains? @sessions id))
     (swap! sessions assoc id command)))


(defn session-remove [id] (
  if (contains? @sessions id)
     (swap! sessions dissoc id)))

(def last-message-id (atom 1))
(def is-updating (atom false))
(def token (get (System/getenv) "TOKEN" "token..."))

(def post-message-url (str "https://api.telegram.org/bot" token "/" "sendMessage"))
(def get-updates-url (str "https://api.telegram.org/bot" token "/" "getUpdates"))

(defn parse[str] (get (first (json/read-str str :key-fn keyword)) :price_usd))
(defn get-updates-request [] (json/write-str {"offset" (+ @last-message-id 1) "timeout" 60}))
(defn post-message-request [chat-id text]
  (json/write-str
   {"chat_id"    chat-id
    "text"       text
    "parse_mode" "Markdown"}))

(defn update-last-message-id [id] (reset! last-message-id id))
(defn recognize-command? [row] (not (clojure.string/blank? (re-matches #"^/.*" row))))



(defn get-updates []
  (if (not @is-updating)
    (do (reset! is-updating true)
      (let [{:keys [status headers body error] :as resp}
            @(http/post get-updates-url
              {:body    (get-updates-request)
               :headers {"Content-Type" "application/json"}})]
        (if error
          (println "Failed, exception: " error)
          (comment println "HTTP GET success: " body))
        (reset! is-updating false) body))))



(defn post-message [chat-id text]
  (let [{:keys [status headers body error] :as resp}
        @(http/post post-message-url
          {:body    (post-message-request chat-id text)
           :headers {"Content-Type" "application/json"}})]
    (if error
      (println "Failed, exception: " error)
      (comment println "HTTP GET success: " status))
    body))


(defn parse-updates [updates]
  (if (not (nil? updates)) (:result (json/read-str updates :key-fn keyword))))

(defn parse-command [row] (let [index (.indexOf row " ")] 
  (if (= -1 index) row (subs row 0 index))))

(defn processing [fulltext chat-id update-id]
  (println "Message: " fulltext " chatId: " chat-id " updateId:" update-id)
  (if (recognize-command? fulltext) 
    (do 
      (session-put chat-id (parse-command fulltext)) 
      (post-message chat-id "enter input data"))
    (do (if (nil? (get @sessions chat-id)) 
      (post-message chat-id "enter command")
      (let [command (get @sessions chat-id)]
        (cond 
          (= command "/echo") (post-message chat-id fulltext)
          (= command "/test") (post-message chat-id "test")
          (= command "/rot13") (post-message chat-id (crpt/rot13 fulltext))
          (= command "/md5") (post-message chat-id (crpt/md5 fulltext))
          :else (post-message chat-id (str "command not found\n" commands)))
        (session-remove chat-id))))))




(defn message-handler [{update-id :update_id {{chat-id :id} :chat text :text} :message}]
  (if (not (nil? text))
  (do (processing (.trim text) chat-id update-id) (update-last-message-id update-id))))

(defn main-task [] (doseq [message (parse-updates (get-updates))] (message-handler message)))

; (main-task)

(defn set-interval [callback ms]
  (future (while true (do (callback) (Thread/sleep ms)))))

(defn -main [] (def job (set-interval #(main-task) 1000)))

