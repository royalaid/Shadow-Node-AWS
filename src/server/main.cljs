(ns server.main
  (:require ["aws-sdk" :as AWS]
            [server.atomic-inc :as ai]
            [server.create-item :as ci]
            [server.create-table :as ct]
            [server.delete-item :as di]
            [server.delete-table :as dt]
            [server.read-item :as ri]
            [server.update-item :as ui]
            [server.update-item-conditionally :as uic]
            ))

(AWS/config.update #js{:region "us-east-1"})

(def dynamo (AWS/DynamoDB.  #js{:apiVersion "2012-08-10"}))

(def params (clj->js {:TableName "increamental-clicker-counts"
                      :Key {"user-id" {:S "0"}}}))

(def intro #js{:getItem (fn []
                          (.getItem dynamo params
                                    (fn [e d]
                                      (println "DONE!")
                                      (def err (or e "nothing"))
                                      (def data d))))
               :createTable (fn [] (ct/invoke))
               :createItem (fn [] (ci/invoke))
               :readItem (fn [] (ri/invoke))
               :deleteTable (fn [] (dt/invoke))
               :deleteItem (fn [] (di/invoke))
               :updateItem (fn [] (ui/invoke))
               :updateItemConditionally (fn [] (uic/invoke))
               :atomicInc (fn [] (ai/invoke))})
