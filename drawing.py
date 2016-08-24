##import Tkinter as tk
##
##
##root=tk.Tk()
##root.geometry("1024x768+0+0")
###root.config(bg="white")
##tk.Image()
##
##
##root.mainloop()


import turtle
screen=turtle.Screen()

tt=turtle.Turtle()

import trackmanager
from objects.rails import StraightTrack, CurveTrack
from objects.trains import Train
from util import Point

def test_loop(train, trackm, track):
    import time
    c=True
    c1="blue"
    c2="red"
    tt.color(c2,c2)
    while True:
        #time.sleep(.1)
        train.wheel1, train.offset, remaining=track.compute_move(train.offset, train.speed, train.D)
        tt.setpos(train.wheel1.x, train.wheel1.z)
        print("%s\t\t%s\t\t%s" % (train.wheel1, train.offset, remaining))
        if remaining > 0:
            print("Switching")
            if c:
                tt.color(c1, c1)
            else:
                tt.color(c2,c2)
            c=not c
            i=trackm.get_exit_index(train.wheel1)
            z=trackm.get_exit(i)
            if not z:
                break
            trackm2, D = z[0], z[1]
            train.D=not D > 0
            train.offset=0
            trackm=trackm2
            track=trackm.track

            train.wheel1, train.offset, remaining=track.compute_move(train.offset, remaining, train.D)
            tt.setpos(train.wheel1.x, train.wheel1.z)
                
        


if __name__=="__main__":
    from objects.rails import StraightTrack, CurveTrack
    s=trackmanager.TrackWrapper(CurveTrack(location=Point(-100,0,-100), rotation=180, radius=500, angle=360/16))
    #s=trackmanager.TrackWrapper(StraightTrack(location=Point(50,0,0), length=100))
    #ss=trackmanager.TrackWrapper(StraightTrack(length=100))
    #trackmanager.force_assemble(s, 1, ss, 0)

    prev=s
    for i in range(16):
        sss=trackmanager.TrackWrapper(CurveTrack(rotation=180, radius=500, angle=360/16))
        trackmanager.force_assemble(prev, 1, sss, 0)
        prev=sss
    sss=trackmanager.TrackWrapper(StraightTrack(length=100))
    trackmanager.force_assemble(prev, 1, sss, 0)
    prev=sss
    for i in range(7):
        sss=trackmanager.TrackWrapper(StraightTrack(length=100))
        trackmanager.force_assemble(prev, 1, sss, 0)
        prev=sss    
    #ss=trackmanager.TrackWrapper(CurveTrack(rotation=0, radius=500, angle=360/16))
    t=Train(wheel1=s.track.location)
    t.speed=20
    test_loop(t, s, s.track)
