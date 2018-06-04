(defproject crypto-bot "0.1.0-SNAPSHOT"
  :description "crypto-bot"
  :dependencies [
                  [org.clojure/clojure "1.9.0"]
                  [cider/cider-nrepl "0.16.0"]
                  [org.clojure/data.json "0.2.6"]
                  [org.clojure/tools.namespace "0.2.11"]
                  [http-kit "2.2.0"]]
  :main crypto-bot.core
  :aot [crypto-bot.core]
  :uberjar-name "../docker/app/app.jar"
  :plugins [[lein-ancient "0.6.15"]])



  