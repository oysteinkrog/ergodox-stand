(ns ergodox-tent.core
  (:use [scad-clj.scad])
  (:use [scad-clj.model]))


;; Set your tent and tilt angles in degrees here

(def tent-degrees 6)
(def tilt-degrees 3.5)




;; Note that I did this using the values from lister's case design.
;; I got all the numbers from the SVG, which represents all values
;; in pixels, with 90 pixels per inch.  At the end, I scale everything
;; to be in mm.

(def rect-depth 124.712) ; mm
(def rect-width 147.369) ; mm
(def rect-height 30) ; mm

(def curve-radius 7.282) ; mm

(def total-width (+ rect-width (* 2 curve-radius)))
(def total-depth (+ rect-depth (* 2 curve-radius)))

(def tent-slope (* tent-degrees (/ Math/PI 180)))
(def tilt-slope (* tilt-degrees (/ Math/PI 180)))

(def screw-radius 1.641) ; mm
(def nut-radius (* 2 screw-radius))

(def nut-hole
  (cylinder nut-radius (* 5 rect-height)))

(def main-base
  (difference
   (translate [0 0 (/ rect-height 2)]
              (union
               (cube total-width rect-depth rect-height)
               (cube rect-width total-depth rect-height)
               (translate [(/ rect-width 2) (/ rect-depth 2) 0] (cylinder curve-radius rect-height))
               (translate [(/ rect-width -2) (/ rect-depth 2) 0] (cylinder curve-radius rect-height))
               (translate [(/ rect-width -2) (/ rect-depth -2) 0] (cylinder curve-radius rect-height))
               (translate [(/ rect-width 2) (/ rect-depth -2) 0] (cylinder curve-radius rect-height))))
   (->> nut-hole
        (translate [(+ (/ rect-width 2) 1.129) ; mm
                    (+ (/ rect-depth 2) 1.129) ; mm
                    0]))
   (->> nut-hole
        (translate [(+ (/ rect-width 2) 1.129 -53.483) ; mm
                    (+ (/ rect-depth 2) 1.129 1.82) ; mm
                    0]))
   (->> nut-hole
        (translate [(+ (/ rect-width 2) 1.129 -68.147) ; mm
                    (+ (/ rect-depth 2) 1.129 -127.72) ; mm
                    0]))))


; (def shift-down-height
;   (let [x (* (/ total-width 2) (Math/tan tent-slope))]
;     (* (- rect-height x)
;        (Math/cos tent-slope))))

(def flush-top
  (->> (union main-base (translate [0 0 (- rect-height)] main-base))
  		(rotate 0 [0 0 0])
;       (rotate (- tent-slope) [0 1 0])
;       (rotate tilt-slope [1 0 0])
;       (translate [0 0 (- shift-down-height)])
	))

(def tent
         (difference
          flush-top
          (translate [0 0 -250] (cube 700 700 500))
          (translate [-375 0 -0] (cube 700 700 500))
          (translate [0 0 100] (cube (* 0.9 rect-width) (* 0.95 rect-depth) 300))))

(spit "resources/tent.scad"
      (write-scad
       (union
        tent
;        (->> tent
;             (mirror [1 0 0])
;             (translate [50 (- 20) 0]))
		)))
