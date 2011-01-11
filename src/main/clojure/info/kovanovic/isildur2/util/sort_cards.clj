(ns info.kovanovic.isildur2.util.sort-cards
  (:use info.kovanovic.isildur2.core.concepts))

(defn- card-comparator
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

(defn- card-comparator-reverse
  "reverse comparation. first are weaker cards"
  [card1 card2]
  (not (card-comparator card1 card2)))

(defn sort-cards
  [cards]
  (sort-by identity card-comparator cards))

(defn sort-cards-reverse
  [cards]
  (sort-by identity card-comparator-reverse cards))
