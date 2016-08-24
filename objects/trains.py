from __future__ import division

if __name__=="__main__":
    import sys
    sys.path.insert(0, "..\\")

from util import Point
import serialization

class Train(serialization.Serializable):

    def __init__(self, wheel1=Point(0,0,0), wheel2=Point(0,0,0)):
        self.wheel1=wheel1
        self.wheel2=wheel2
        self.speed=0
        self.offset=0
        self.D=True




def test_loop(train, track):
    import time
    while True:
        time.sleep(1)
        train.wheel1, train.offset, remaining=track.compute_move(train.offset, train.speed, train.D)
        print("%s\t\t%s\t\t%s" % (train.wheel1, train.offset, remaining))
        if remaining > 0:
            break
        


if __name__=="__main__":
    from objects.rails import StraightTrack, CurveTrack
    s=CurveTrack(rotation=180, radius=5, angle=360/16)
    t=Train(wheel1=s.location)
    t.speed=0.1
    test_loop(t, s)
