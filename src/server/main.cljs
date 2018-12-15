(ns server.main
  (:require ["aws-sdk" :as AWS]))

(AWS/config.update #js{:region "us-east-1"})

(def dynamo (AWS/DynamoDB.  #js{:apiVersion "2012-08-10"}))

(def params (clj->js {:TableName "increamental-clicker-counts"
                      :Key {"user-id" {:S "0"}}}))

(def intro #js{#_#_:dynamo dynamo
               :res (fn []
                      (.getItem dynamo params
                                (fn [e d]
                                  (println d))))})


(println "Start")
(.getItem dynamo params
          (fn [e d]
            (println "DONE!")
            (def err (or e "nothing"))
            (def data d)))
(println "Done")

(println "Yo Foo")
(js/console.log "CC")

(defn handler [_ _ cb]
  (cb nil
      #js {:statusCode 200
           :body (js/JSON.stringify "Hello from Shadow")}))
