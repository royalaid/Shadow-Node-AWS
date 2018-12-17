(ns server.delete-table
  (:require ["aws-sdk" :as AWS]
            ))

(AWS/config.update #js{:region "us-east-1"})

(def dynamo (AWS/DynamoDB.  #js{:apiVersion "2012-08-10"}))

(def params (clj->js {:TableName "Movies"}))

(defn invoke []
  (.deleteTable dynamo params
                #(if %1
                   (js/console.error "Unable to delete table. Error JSON:"
                                     (js/JSON.stringify %1 nil 2))
                   (js/console.log "Delete table. Table description JSON:"
                                   (js/JSON.stringify %2 nil 2)))))
