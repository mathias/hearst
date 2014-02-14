(ns hearst.url-cleanup
  (:require [clojure.string :refer [lower-case upper-case]]
            [cemerick.url :refer [url url-encode url-decode]]
            [clojure.walk :as walk])
  (:import (java.util.regex Matcher)))

;; replace-by comes from clojure.contrib.string, which is no more.
(defn- replace-by
  [^CharSequence s re f]
  (let [m (re-matcher re s)]
    (if (.find m)
      (let [buffer (StringBuffer. (.length s))]
        (loop [found true]
          (if found
            (do (.appendReplacement m buffer (Matcher/quoteReplacement (f (re-groups m))))
                (recur (.find m)))
            (do (.appendTail m buffer)
                (.toString buffer)))))
      s)))

(defn capitalize-letter-matches [matches]
  (upper-case (first matches)))

(def reserved-chars
  (clojure.walk/keywordize-keys {"%21" "!"
                                 "%23" "#"
                                 "%24" "$"
                                 "%26" "&"
                                 "%27" "'"
                                 "%28" "("
                                 "%29" ")"
                                 "%2B" "+"
                                 "%2C" ","
                                 "%2F" "/"
                                 "%3A" ":"
                                 "%3B" ";"
                                 "%3D" "="
                                 "%3F" "?"
                                 "%40" "@"
                                 "%5B" "["
                                 "%5D" "]"
                                 "*"   "*"}))

(defn decode-reserved-characters [[match _]]
  ((keyword match) reserved-chars match))

(defn normalize-percent-encodings [segment]
  (when segment
    (->
     segment
     ;; capitalize letters in escape sequences
     (replace-by #"(%[a-fA-F0-9]{2})" capitalize-letter-matches)

     ;; decode reserved characters
     (replace-by #"(%[a-fA-F0-9]{2})" decode-reserved-characters)
     ;; decode unreserved characters
    (clojure.string/replace #"(%7E)" "~"))))

(defn clean-each-param
  ([q] (apply clean-each-param (vec q)))
  ([k v]
     [k (normalize-percent-encodings v)]))

(defn filter-invalid-params [query]
  (filter (fn [q] (and (not-empty q)
                      (every? (fn [z] (not (empty? z))) q))) query))

(defn reject-utm-params [query]
  (let [utm-params ["utm_campaign"
                    "utm_medium"
                    "utm_source"
                    "utm_content"]]
    (filter (fn [q] (not (some (set q) utm-params))) query)))

(defn clean-query-params [query]
  (map clean-each-param (->
                         query
                         filter-invalid-params
                         reject-utm-params)))

(defn clean-path [path]
  (normalize-percent-encodings path))

(defn normalize-url [uri]
  (let [parsed-url (url uri)]
    (str
     (->
      parsed-url
      (assoc :query (clean-query-params (:query parsed-url)))
      (assoc :host (lower-case (:host parsed-url)))
      (assoc :path (clean-path (:path parsed-url)))))))
