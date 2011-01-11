(ns info.kovanovic.isildur2.util.util
  (:use info.kovanovic.isildur2.core.concepts))

(defn get-cards-from-card-names
  "creates list of cards from the provided string of short-card names.
   For example string \"Ad Ac\" returns the list of two appropriate cards."
  [hand-str]
  (apply hand (map keyword (.split hand-str " "))))

(defn get-cards-from-whole-hand
  [whole-hand]
  (vals (concat (:starting-hand whole-hand)
		(:board whole-hand))))

(defn print-cards
  [cards]
  (if-not (empty? cards)
    (let [card (first cards)]
      (println (:rank card) (:suit card))
      (recur (rest cards)))))
