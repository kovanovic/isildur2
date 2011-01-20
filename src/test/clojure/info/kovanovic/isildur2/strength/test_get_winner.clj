(ns info.kovanovic.isildur2.strength.test-get-winner
  [:use [info.kovanovic.isildur2 core get-hand get-winner test-fns]]
  [:use [clojure.test]])

(defn- test-get-winner-for-two-hands
  [hand-type winner looser]
  (let [winning-cards (create-cards winner)
	loosing-cards (create-cards looser)
	winning-hand (get-hand winning-cards)
	loosing-hand (get-hand loosing-cards)
	winner (nth (get-winner winning-cards loosing-cards) 1)]
    (is (= (:type winning-hand) hand-type))
    (is (= (:hand-cards winning-hand)
	   (take 5 winning-cards)))
    (is (= (:hand-cards loosing-hand)
	   (take 5 loosing-cards)))
    (is (= winning-hand winner))))

(defn- test-all-hand-combinations
  [hand-type combinations]
  (if-not (empty? combinations)
    (let [combo (first combinations)]
      (test-get-winner-for-two-hands hand-type
				     (first combo)
				     (second combo))
      (recur hand-type (rest combinations)))))

(defn- test-get-winner-for-hand-type
  [hand-type]
  (let [different-hand-type-combinations (for [winner (get-hands-with-strongth hand-type)
					       looser (get-hands-weaker-than hand-type)]
					   (list winner looser))
	same-hand-type-combinations (for [winner (get-hands-with-strongth hand-type)
					  looser (get-same-type-hands-weaker-than winner)]
				      (list winner looser))]
    (test-all-hand-combinations hand-type
				(concat different-hand-type-combinations
					same-hand-type-combinations))))

(deftest test-get-winner
  (run-test-for-all-types test-get-winner-for-hand-type))
