(ns info.kovanovic.isildur2.core
  [:require [clojure.contrib.string :as s :only trim]])

(def hand-type {:high-card       1
		:one-pair        2
		:two-pairs       3
		:three-of-a-kind 4
		:straight        5
		:flush           6
		:full-house      7
		:four-of-a-kind  8
		:straight-flush  9})

(def rank {:A 14
	   :K 13
	   :Q 12
	   :J 11
	   :T 10
	   :9  9
	   :8  8
	   :7  7
	   :6  6
	   :5  5
	   :4  4
	   :3  3
	   :2  2})

(def suit {:spades   4
	   :hearts   3
	   :diamonds 2
	   :clubs    1})

(defstruct card
  :rank
  :suit)

(defn get-card-name
  [card]
  (let [suit (name (:suit card))
	rank (name (:rank card))]
    (str rank (first suit))))

(defn get-rank
  [card]
  ((:rank card) rank))

(defstruct whole-hand
  :starting-hand
  :board)

(defn get-whole-hand-cards
  [whole-hand]
  (vals (concat (:starting-hand whole-hand)
		(:board whole-hand))))
(defstruct hand
  :type
  :all-cards
  :hand-cards)

(defn get-type
  [hand]
  ((:type hand) hand-type))

(defn create-card-names
  [cards]
  (s/trim (apply str (map #(str (get-card-name %) " ") cards))))

(def deck (let [cards (for [r (keys rank)
			    s (keys suit)]
			(struct-map card
			  :rank r
			  :suit s))
		card-names (map get-card-name cards)]
	    (apply hash-map (interleave card-names cards))))

(defn create-cards
  "function that returns actual cards from string such as \"Ad Ks Qh\""
  [cards]
  (map deck (.split cards " ")))

(defn print-cards
  [cards]
  (if-not (empty? cards)
    (let [card (first cards)]
      (println (:rank card) (:suit card))
      (recur (rest cards)))))

(defn card-comparator
  "compares two cards. first are stronger cards
  first by rank and then if rank is the same, by suit."
  [card1 card2]
  (let [r1 ((:rank card1) rank)
	r2 ((:rank card2) rank)
	s1 ((:suit card1) suit)
	s2 ((:suit card2) suit)]
    (if (not= r1 r2) 
      (> r1 r2)
      (> s1 s2))))

(defn sort-cards
  [cards]
  (sort-by identity card-comparator cards))

(defn get-random-from-deck
  [deck n]
  (split-at n (shuffle deck)))

(defn compare-cards
  [cards1 cards2]
  (if (empty? cards1)
      true
      (let [r1 (get-rank (first cards1))
	    r2 (get-rank (first cards2))]
	(if-not (= r1 r2)
	  false
	  (recur (rest cards1)
		 (rest cards2))))))

(defn same-by-rank
  [cards1 cards2]
  (if-not (= (count cards1)
	     (count cards2))
    false
    (compare-cards (sort-cards cards1)
		   (sort-cards cards2))))
