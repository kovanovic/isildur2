(ns info.kovanovic.isildur2.get-odds
  [:use [info.kovanovic.isildur2
	 [core :only (get-random-from-deck
		      deck
		      sort-cards
		      print-cards
		      create-cards)]
	 [get-winner :only (get-winner)]]])

(defn get-deck-reminder
  [cards1 cards2 board-cards]
  (let [used-cards (set (concat cards1
				cards2
				board-cards))]
    (filter #(not (used-cards %)) (vals deck))))

(defn get-random-boards
  [deck c]
  (for [ _ (range c)]
    (first (get-random-from-deck deck 5))))

(defn run-simulation
  [cards1 cards2 board-cards deck cards-to-deal]
  (let [random-cards (get (get-random-from-deck deck cards-to-deal) 0)]

    (first (get-winner (concat cards1 board-cards random-cards)
		       (concat cards2 board-cards random-cards)))))

(defn inc-key
  [map key]
  (assoc map key (inc (key map))))

(defn count-wins
  [odds, outcome]
  (condp = outcome
      0 (inc-key odds :tie)
      1 (inc-key odds :hand1)
      2 (inc-key odds :hand2)))

(defn convert-wins-to-percentage
  [wins iters]
  (double (* (/ wins iters) 100)))

(defn convert-to-percentages
  [odds iter-count card-names1 card-names2]
  (list
   (str card-names1 ":")
   (convert-wins-to-percentage (:hand1 odds) iter-count)
   (str card-names2 ":")
   (convert-wins-to-percentage (:hand2 odds) iter-count)
   "ties:"
   (convert-wins-to-percentage (:tie odds) iter-count)))

(defn get-odds
  ([card-names1 card-names2 board-cards-names]
      (let [iter-count 1000
	    cards1 (create-cards card-names1)
	    cards2 (create-cards card-names2)
	    board-cards (create-cards board-cards-names)
	    deck-reminder (get-deck-reminder cards1 cards2 board-cards)
	    cards-to-deal (- 5 (count board-cards))
	    simulation-results (pmap (fn [_] (run-simulation cards1
							     cards2
							     board-cards
							     deck-reminder
							     cards-to-deal))
				     (range iter-count))
	    counted-wins (reduce count-wins
				 {:hand1 0
				  :hand2 0
				  :tie 0}
				 simulation-results)
	    wins-in-percentages (convert-to-percentages counted-wins
							iter-count
							card-names1
							card-names2)]
	(str (nth wins-in-percentages 0) (nth wins-in-percentages 1) "% "
	     (nth wins-in-percentages 2) (nth wins-in-percentages 3) "% "
	     (nth wins-in-percentages 4) (nth wins-in-percentages 5) "%")))
  ([card-names1 card-names2]
     (get-odds card-names1 card-names2 " ")))
