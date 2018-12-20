(defproject crypto-bot "0.1.0-SNAPSHOT"
  :description "crypto-bot"
  :dependencies [
                 [org.clojure/clojure "1.10.0"]
                 [cheshire "5.8.1"]
                 [org.clojure/tools.namespace "0.2.11"]
                 [http-kit "2.4.0-alpha2"]]
  :main crypto-bot.core
  :aot [crypto-bot.core]
  :uberjar-name "../docker/app/app.jar"
  :plugins [[lein-ancient "0.6.15"]]
  )



  