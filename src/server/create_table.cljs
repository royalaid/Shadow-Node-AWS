(ns server.create-table
  (:require ["aws-sdk" :as AWS]
            ))

(AWS/config.update #js{:region "us-east-1"})

(def dynamo (AWS/DynamoDB.  #js{:apiVersion "2012-08-10"}))

(def params
  (clj->js {:TableName "Movies",
            :KeySchema
            [{:AttributeName "year", :KeyType "HASH"}
             {:AttributeName "title", :KeyType "RANGE"}],
            :AttributeDefinitions
            [{:AttributeName "year", :AttributeType "N"}
             {:AttributeName "title",
              :AttributeType "S"}],
            :ProvisionedThroughput
            {:ReadCapacityUnits 10,
             :WriteCapacityUnits 10}}))

(defn invoke []
 (.createTable dynamo params
               #(if %1
                  (js/console.error "Unable to create table. Error JSON:"
                                    (js/JSON.stringify %1 nil 2))
                  (js/console.log "Created table. Table description JSON:"
                                  (js/JSON.stringify %2 nil 2)))))



