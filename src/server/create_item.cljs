(ns server.create-item
  (:require ["aws-sdk" :as AWS]))

(AWS/config.update #js{:region "us-east-1"})

(def doc-client (AWS/DynamoDB.DocumentClient.))

(def table "Movies")

(def year 2015)

(def title "The Big New Movie")

(def params (clj->js {:TableName table
                      :Item {"year" year
                             "title" title
                             "info" {"plot" "Nothing happens at all."
                                     "rating" 0}}}))

(defn invoke []
  (println "Adding new item...")
  (.put doc-client params
        #(if %1
           (js/console.error "Unable to add item. Error JSON:"
                             (js/JSON.stringify %1 nil 2))
           (js/console.log "Added Item:"
                           (js/JSON.stringify %2 nil 2)))))
