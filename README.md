# hearst

Article/website content extraction. Because the world needed another one.

### The Most Recent Release

`hearst` has not been fully released to Clojars yet because it is not ready for prime time.

With Leiningen, add it to the dependencies in `project.clj`:

```clojure
[hearst "0.1.1-SNAPSHOT"]
```

## Usage

```clojure
user=> (use 'heart.url-cleanup)
user=> (normalize-url "http://example.com/%7Ejane?q=Search&ugly=%c2%b1&utm_source=example.com&utm_medium=whoknows")
"http://example.com/~jane?q=Search&ugly=%C2%B1"
```

## License

Copyright © 2014 Matt Gauger

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
