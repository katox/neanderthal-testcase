(defproject neanderthal-testcase "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [uncomplicate/neanderthal "0.25.6"]]
  :main ^:skip-aot neanderthal-testcase.core
  :target-path "target/%s"
  :jvm-opts ["--add-opens=java.base/jdk.internal.ref=ALL-UNNAMED"]
  :source-paths ["src"]
  :profiles {:dev          [:project/dev :profiles/dev]
             :debug        {:jvm-opts ^:replace ["--add-opens=java.base/jdk.internal.ref=ALL-UNNAMED"
                                                 "--add-opens=java.xml/com.sun.xml.internal.stream=ALL-UNNAMED"
                                                 "-Dclojure.compiler.disable-locals-clearing=true"]}
             :project/dev  {:dependencies [[criterium "0.4.5"]]}
             :profiles/dev {}
             :uberjar {:aot :all}})
