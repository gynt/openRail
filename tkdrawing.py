from __future__ import division

import Tkinter as tk

root=tk.Tk()
root.geometry("800x600+0+0")


canvas=tk.Canvas(root, bg='green')
canvas.place(x=0, y=0, width=800, height=600)
#arc=canvas.create_arc(100,100,10,10, style=tk.ARC, tag="track")

import trackmanager
import objects.tracks as O
import objects.trains as T

def draw_tracks(tracks):
    for t in tracks:
        draw_track(t)

def draw_track(track):
    if track.__class__==O.CurveTrack:
        arc=canvas.create_line(track._exits[0].x,track._exits[0].z,track._exits[1].x,track._exits[1].z, tag="track")
    elif track.__clas__==O.StraightTrack:
        arc=canvas.create_line(track._exits[0].x,track._exits[0].z,track._exits[1].x,track._exits[1].z, tag="track")



from util import Point

tracks=[]
s=O.CurveTrack(location=Point(x=600, y=0, z=300), yaw=0, radius=200, angle=360/16)
canvas.create_oval(s._exits[0].x-400, s._exits[0].z-200, s._exits[0].x, s._exits[0].z+200)
tracks.append(s)
prev=s
for i in range(15):
    sss=O.CurveTrack(yaw=180, radius=200, angle=360/16)
    trackmanager.force_assemble(prev, 1, sss, 0)
    prev=sss
    tracks.append(sss)

trackmanager.force_assemble(prev, 1, s, 0)
    
train=T.Train(track1=s, track2=s, track1offset=s.length, track2offset=0)
train.speed=1
train.D=False
train.D2=False


global line
line=None
def redraw_train(train):
    global line

    canvas.create_line(train.location.x, train.location.z, train.location.x, train.location.z, fill="blue")
    
    canvas.delete("train")
    line=canvas.create_line(train.wheel1.x, train.wheel1.z, train.wheel2.x, train.wheel2.z, fill="red", tag="train")



def run():
    import time
    redraw_train(train)

    def loop():
        train.do_move()
        redraw_train(train)
        root.after(10, loop)      
    loop()
        


def draw():
    draw_tracks(tracks)

#draw()

root.after(1000, run)

root.mainloop()
