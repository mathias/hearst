(ns hearst.url-cleanup-test
  (:require [midje.sweet :refer :all]
            [hearst.url-cleanup :refer :all]))

(fact "basic, valid URLs are unchanged"
      (normalize-url "http://example.com") => "http://example.com")

(fact "mixed-case domains are downcased"
      (normalize-url "http://EXAMPLE.com") => "http://example.com")

(fact "scheme (protocol) is downcased"
      (normalize-url "HTTP://www.example.com") => "http://www.example.com")

(fact "capitalizing letters in escape sequences in paths"
      (normalize-url "http://www.example.com/a%c2%b1b")
      =>
      "http://www.example.com/a%C2%B1b")

(fact "decode unreserved characers"
      (normalize-url "http://example.com/%7Ejane")
      =>
      "http://example.com/~jane")

(comment
  (fact "decode reserved characters"
        (normalize-url "http://example.com//%3Ffoo%3Dbar%26bif%3Dbaz")
        =>
        "http://example.com/?foo=bar&bif=baz"))

(facts "removing empty user info as part of normalization"
       (fact "http://:@example.com/"
             (normalize-url "http://:@example.com/") => "http://example.com/")
       (fact "http://@example.com/"
             (normalize-url  "http://@example.com/") => "http://example.com/"))

(facts "drop extra dots and slashes"
       (fact (normalize-url "http://www.example.com/foo/../foo") => "http://www.example.com/foo")
       (fact (normalize-url "http://www.example.com/foo/") => "http://www.example.com/foo")
       (fact (normalize-url "http://www.example.com/foo///bar//") => "http://www.example.com/foo/bar"))

(facts "removes invalid query params"
       (fact (normalize-url "http://example.com?q=") => "http://example.com")
       (fact (normalize-url "http://example.com?=foo") => "http://example.com"))

(facts "removes utm params"
       (fact (normalize-url "http://example.com?utm_source=foo") => "http://example.com")
       (fact (normalize-url "http://example.com?utm_content=bar") => "http://example.com")
       (fact (normalize-url "http://example.com?utm_medium=baz&q=foo") => "http://example.com?q=foo"))
