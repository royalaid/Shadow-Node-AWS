(ns server.delete-item
  (:require ["aws-sdk" :as AWS]))

(AWS/config.update #js{:region "us-east-1"})

(def doc-client (AWS/DynamoDB.DocumentClient.))

(def table "Movies")

(def year 2015)

(def title "The Big New Movie")

(def params (clj->js {
                      :TableName table
                      :Key {"year" year
                            "title" title}}))

(defn invoke []:w
  (println "Attempting a conditional update...")
  (.delete doc-client params
           #(if %1
              (js/console.error "Unable to delete item. Error JSON:"
                                (js/JSON.stringify %1 nil 2))
              (js/console.log "DeleteItem succeeded:"
                              (js/JSON.stringify %2 nil 2)))))
