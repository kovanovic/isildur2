(ns info.kovanovic.isildur2.core.structs)

(defstruct card
  :rank
  :suit)

(defstruct starting-hand
  :card1
  :card2)

(defstruct board
  :flop1
  :flop2
  :flop3
  :turn
  :river)

(defstruct whole-hand
  :starting-hand
  :board)

(defstruct made-hand
  :type
  :all-cards
  :hand-cards)
