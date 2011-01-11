(ns info.kovanovic.isildur2.test-data
  "this namespace contains test data used for testing of strength namespace")

(def test-data {:high-card
		'("Ad Kc 9d 6s 5d 3d 2h"
		  "Ad Kc Jd 8s 6d 5d 2h"
		  "Td 8c 7d 5s 4s 3d 2d"
		  "Jh 9d 8c 5s 4d 3s 2h"
		  "9h 8d 7c 6s 4d 3s 2h")
		
		:one-pair
		'("Ah Ad 9h 7h 5c 4h 2d"
		  "6h 6d 9c 7h 5c 4h 2h"
		  "Ts Td 9h 7h 5c 4h 2h"
		  "2s 2d 9h 7h 5c 4h 3h"
		  "Ah Ad Kc Js Td 9s 2h")
		
		:two-pairs
		'("Ad Ac Kh Kd Js Td 2d"
		  "Ts Td 9h 9d 7d 6s 3c"
		  "5s 5c 2h 2d Ad Kh Ts"
		  "3h 3d 2s 2h Qs 9d 5h"
		  "7d 7c 4s 4h Js 9h 5h")
		
		:three-of-a-kind
		'("Ah Ad Ac Js Td 9s 2h"
		  "Kh Kd Kc Js Td 9s 2h"
		  "9h 9d 9c Js Td 8s 2h"
		  "6h 6d 6c Js Td 9s 2h"
		  "2h 2d 2c Js Td 9s 3h")
		
		:straight
		'("Ad Kc Qs Jd Th 9s 8h"
		  "Kc Qs Jd Th 9s 3h 2d"
		  "Th 9s 8h 7d 6d Ac 5h"
		  "8h 7h 6c 5h 4d As Kc"
		  "6h 5d 4h 3s 2d Kh Ts")
		
		:flush
		'("Ah Kh Th 9h 2h Qs Jd"
		  "Ks 9s 8s 3s 2s Jd 9c"
		  "Kc Qc 9c 7c 2c Ad Kd"
		  "9d 8d 7d 5d 2d Ah Ks"
		  "7h 5h 4h 3h 2h Qd Js")
		
		:full-house
		'("As Ah Ad Kh Kd 9d 2s"
		  "Qs Qh Qd 2s 2d As Kh"
		  "Ts Th Td 3h 3c Qs Jd"
		  "9s 9h 9d 4h 4d As Jc"
		  "2s 2h 2d Ah Ac Kh Qs")
		
		:four-of-a-kind
		'("Qs Qh Qd Qc 2s 2c 2h"
		  "As Ah Ad Ac Qs Td Jh"
		  "2s 2h 2d 2c As Ac Ah"
		  "4s 4h 4d 4c As Kd Qh"
		  "9s 9h 9d 9c Ad 3s 2h")
		
		:straight-flush
		'("Ah Kh Qh Jh Th 9h 8h"
		  "9d 8d 7d 6d 5d 2d 3h"
		  "Qs Js Ts 9s 8s 4c 3h"
		  "Jc Tc 9c 8c 7c Ah Kd"
		  "6d 5d 4d 3d 2d Ac As")})
