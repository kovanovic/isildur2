(ns info.kovanovic.isildur2.strength.test-get-hand
  "this namespace is used to test the strength namespace of the project"
  (:use info.kovanovic.isildur2.get-hand
	info.kovanovic.isildur2.test-fns
	info.kovanovic.isildur2.core
	clojure.test))

(defn- is-hand
  "tests wheather the provided string of short card names
   is of desired hand type. First it transforms the string of card names
   to the list of cards and then after its shuffling checks wheather the return vales
   matches the first 5 cards in the list."
  [card-names]
  (let [cards (create-cards card-names)]
    (is (= (:hand-cards (get-hand (shuffle cards)))
	   (take 5 cards)))))

(defn- is-not-hand
  "tests wheater the provide string of short card names is not
   of the specified hand type. Calling (get-hand hand-type %) should return nil
   so we are checking to see weather it is actually nil."
  [ht card-names]
  (is (not= ht
	    (:type (get-hand (shuffle (create-cards card-names)))))))

(defn test-get-hand-for-hand-type
  ([ht]
     (test-get-hand-for-hand-type
      ht
      (get-hands-with-strongth ht)
      (get-hands-weaker-than ht)))
  
  ([ht valid-hands invalid-hands]
      (if-not (empty? valid-hands)
	(do (is-hand (first valid-hands))
	    (recur ht
		   (rest valid-hands)
		   invalid-hands))
	(if-not (empty? invalid-hands)
	  (do (is-not-hand ht (first invalid-hands))
	      (recur ht
		     valid-hands
		     (rest invalid-hands)))))))

(deftest test-hand-strength
  (run-test-for-all-types test-get-hand-for-hand-type))
