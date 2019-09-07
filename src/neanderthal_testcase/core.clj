(ns neanderthal-testcase.core
  (:require [uncomplicate.commons.core :refer [with-release let-release release]]
            [uncomplicate.neanderthal.native :refer [native-double native-float fv dv dge fge]]
            [uncomplicate.neanderthal.core :refer [transfer transfer! ge axpy axpy! entry! view view-ge]]
            [uncomplicate.neanderthal.cuda :refer [cuda-float cuda-double]]
            [uncomplicate.clojurecuda.core :as clojurecuda]
            [uncomplicate.fluokitten.core :refer [fold]]))

(defn native-compute []
  (let [factory native-float]
    (with-release [x (entry! (ge factory 2 5) 1.0)
                   y (entry! (ge factory 2 5) 2.0)]
      (with-release [vx (view x)
                     vy (view y)]
        (axpy! x y)
        (assert (= 30.0 (fold y)))
        (axpy! vx vy)
        (assert (= 40.0 (fold vy))))
      (fold (axpy! x y)))))                                 ; = 50.0

(defn cuda-compute []
  (clojurecuda/with-default
    (let [factory (cuda-float (clojurecuda/current-context) clojurecuda/default-stream)]
      (with-release [x (entry! (ge factory 2 5) 1.0)
                     y (entry! (ge factory 2 5) 2.0)]
        (with-release [vx (view x)
                       vy (view y)]
          (axpy! x y)
          (axpy! vx vy))
        (axpy! x y)
        (with-release [host-x (transfer! x)]
          (fold host-x))))))                              ; = 50.0

(comment

  (fold (axpy! (entry! (ge native-float 2 5) 1.0)
               (entry! (ge native-float 2 5) 2.0)))

  (native-compute)

  (cuda-compute)

  )

