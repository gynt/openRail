from __future__ import division

if __name__=="__main__":
    import sys
    sys.path.insert(0, "..\\")

from util import Point
import serialization
import math as T
import operator

class Train(serialization.Serializable):

    def __init__(self, wheel1=None, wheel2=None, length=None, location=None, yaw=None, pitch=None, track1=None, track2=None, track1offset=None, track2offset=None):
        if not location and wheel1 and wheel2:
            self.location, self.yaw, self.pitch = self._calc_location_and_rotation(wheel1, wheel2)
            self.length = self._calc_length(wheel1, wheel2)
            self.wheel1, self.wheel2 = wheel1, wheel2
            self.offset=-1
            self.offset2=-1
        elif location and length and yaw and pitch and not wheel1 and not wheel2:
            self.wheel1, self.wheel2 = self._calc_wheels(location, length, yaw, pitch)
            self.location, self.length, self.yaw, self.pitch = location, length, yaw, pitch
            self.offset=-1
            self.offset2=-1
        elif not track1==None and not track2==None and not track1offset==None and not track2offset==None:
            self.track1=track1
            self.track2=track2
            self.offset=track1offset
            self.offset2=track2offset
            self.wheel1, a, b=track1.compute_move(track1offset, 0, True)
            self.wheel2, a, b=track2.compute_move(track2offset, 0, True)
            self.location, self.yaw, self.pitch = self._calc_location_and_rotation(self.wheel1, self.wheel2)
            self.length = self._calc_length(self.wheel1, self.wheel2)            
        else:
            raise Exception("Invalid combination of arguments")
        
        self.speed=0
        #self.offset=0
        #self.offset2=0
        #self.track1=None
        #self.track2=None
        self.D=True
        self.D2=True

    def _calc_length(self, wheel1, wheel2):
        temp=wheel1.apply(func=operator.sub, point=wheel2)
        length=T.sqrt(T.pow(temp.x,2)+T.pow(temp.z, 2))
        return length

    def _calc_wheels(self, location, length, yaw, pitch):
        wheel1=location.apply(func=operator.add
                                   , x=0.5*length*(T.cos(T.radians(yaw)))
                                   , y=0.5*length*T.sin(T.radians(pitch))
                                   , z=0.5*length*(T.sin(T.radians(yaw)))
                                   )
        wheel2=location.apply(func=operator.add
                                   , x=0.5*length*(T.cos(T.radians(yaw+180)))
                                   , y=0.5*length*T.sin(T.radians(pitch+180))
                                   , z=0.5*length*(T.sin(T.radians(yaw+180)))
                                   )
        return wheel1, wheel2
        

    def _calc_location_and_rotation(self, wheel1, wheel2):
        temp=wheel1.apply(func=operator.sub, point=wheel2)
        half=temp.apply(func=operator.mul, x=.5, y=.5, z=.5)
        location=wheel1.apply(func=operator.add, point=half)
        yaw=T.degrees(T.atan(temp.z/temp.x))
        pitch=T.degrees(T.atan(temp.y/temp.x))
        return location, yaw, pitch
        
    def do_move(self):
        tw1, to, r= self.track1.compute_move(self.offset, self.speed, self.D)
        tw2, to2, r2= self.track2.compute_move(self.offset2, self.speed, self.D2)



        while r > 0:
            print(r)
            i=self.track1.get_exit_index(tw1)
            #print("%s "%i)
            z=self.track1.get_exit(i)
            if z:                
                track_new, D = z[0], z[1]
                self.D=not D > 0
                to=0
                self.track1=track_new

                tw1, to, r = self.track1.compute_move(to, r, self.D)
            else:
                self.speed=0
                break

        while r2 > 0:
            i=self.track2.get_exit_index(tw2)
            #print("%s" % i)
            z=self.track2.get_exit(i)
            if z:                
                track_new, D = z[0], z[1]
                self.D2=not D > 0
                to2=0
                self.track2=track_new

                tw2, to2, r2 = self.track2.compute_move(to2, r2, self.D2)
            else:
                self.speed=0
                break

        self.wheel1=tw1
        self.offset=to
        self.wheel2=tw2
        self.offset2=to2
        self.location, self.yaw, self.pitch = self._calc_location_and_rotation(tw1, tw2)

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
