(ns info.kovanovic.isildur2.strength.test-get-made-hand
  [:use [info.kovanovic.isildur2 core get-winner test-fns get-hand]]
  [:use [clojure.test]])

(defn- test-get-hand-for-hand-type
  ([hand-type]
     (test-get-hand-for-hand-type hand-type
			     (get-hands-with-strongth hand-type)))
  ([hand-type hand-strings]
     (if-not (empty? hand-strings)
       (let [cards (create-cards (first hand-strings))
	     hand (get-hand cards)]
	 (is (= hand-type
		(:type hand)))
	 (is (= (:hand-cards hand)
		(take 5 cards)))
	 (recur hand-type
		(rest hand-strings))))))

(deftest test-get-hand
  (run-test-for-all-types test-get-hand-for-hand-type))
