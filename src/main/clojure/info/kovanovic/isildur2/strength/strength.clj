(ns info.kovanovic.isildur2.strength.strength
  (:use info.kovanovic.isildur2.core.concepts
	info.kovanovic.isildur2.util.sort-cards
	clojure.contrib.combinatorics))

(defn get-rank
  [card]
  ((:rank card) rank))

(defn filter-by-suit
  [suit hand]
  (filter #(= suit (:suit %)) hand))

(defn get-highest-straight
  [straights-coll]
  (last (sort-by #(get-rank (first %)) straights-coll)))

(defn decrementing?
  [numbers]
  (= numbers
     (take (count numbers)
	   (iterate dec (first numbers)))))

(defn decrementing-cards?
  [card-coll]
  (let [card-ranks (map get-rank card-coll)]
    (decrementing? card-ranks)))

(defn same-value
  [coll fn]
  (let [res (every? #(= (fn %) (:rank (first coll))) coll)]
    (if res
      coll)))

(defn same-rank
  [cards]
  (same-value cards :rank))

(defn get-run
  [whole-hand size]
  (some same-rank
	(partition size 1 (sort-cards whole-hand))))

(defn get-highest
  [n cards]
  (take n (sort-cards cards)))

(defn remove-cards
  [hand-cards, cards]
  (filter #(not ((set cards) %)) hand-cards))

(defn get-high-card
  [hand-cards]
  (get-highest 5 hand-cards))

(defn get-one-pair
  [hand-cards]
  (let [pair (get-run hand-cards 2)
	kickers (get-highest 3 (remove-cards hand-cards pair))]
    (if pair
      (concat pair kickers))))

(defn get-two-pairs
  [hand-cards]
  (let [first-pair  (take 2 (get-one-pair hand-cards))
	rem1        (remove-cards hand-cards first-pair)
	second-pair (take 2 (get-one-pair rem1))
	kicker      (get-highest 1 (remove-cards rem1 second-pair))]
    (if (and (not (empty? first-pair))
	     (not (empty? second-pair)))
      (concat first-pair
	      second-pair
	      kicker))))

(defn get-three-of-a-kind
  [hand-cards]
  (let [trips (get-run hand-cards 3)
	kickers (get-highest 2 (remove-cards hand-cards trips))]
    (if trips
      (concat trips kickers))))

(defn get-straight
  [hand-cards]
  (let [all-straights (filter decrementing-cards?
			      (map sort-cards
				   (combinations hand-cards 5)))]
    (if-not (empty? all-straights)
      (get-highest-straight all-straights))))

(defn get-flush
  [hand-cards]
  (let [cards-by-suit (map #(filter-by-suit % hand-cards) (keys suit))
	flush-cards (first (filter #(>= (count %) 5) cards-by-suit))]
    (if-not (empty? flush-cards)
      (get-highest 5 flush-cards))))

(defn get-full-house
  [hand-cards]
  (let [trips (take 3 (get-three-of-a-kind hand-cards))
	pair (take 2 (get-one-pair (remove-cards hand-cards trips)))]
    (if (and (not (empty? pair))
	     (not (empty? trips)))
      (concat trips pair))))

(defn get-four-of-a-kind
  [hand-cards]
  (let [quads (get-run hand-cards 4)
	kicker (get-highest 1 (remove-cards hand-cards quads))]
    (if quads
      (concat quads kicker))))

(defn get-straight-flush
  [hand-cards]
  (let [straights-flushes (filter #(and (get-straight %)
					(get-flush %))
				  (map sort-cards
				       (combinations hand-cards 5)))]
    (if-not (empty? straights-flushes)
      (get-highest-straight straights-flushes))))
