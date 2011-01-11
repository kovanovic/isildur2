(ns info.kovanovic.isildur2.strength.get-hand
  (:use info.kovanovic.isildur2.strength.strength))

(defn- get-hand-disp [type hand]
  type)

(defmulti get-hand get-hand-disp)

(defmethod get-hand :high-card
  [type hand]
  (get-high-card hand))

(defmethod get-hand :one-pair
  [type hand]
  (get-one-pair hand))

(defmethod get-hand :two-pairs
  [type hand]
  (get-two-pairs hand))

(defmethod get-hand :three-of-a-kind
  [type hand]
  (get-three-of-a-kind hand))

(defmethod get-hand :straight
  [type hand]
  (get-straight hand))

(defmethod get-hand :flush
  [type hand]
  (get-flush hand))

(defmethod get-hand :full-house
  [type hand]
  (get-full-house hand))

(defmethod get-hand :four-of-a-kind
  [type hand]
  (get-four-of-a-kind hand))

(defmethod get-hand :straight-flush
  [type hand]
  (get-straight-flush hand))
