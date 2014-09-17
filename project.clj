(defproject hearst "0.1.1-SNAPSHOT"
  :description "URL normalizer, article content extraction, summary generator."
  :url "https://github.com/mathias/hearst"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [com.cemerick/url "0.1.1"]]
  :profiles {:dev
             {:dependencies [[midje "1.6.3"]]
              :plugins [[lein-midje "3.1.1"]]}})
