cd ../
docker run -it --rm -v "$PWD":/usr/src/app -w /usr/src/app clojure lein uberjar
