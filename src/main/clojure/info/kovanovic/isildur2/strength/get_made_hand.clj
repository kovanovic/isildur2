(ns info.kovanovic.isildur2.strength.get-made-hand
  (:use info.kovanovic.isildur2.core.structs
	info.kovanovic.isildur2.core.concepts
	info.kovanovic.isildur2.strength.get-hand))

(defn create-made-hand-struct
  [seven-card-hand type]
  (if-let [five-card-hand (get-hand type seven-card-hand)]
    (struct-map made-hand
      :type type
      :all-cards seven-card-hand
      :hand-cards five-card-hand)))

(defn get-made-hand
  [seven-card-hand]
  (if-let [hand (create-made-hand-struct seven-card-hand :straight-flush)]
    hand
  (if-let [hand (create-made-hand-struct seven-card-hand :four-of-a-kind)]
    hand
  (if-let [hand (create-made-hand-struct seven-card-hand :full-house)]
    hand
  (if-let [hand (create-made-hand-struct seven-card-hand :flush)]
    hand
  (if-let [hand (create-made-hand-struct seven-card-hand :straight)]
    hand
  (if-let [hand (create-made-hand-struct seven-card-hand :three-of-a-kind)]
    hand
  (if-let [hand (create-made-hand-struct seven-card-hand :two-pairs)]
    hand
  (if-let [hand (create-made-hand-struct seven-card-hand :one-pair)]
    hand
  (create-made-hand-struct seven-card-hand :high-card))))))))))
