import turtle
screen=turtle.Screen()

tt=turtle.Turtle()

import trackmanager
from objects.tracks import StraightTrack, CurveTrack
from objects.trains import Train
from util import Point

def test_loop(train, track):
    import time
    c=True
    c1="blue"
    c2="red"
    tt.color(c2,c2)
    while True:
        print(tt.pos())
        print(train.wheel1)
        print(track._exits)
        #time.sleep(.1)
        train.wheel1, train.offset, remaining=track.compute_move(train.offset, train.speed, train.D)
        tt.setpos(train.wheel1.x, train.wheel1.z)
        #print("%s\t\t%s\t\t%s" % (train.wheel1, train.offset, remaining))

        if remaining > 0:
         #   print("Switching")
            print(remaining)
            if c:
                tt.color(c1, c1)
            else:
                tt.color(c2,c2)
            c=not c
            i=track.get_exit_index(train.wheel1)
            z=track.get_exit(i)
            if not z:
                break
            track, I = z[0], z[1]
            train.D=not I > 0
            train.offset=0

            train.wheel1, train.offset, remaining=track.compute_move(train.offset, remaining, train.D)
            tt.setpos(train.wheel1.x, train.wheel1.z)
                
        


if __name__=="__main__":
    s=CurveTrack(rotation=180, radius=200, angle=360/16)
    
    prev=s
    for i in range(15):
        sss=CurveTrack(rotation=180, radius=200, angle=360/16)
        trackmanager.force_assemble(prev, 1, sss, 0)
        prev=sss
        
    t=Train(wheel1=s.location)
    t.speed=1
    test_loop(t, s)


