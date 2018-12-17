(ns server.read-item
  (:require ["aws-sdk" :as AWS]))

(AWS/config.update #js{:region "us-east-1"})

(def doc-client (AWS/DynamoDB.DocumentClient.))

(def table "Movies")

(def year 2015)

(def title "The Big New Movie")

(def params (clj->js {:TableName table
                      :Key {"year" year
                            "title" title}
                      :UpdateExpression "set info.rating = :r, info.plot=:p, info.actors=:a"
                      :ExpressionAttributeValues{":r" 5.5,
                                                 ":p" "Everything happens all at once.",
                                                 ":a" ["Larry", "Moe", "Curly"]
                                                 }
                      }))

(defn invoke []
  (println "Adding new item...")
  (.get doc-client params
        #(if %1
           (js/console.error "Unable to read item. Error JSON:"
                             (js/JSON.stringify %1 nil 2))
           (js/console.log "GetItem succeeded:"
                           (js/JSON.stringify %2 nil 2)))))
