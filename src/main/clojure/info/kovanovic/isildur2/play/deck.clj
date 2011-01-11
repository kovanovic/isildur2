(ns info.kovanovic.isildur2.play.deck
  (:use info.kovanovic.isildur2.util.util
	info.kovanovic.isildur2.core.structs))

;;
;; private funcion that returns vector of 2 elements.
;;
;; list of n elements from the deck as first vector element
;; and the remaining of the deck as second vector element
;;
(defn- n-from-deck
  [deck n selected]
  (if (zero? n)
    [selected deck]
    (let [card-idx (rand (count deck))
	  card (nth deck (inc card-idx))
	  before (take card-idx deck)
	  after (drop (inc card-idx) deck)]
      (recur (concat before after)
	     (dec n)
	     (cons card selected)))))

;;
;; funcion that returns vector of 2 elements.
;;
;; selected card or cards from the deck as first element
;; and the remaining of the deck as second element
;;
(defn random-from-deck
  ([deck]
     (random-from-deck deck 1))
  ([deck n]
     (n-from-deck deck n '())))

;;
;; funcion that returns vector of 2 elements.
;;
;; struct-map starting-hand as first element
;; and the remaining of the deck as second element
;;
(defn random-starting-hand
  [deck]
  (let [[[c1 c2] rem-deck] (random-from-deck deck 2)
	starting-hand (struct-map starting-hand
			:card1 c1
			:card2 c2)]

    [starting-hand rem-deck]))

;;
;; funcion that returns vector of 2 elements.
;;
;; struct-map board as first element
;; and the remaining of the deck as second element
;;
(defn random-board
  [deck]
  (let [[[f1 f2 f3 t r] rem-deck] (random-from-deck 5 deck)
	board (struct-map board
		:flop1 f1
		:flop2 f2
		:flop3 f3
		:turn  t
		:river r)]
    [board rem-deck]))

;;
;; funcion that returns vector of 2 elements.
;;
;; struct-map whole-hand as first element
;; and the remaining of the deck as second element
;;
(defn random-whole-hand
  [deck]
  (let [[starting-hand rem-deck1] (random-starting-hand deck)
	[board         rem-deck2] (random-board rem-deck1)
	whole-hand (struct-map whole-hand
		     :starting-hand starting-hand
		     :board board)]
    [whole-hand rem-deck2]))
