(ns info.kovanovic.isildur2.strength.get-winner
  (:use info.kovanovic.isildur2.core.structs
	info.kovanovic.isildur2.core.concepts
	info.kovanovic.isildur2.strength.get-made-hand))

(defn- compare-as-high-card-hands
  [h1 h2]
  (if (empty? h1)
    :same-hands
    (let [highest-h1 (rank (:rank (first h1)))
	  highest-h2 (rank (:rank (first h2)))]
      (if (> highest-h1 highest-h2)
	true
	(recur (rest h1)
	       (rest h2))))))

(defn- bigger-high-card
  [h1 h2]
  (if (compare-as-high-card-hands
       (:made-hand h1)
       (:made-hand h2))
    h1
    h2))

(defmulti bigger-same-type (fn [seven-card-hand1 seven-card-hand2]
			       (:type seven-card-hand1)))

(defmethod bigger-same-type :high-card
  [seven-card-hand1 seven-card-hand2]
  (bigger-high-card seven-card-hand1 seven-card-hand2))

(defmethod bigger-same-type :one-pair
  [seven-card-hand1 seven-card-hand2]
  (println "todo")
  seven-card-hand1)

(defmethod bigger-same-type :two-pair
  [seven-card-hand1 seven-card-hand2]
  (println "todo")
  seven-card-hand1)

(defmethod bigger-same-type :three-of-a-kind
  [seven-card-hand1 seven-card-hand2]
  (println "todo")
  seven-card-hand1)

(defmethod bigger-same-type :straight
  [seven-card-hand1 seven-card-hand2]
  (println "todo")
  seven-card-hand1)

(defmethod bigger-same-type :flush
  [seven-card-hand1 seven-card-hand2]
  (bigger-high-card seven-card-hand1 seven-card-hand2))

(defmethod bigger-same-type :full-house
  [seven-card-hand1 seven-card-hand2]
  (println "todo")
  seven-card-hand1)

(defmethod bigger-same-type :four-of-a-kind
  [seven-card-hand1 seven-card-hand2]
  (println "todo")
  seven-card-hand1)

(defmethod bigger-same-type :straight-flush
  [seven-card-hand1 seven-card-hand2]
  (println "todo")
  seven-card-hand1)

(defn get-winner
  [seven-card-hand1 seven-card-hand2]
  (let [ch1 (get-made-hand seven-card-hand1)
	ch2 (get-made-hand seven-card-hand2)

	type-rank-h1 (hand-type (:type ch1))
	type-rank-h2 (hand-type (:type ch2))]
    (if (> type-rank-h1 type-rank-h2)
      ch1
      (if (< type-rank-h1 type-rank-h2)
	ch2
	(bigger-same-type seven-card-hand1 seven-card-hand2)))))
