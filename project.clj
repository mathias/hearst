(defproject hearst "0.1.0-SNAPSHOT"
  :description "Article/website content extraction"
  :url "https://github.com/mathias/hearst"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [com.cemerick/url "0.1.0"]
                 [midje "1.6.0"]]
  :profiles {:dev
             {:plugins [[lein-midje "3.1.1"]]}})
