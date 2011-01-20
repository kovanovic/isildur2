(ns info.kovanovic.isildur2.get-winner
  [:use [info.kovanovic.isildur2
	 [core :only (rank
		      get-rank
		      same-by-rank
		      get-type)]
	 [get-hand :only (get-hand)]]])

(defn is-bigger-high-card
  [cards1 cards2]
  (if-not (empty? cards1)
    (let [r1 (get-rank (first cards1))
	  r2 (get-rank (first cards2))]
      (cond
       (> r1 r2) 1
       (< r1 r2) 2
       true (recur (rest cards1)
		   (rest cards2))))
    0))

(defn get-stronger-as-high-card
  [hand1 hand2]
  (let [winner (is-bigger-high-card (:hand-cards hand1)
				    (:hand-cards hand2))]
    (condp = winner
	0 (list 0 hand1 hand2)
	1 (list 1 hand1 hand2)
	2 (list 2 hand2 hand1))))

(defmulti get-winner-same-type (fn [hand1 _] (:type hand1)))

(defmethod get-winner-same-type :high-card
  [hand1 hand2]
  (get-stronger-as-high-card hand1 hand2))

(defmethod get-winner-same-type :one-pair
  [hand1 hand2]
  (let [r1 (get-rank (first (:hand-cards hand1)))
	r2 (get-rank (first (:hand-cards hand2)))]
    (cond
     (> r1 r2) (list 1 hand1 hand2)
     (< r1 r2) (list 2 hand2 hand1)
     true      (get-stronger-as-high-card hand1 hand2))))

(defmethod get-winner-same-type :two-pairs
  [hand1 hand2]
  (let [fp1 (get-rank (first (:hand-cards hand1)))
	fp2 (get-rank (first (:hand-cards hand2)))
	sp1 (get-rank (first (drop 2 (:hand-cards hand1))))
	sp2 (get-rank (first (drop 2 (:hand-cards hand2))))
	k1 (get-rank (last (:hand-cards hand1)))
	k2 (get-rank (last (:hand-cards hand2)))]
    (cond
     (> fp1 fp2) (list 1 hand1 hand2)
     (< fp1 fp2) (list 2 hand2 hand1)
     (> sp1 sp2) (list 1 hand1 hand2)
     (< sp1 sp2) (list 2 hand2 hand1)
     (> k1 k2)   (list 1 hand1 hand2)
     (< k1 k2)   (list 2 hand2 hand1)
     true        (list 0 hand1 hand2))))

(defmethod get-winner-same-type :three-of-a-kind
  [hand1 hand2]
  (let [t1 (rank (:rank (first (:hand-cards hand1))))
	t2 (rank (:rank (first (:hand-cards hand2))))]
    (cond
     (> t1 t2) (list 1 hand1 hand2)
     (< t1 t2) (list 2 hand2 hand1)
     true      (get-stronger-as-high-card hand1 hand2))))

(defmethod get-winner-same-type :straight
  [hand1 hand2]
  (get-stronger-as-high-card hand1 hand2))

(defmethod get-winner-same-type :flush
  [hand1 hand2]
  (get-stronger-as-high-card hand1 hand2))

(defmethod get-winner-same-type :full-house
  [hand1 hand2]
  (let [t1 (rank (:rank (first (:hand-cards hand1))))
	t2 (rank (:rank (first (:hand-cards hand2))))
	p1 (rank (:rank (first (drop 3 (:hand-cards hand1)))))
	p2 (rank (:rank (first (drop 3 (:hand-cards hand2)))))]
    (cond
     (> t1 t2) (list 1 hand1 hand2)
     (< t1 t2) (list 2 hand2 hand1)
     (> p1 p2) (list 1 hand1 hand2)
     (< p1 p2) (list 2 hand2 hand1)
     true      (list 0 hand1 hand2))))

(defmethod get-winner-same-type :four-of-a-kind
  [hand1 hand2]
  (let [q1 (rank (:rank (first (:hand-cards hand1))))
	q2 (rank (:rank (first (:hand-cards hand2))))
	k1 (rank (:rank (last (:hand-cards hand1))))
	k2 (rank (:rank (last (:hand-cards hand2))))]
    (cond
     (> q1 q2) (list 1 hand1 hand2)
     (< q1 q2) (list 2 hand2 hand1)
     (> k1 k2) (list 1 hand1 hand2)
     (< k1 k2) (list 2 hand2 hand1)
     true      (list 0 hand1 hand2))))

(defmethod get-winner-same-type :straight-flush
  [hand1 hand2]
  (get-stronger-as-high-card hand1 hand2))

(defn get-winner
  [cards1 cards2]
  (let [hand1 (get-hand cards1)
	hand2 (get-hand cards2)
	hr1 (get-type hand1)
	hr2 (get-type hand2)]
    (cond
     (> hr1 hr2)
     (list 1 hand1 hand2)
     
     (< hr1 hr2)
     (list 2 hand2 hand1)

     (same-by-rank (:hand-cards hand1)
		   (:hand-cards hand2))
     (list 0 hand1 hand2)
     
     true
     (get-winner-same-type hand1 hand2))))
