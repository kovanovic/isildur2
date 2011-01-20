(ns info.kovanovic.isildur2.test-fns
  "set of functions that are used to ckeck functions in the strength package"
  [:use	[info.kovanovic.isildur2 core test-data get-winner get-hand]]
  [:use [clojure.test]])

(defn get-hands-with-strongth
  "returns the collection of hands from the test-data dataset
  with the specified hand type(strength).
  Every hand is string of short card names"
  [ht]
  (get test-data ht))

(defn get-hands-weaker-than
  "returns the collection of hands from the test-data dataset
  where each hand is weaker than provided hand type
  Every hand is string of short card names"
  [ht]
  (let [ht-strength (hand-type ht)
	weaker-selector (fn [ht]
			  (< (last ht) ht-strength))
	weaker-type-names (map #(first %) (filter weaker-selector hand-type))]
    (apply concat (map test-data weaker-type-names))))

(defn get-same-type-hands-weaker-than
  "returns the collection of hands from the test-data dataset
  where each hand is weaker than provided hand type
  Every hand is string of short card names"
  [hand]
  (let [made-hand (get-hand (create-cards hand))
	all-hands (get-hands-with-strongth (:type made-hand))]
    (rest (drop-while #(not (= % hand))
		      all-hands))))

(defn run-test-for-all-types
  ([func]
     (run-test-for-all-types func (keys hand-type)))
  ([func types]
     (if-not (empty? types)
       (let [type (first types)]
	 (func type)
	 (recur func (rest types))))))
