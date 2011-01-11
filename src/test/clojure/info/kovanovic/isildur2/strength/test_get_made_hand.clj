(ns info.kovanovic.isildur2.strength.test-get-made-hand
  (:use info.kovanovic.isildur2.strength.get-made-hand
	info.kovanovic.isildur2.test-fns
	info.kovanovic.isildur2.util.util
	info.kovanovic.isildur2.core.concepts
	clojure.test))

(defn- test-get-made-hand-for-type
  ([type]
     (test-get-made-hand-for-type type
					 (get-hands-with-strongth type)))
  ([type hand-strings]
     (if-not (empty? hand-strings)
       (let [cards (get-cards-from-card-names (first hand-strings))
	     made-hand (get-made-hand cards)]
	 (is (= type
		(:type made-hand)))
	 (is (= (:hand-cards made-hand)
		(take 5 cards)))
	 (recur type
		(rest hand-strings))))))

(deftest test-get-made-hand
  (run-test-for-all-types test-get-made-hand-for-type))
