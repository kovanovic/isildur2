(ns info.kovanovic.isildur2.strength.test-get-hand
  "this namespace is used to test the strength namespace of the project"
  (:use info.kovanovic.isildur2.strength.get-hand
	info.kovanovic.isildur2.test-fns
	info.kovanovic.isildur2.core.concepts
	info.kovanovic.isildur2.util.util
	clojure.test))

(defn- is-hand
  "tests wheather the provided string of short card names
   is of desired hand type. First it transforms the string of card names
   to the list of cards and then after its shuffling checks wheather the return vales
   matches the first 5 cards in the list."
  [type card-string]
  (let [hand-cards (get-cards-from-card-names card-string)]
    (is (= (get-hand type (shuffle hand-cards))
	   (take 5 hand-cards)))))

(defn- is-not-hand
  "tests wheater the provide string of short card names is not
   of the specified card type. Calling (get-hand type %) should return nil
   so we are checking to see weather it is actually nil."
  [type card-string]
  (is (nil? (get-hand type (shuffle (get-cards-from-card-names card-string))))))

(defn test-get-hand-for-type
  ([type]
     (test-get-hand-for-type type
		     (get-hands-with-strongth type)
		     (get-hands-weaker-than type)))
  ([type valid-hands invalid-hands]
      (if-not (empty? valid-hands)
	(do (is-hand type (first valid-hands))
	    (recur type
		   (rest valid-hands)
		   invalid-hands))
	(if-not (empty? invalid-hands)
	  (do (is-not-hand type (first invalid-hands))
	      (recur type
		     valid-hands
		     (rest invalid-hands)))))))

(deftest test-hand-strength
  (run-test-for-all-types test-get-hand-for-type))
