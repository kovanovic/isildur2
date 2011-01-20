(ns info.kovanovic.isildur2.get-hand
  [:use [info.kovanovic.isildur2.core :only (get-rank
					     sort-cards
					     print-cards
					     suit
					     hand)]]
  [:use [clojure.contrib.combinatorics :only (combinations)]])

(defn filter-by-suit
  [suit cards]
  (filter #(= suit (:suit %)) cards))

(defn get-highest-straight
  [straights-coll]
  (last (sort-by #(get-rank (first %)) straights-coll)))

(defn decrementing?
  [numbers]
  (= numbers
     (take (count numbers)
	   (iterate dec (first numbers)))))

(defn decrementing-cards?
  [cards]
  (decrementing? (map get-rank cards)))

(defn same-value
  [coll fn]
  (let [res (every? #(= (fn %) (:rank (first coll))) coll)]
    (if res
      coll)))

(defn same-rank
  [cards]
  (same-value cards :rank))

(defn get-run
  [cards size]
  (some same-rank
	(partition size 1 (sort-cards cards))))

(defn get-highest
  [n cards]
  (take n (sort-cards cards)))

(defn remove-cards
  [cards, cards-to-remove]
  (filter #(not ((set cards-to-remove) %)) cards))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn get-high-card
  [cards]
  (get-highest 5 cards))

(defn get-one-pair
  [cards]
  (let [pair (get-run cards 2)
	kickers (get-highest 3 (remove-cards cards pair))]
    (if pair
      (concat pair kickers))))

(defn get-two-pairs
  [cards]
  (let [first-pair  (get-run cards 2)
	rem1 (remove-cards cards first-pair)
	second-pair (get-run rem1 2)
	kicker (get-highest 1 (remove-cards rem1 second-pair))]
    (if-not (or (empty? first-pair)
		(empty? second-pair))
      (concat first-pair
	      second-pair
	      kicker))))

(defn get-three-of-a-kind
  [cards]
  (let [trips (get-run cards 3)
	kickers (get-highest 2 (remove-cards cards trips))]
    (if trips
      (concat trips kickers))))

(defn get-straight
  [cards]
  (let [all-straights (filter decrementing-cards?
			      (map sort-cards
				   (combinations cards 5)))]
    (if-not (empty? all-straights)
      (get-highest-straight all-straights))))

(defn get-flush
  [cards]
  (let [cards-by-suit (map #(filter-by-suit % cards) (keys suit))
	flush-cards (first (filter #(>= (count %) 5) cards-by-suit))]
    (if-not (empty? flush-cards)
      (get-highest 5 flush-cards))))

(defn get-full-house
  [cards]
  (let [trips (get-run cards 3)
	pair (get-run (remove-cards cards trips) 2)]
    (if-not (or (empty? pair)
		(empty? trips))
      (concat trips pair))))

(defn get-four-of-a-kind
  [cards]
  (let [quads (get-run cards 4)
	kicker (get-highest 1 (remove-cards cards quads))]
    (if quads
      (concat quads kicker))))

(defn get-straight-flush
  [cards]
  (let [straights-flushes (filter #(and (get-straight %)
					(get-flush %))
				  (map sort-cards
				       (combinations cards 5)))]
    (if-not (empty? straights-flushes)
      (get-highest-straight straights-flushes))))

(defn create-hand
  [cards func ht]
  (if-let [used-cards (func cards)]
    (struct-map hand
      :type ht
      :all-cards cards
      :hand-cards used-cards)))

(defn get-hand
  [cards]
  (if-let [h (create-hand cards get-straight-flush :straight-flush)]
    h
    (if-let [h (create-hand cards get-four-of-a-kind :four-of-a-kind)]
      h
      (if-let [h (create-hand cards get-full-house :full-house)]
	h
	(if-let [h (create-hand cards get-flush :flush)]
	  h
	  (if-let [h (create-hand cards get-straight :straight)]
	    h
	    (if-let [h (create-hand cards get-three-of-a-kind :three-of-a-kind)]
	      h
	      (if-let [h (create-hand cards get-two-pairs :two-pairs)]
		h
		(if-let [h (create-hand cards get-one-pair :one-pair)]
		  h
		  (create-hand cards get-high-card :high-card))))))))))
