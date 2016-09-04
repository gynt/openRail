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
    print(track._exits)
    if track.__class__==O.CurveTrack:
        arc=canvas.create_line(track._exits[0].x,track._exits[0].z,track._exits[1].x,track._exits[1].z, tag="track")
    elif track.__clas__==O.StraightTrack:
        arc=canvas.create_line(track._exits[0].x,track._exits[0].z,track._exits[1].x,track._exits[1].z, tag="track")



from util import Point

tracks=[]
s=O.CurveTrack(location=Point(x=400, y=0, z=300), rotation=180, radius=200, angle=360/16)
tracks.append(s)
prev=s
for i in range(15):
    sss=O.CurveTrack(rotation=180, radius=200, angle=360/16)
    trackmanager.force_assemble(prev, 1, sss, 0)
    prev=sss
    tracks.append(sss)
    
train=T.Train(wheel1=s.location)
train.speed=1



def redraw_train(train):

    
    
    canvas.delete("train")
    canvas.create_line(train.wheel1.x, train.wheel1.z, train.wheel2.x, train.wheel2.z, fill="red")



def run():
    import time
    train.track1=tracks[1]
    train.track2=tracks[0]
    train.offset=3
    train.offset2=0
    redraw_train(train)
    while True:
        train.do_move()
        redraw_train(train)
        time.sleep(1)
        


def draw():
    draw_tracks(tracks)

draw()

root.after(1000, run)

root.mainloop()
