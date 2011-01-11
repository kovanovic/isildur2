(ns info.kovanovic.isildur2.strength.test-get-winner
  (:use info.kovanovic.isildur2.strength.get-winner
	info.kovanovic.isildur2.strength.get-made-hand
	info.kovanovic.isildur2.test-fns
	info.kovanovic.isildur2.util.util
	info.kovanovic.isildur2.core.concepts
	clojure.test))

(defn- test-get-winner-for-two-hands
  [type winner looser]
  (let [winning-cards (get-cards-from-card-names winner)
	loosing-cards (get-cards-from-card-names looser)
	winning-made-hand (get-made-hand winning-cards)
	loosing-made-hand (get-made-hand loosing-cards)
	winner (get-winner winning-cards loosing-cards)]
    (is (= (:type winning-made-hand) type))
    (is (= (:hand-cards winning-made-hand)
	   (take 5 winning-cards)))
    (is (= (:hand-cards loosing-made-hand)
	   (take 5 loosing-cards)))
    (is (= winning-made-hand winner))))

(defn- test-all-hand-combinations
  [type combinations]
  (if-not (empty? combinations)
    (let [combo (first combinations)]
      (test-get-winner-for-two-hands type
				     (first combo)
				     (second combo))
      (recur type (rest combinations)))))

(defn- test-get-winner-for-type
  [type]
  (let [hand-combinations (for [winner (get-hands-with-strongth type)
				looser (get-hands-weaker-than type)]
			    (list winner looser))]
    (test-all-hand-combinations type hand-combinations)))

(deftest test-get-winner
  (run-test-for-all-types test-get-winner-for-type))
