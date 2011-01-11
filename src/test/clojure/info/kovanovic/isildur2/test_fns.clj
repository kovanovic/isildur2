(ns info.kovanovic.isildur2.test-fns
  "set of functions that are used to ckeck functions in the strength package"
  (:use	info.kovanovic.isildur2.core.concepts
	info.kovanovic.isildur2.util.sort-cards
	info.kovanovic.isildur2.test-data
	clojure.test))

(defn get-hands-with-strongth
  "returns the collection of hands from the test-data dataset
  with the specified hand type(strength).
  Every hand is string of short card names"
  [type]
  (get test-data type))

(defn get-hands-weaker-than
  "returns the collection of hands from the test-data dataset
  where each hand is weaker than provided hand type
  Every hand is string of short card names"
  [type]
  (let [type-strength (hand-type type)
	weaker-types (map #(get % 0)
			  (filter #(< (get % 1) type-strength)
				  hand-type))]
    (apply concat (map #(get test-data %)
		       weaker-types))))

(defn run-test-for-all-types
  ([func]
     (run-test-for-all-types func (keys hand-type)))
  ([func types]
     (if-not (empty? types)
       (let [type (first types)]
	 (func type)
	 (recur func (rest types))))))
