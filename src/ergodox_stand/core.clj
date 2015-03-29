(ns ergodox-stand.core
  (:use [scad-clj.scad])
  (:use [scad-clj.model]))

;; Note:
;; adereth's version used SVG pixels (1/90th of an inch) for dimensions
;; I changed everything to mm for my own sanity



(def standard-depth 124.712)
(def fullhand-depth 200.139)







;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; CUSTOMIZABLES

; Which case do you want to support?
(def rect-depth fullhand-depth)	; choose fullhand-depth or standard-depth

; Angles for tenting and tilting, to your preferences
(def tent-degrees 6)			; side-to-side angle
(def tilt-degrees -3.5)			; front-to-back angle

; Baseline height determines how far down to clip the bottom flat.
; You probably want a complete model with at least 5mm extra for screws and
; nuts to hang down.
(def baseline-height-adjust 20)	; adjust the baseline for clipping the bottom

; Tenting and tilting affects how close the mirrored pieces sit to one
; another. Adjust these values so that the gaps are small but at least a
; few mm apart.
(def mirror-x-gap 5)			; side-to-side gap between mirrored pieces
(def mirror-y-gap 5)			; back-to-front gap between mirrored pieces

;; STOP CUSTOMIZING
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;








(def rect-width 147.087)
(def profile-height 50)
(def curve-radius 7.282)
(def total-width (+ rect-width (* 2 curve-radius)))
(def total-depth (+ rect-depth (* 2 curve-radius)))

(def tent-slope (* tent-degrees (/ Math/PI 180)))
(def tilt-slope (* tilt-degrees (/ Math/PI 180)))

(def screw-radius 1.641)
(def nut-radius (* 2 (+ screw-radius 0.125)))

(def nut-hole
	(cylinder nut-radius (* 5 profile-height)))

(def main-base
	(difference

		;; BASIC SHAPE
		; rectangular prism with rounded edges
		(translate [0 0 (/ profile-height 2)]
			(union
				; main area
				(cube total-width rect-depth profile-height)
				(cube rect-width total-depth profile-height)

				; rounded edges
				(translate [(/ rect-width 2)  (/ rect-depth 2)  0] (cylinder curve-radius profile-height))
				(translate [(/ rect-width -2) (/ rect-depth 2)  0] (cylinder curve-radius profile-height))
				(translate [(/ rect-width -2) (/ rect-depth -2) 0] (cylinder curve-radius profile-height))
				(translate [(/ rect-width 2)  (/ rect-depth -2) 0] (cylinder curve-radius profile-height))
			)
		)



		;; NUT HOLES (directions wrt to left-hand side)
		; top right
		(->> nut-hole
			(translate [(+ (/ rect-width 2) 1.129)
						(+ (/ rect-depth 2) 1.129)
						0]))

		; top middle
		(->> nut-hole
			(translate [(+ (/ rect-width 2) 1.129 -53.483)
						(+ (/ rect-depth 2) 1.129 1.82)
						0]))

		; top left
		(->> nut-hole
			(translate [(+ (/ rect-width 2) 1.129 -148.553)
						(+ (/ rect-depth 2) 1.129)
						0]))

		; middle left (bottom left on standard case)
		(->> nut-hole
		 	(translate [(+ (/ rect-width 2) 1.129 -148.553)
		 				(+ (/ rect-depth 2) 1.129 -124.315)
		 				0]))

		; middle-middle (bottom middle on standard case)
		(->> nut-hole
			(translate [(+ (/ rect-width 2) 1.129 -68.147)
						(+ (/ rect-depth 2) 1.129 -127.721)
						0]))

		; false middle-right hole, for material savings in standard-size leg
		(if (= rect-depth standard-depth)
			(->> nut-hole
				(translate [(+ (/ rect-width 2) 1.129)
							(+ (/ rect-depth 2) 1.129 -127.721)
							0])
			)
			nil
		)

		; bottom left
		(->> nut-hole
			(translate [(+ (/ rect-width 2) 1.129 -148.553)
						(+ (/ rect-depth 2) 1.129 -201.611)
						0]))

		; bottom right
		(->> nut-hole
			(translate [(+ (/ rect-width 2) 1.129 -1.876)
						(+ (/ rect-depth 2) 1.129 -202.003)
						0]))



		;; HOLLOWED-OUT AREAS
		; The more you hollow out, the cheaper!
		(translate [10 0 (/ profile-height 2)]
			(cube (+ rect-width 10) (- rect-depth 8) (+ profile-height 10))
		)

		; Lateral underside hollowing
		(translate [0 0 (+ -10 (/ profile-height 2))]
			(cube (+ 5 total-width) (- rect-depth 15) (+ profile-height 10))
		)

		; Front-back underside hollowing
		(translate [0 0 (+ -10 (/ profile-height 2))]
			(cube (- rect-width 11.5) (+ 5 total-depth) (+ profile-height 10))
		)
	) ; difference
) ; def main-base

(def rotated-clipped
	(difference
		(->> main-base (rotate (- tent-slope) [0 1 0]) (rotate tilt-slope [1 0 0]))
		(translate [0 0 (- baseline-height-adjust)] (cube 1000 1000 100))
	)
)

(def flipped
	(->> rotated-clipped (rotate (- tilt-slope) [1 0 0]) (rotate tent-slope [0 1 0]) (rotate Math/PI [0 1 0]))
)

(def mirrored
	(union
		flipped
		(->> flipped
			(mirror [1 0 0])
			(translate [(- (+ 12 mirror-x-gap)) (- (+ mirror-y-gap 12)) 0])
		)
	)
)

(spit "resources/stand.scad" (write-scad mirrored))