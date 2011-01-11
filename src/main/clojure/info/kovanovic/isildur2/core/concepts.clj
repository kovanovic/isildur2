(ns info.kovanovic.isildur2.core.concepts
  (:use info.kovanovic.isildur2.core.structs))

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

(def deck (let [cards (for [r (keys rank)
			    s (keys suit)]
			(struct-map card
			  :rank r
			  :suit s))
		create-name #(str (name (:rank %))
				  (first (name (:suit %))))
		card-names (map create-name cards)]
	    (apply hash-map (interleave card-names cards))))

(defn hand
  [& cards]
  (map #(deck (name %)) cards))
