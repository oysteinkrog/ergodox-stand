union () {
  difference () {
    rotate (a=0.0, v=[0,0,0]) {
      union () {
        difference () {
          translate ([0,0,15]) {
            union () {
              cube([161.933, 124.712, 30], center=true);
              cube([147.369, 139.276, 30], center=true);
              translate ([73.6845,62.356,0]) {
                cylinder (h=30, r=7.282, center=true);
              }
              translate ([-73.6845,62.356,0]) {
                cylinder (h=30, r=7.282, center=true);
              }
              translate ([-73.6845,-62.356,0]) {
                cylinder (h=30, r=7.282, center=true);
              }
              translate ([73.6845,-62.356,0]) {
                cylinder (h=30, r=7.282, center=true);
              }
            }
          }
          translate ([74.8135,63.485,0]) {
            cylinder (h=150, r=3.282, center=true);
          }
          translate ([21.330500000000008,65.30499999999999,0]) {
            cylinder (h=150, r=3.282, center=true);
          }
          translate ([6.666499999999999,-64.235,0]) {
            cylinder (h=150, r=3.282, center=true);
          }
        }
        translate ([0,0,-30]) {
          difference () {
            translate ([0,0,15]) {
              union () {
                cube([161.933, 124.712, 30], center=true);
                cube([147.369, 139.276, 30], center=true);
                translate ([73.6845,62.356,0]) {
                  cylinder (h=30, r=7.282, center=true);
                }
                translate ([-73.6845,62.356,0]) {
                  cylinder (h=30, r=7.282, center=true);
                }
                translate ([-73.6845,-62.356,0]) {
                  cylinder (h=30, r=7.282, center=true);
                }
                translate ([73.6845,-62.356,0]) {
                  cylinder (h=30, r=7.282, center=true);
                }
              }
            }
            translate ([74.8135,63.485,0]) {
              cylinder (h=150, r=3.282, center=true);
            }
            translate ([21.330500000000008,65.30499999999999,0]) {
              cylinder (h=150, r=3.282, center=true);
            }
            translate ([6.666499999999999,-64.235,0]) {
              cylinder (h=150, r=3.282, center=true);
            }
          }
        }
      }
    }
    translate ([0,0,-250]) {
      cube([700, 700, 500], center=true);
    }
    translate ([-375,0,0]) {
      cube([700, 700, 500], center=true);
    }
    translate ([0,0,100]) {
      cube([132.6321, 118.4764, 300], center=true);
    }
  }
}
