from __future__ import division

if __name__=="__main__":
    import sys
    sys.path.insert(0, "..\\")

from util import Point
import serialization
import math as T
import operator

class Train(serialization.Serializable):

    def __init__(self, wheel1=Point(0,0,0), wheel2=Point(0,0,0), length=0, location=Point(0,0,0)):
        self.wheel1=wheel1
        self.wheel2=wheel2
        self.speed=0
        self.offset=0
        self.offset2=0
        self.track1=None
        self.track2=None
        self.D=True
        self.D2=True
        self.rotation=0

    def _calc_length(self):
        temp=self.wheel1.apply(func=operator.sub, point=self.wheel2)
        self.length=(temp.x**2+temp.z**2)**0.5

    def _calc_wheels(self):
        

    def _calc_location_and_rotation(self):
        temp=self.wheel1.apply(func=operator.sub, point=self.wheel2)
        half=temp.apply(func=operator.mul, x=.5, y=1, z=.5)
        self.location=self.wheel1.apply(func=operator.add, point=half)
        self.rotation=T.degrees(T.tan(temp.z/temp.x))
        

    def do_move(self):
        tw1, to, r= self.track1.compute_move(self.offset, self.speed, self.D)
        tw2, to2, r2= self.track2.compute_move(self.offset2, self.speed, self.D2)



        while r > 0:
            #print(r)
            i=self.track1.get_exit_index(tw1)
            print("%s "%i)
            z=self.track1.get_exit(i)
            if z:                
                track_new, D = z[0], z[1]
                self.D=not D > 0
                to=0
                self.track1=track_new

                tw1, to, r = self.track1.compute_move(to, r, self.D)
                

            i=self.track2.get_exit_index(tw2)
            print("%s" % i)
            z=self.track2.get_exit(i)
            if z:                
                track_new, D = z[0], z[1]
                self.D2=not D > 0
                to2=0
                self.track2=track_new

                tw2, to2, r2 = self.track2.compute_move(to2, r2, self.D2)

        self.wheel1=tw1
        self.offset=to
        self.wheel2=tw2
        self.offset2=to2

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
