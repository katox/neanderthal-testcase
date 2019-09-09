(ns neanderthal-testcase.core-test
  (:require [clojure.test :refer :all]
            [neanderthal-testcase.core :refer :all]))

(deftest native-compute-test
  (testing "Memory corruption during view release (native)."
    (loop [i 0]
      (let [actual-value (native-compute)]
        (if (and (= 50.0 actual-value) (< i 100))
          (recur (inc i))
          (do
            (println "Finished cycles:" i)
            (is (= 50.0 actual-value))))))))

(deftest cuda-compute-test
  (testing "Memory corruption during view release (CUDA). "
    (loop [i 0]
      (let [actual-value (cuda-compute)]
        (if (and (= 50.0 actual-value) (< i 100))
          (recur (inc i))
          (do
            (println "Finished cycles:" i)
            (is (= 50.0 actual-value))))))))

