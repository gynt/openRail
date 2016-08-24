
import operator
from util import Point

class TrackWrapper(object):

    def __init__(self, track):
        self.track=track
        self._exitsmap=[None]*track._exits

    def get_exit_index(self, point):
        for i in range(len(self._exitsmap)):
            p=point.apply(point=getattr(self.track,"_l%s"%i), func=operator.eq)
            if p.x==True and p.y==True and p.z==True:
                return i

    def get_exit(self, index):
        return self._exitsmap[index]

    def set_exit_point(self, index, track, index2):
        self._exitsmap[index]=(track, index2)

def force_assemble(track_wrapper1, exit1, track_wrapper2, exit2):
    offset=0
    if track_wrapper1.track.__class__.__name__=="StraightTrack" and track_wrapper2.track.__class__.__name__=="StraightTrack":
        if exit2 > 0:
            offset=180
        track_wrapper2.track.set_rotation(track_wrapper1.track.rotation+offset)
        d=getattr(track_wrapper1.track,"_l%s"%exit1).apply(func=operator.sub, point=getattr(track_wrapper2.track,"_l%s"%exit2))
        track_wrapper2.track.set_location(d.apply(func=operator.add, point=track_wrapper2.track.location))
        track_wrapper1.set_exit_point(exit1, track_wrapper2, exit2)
        track_wrapper2.set_exit_point(exit2, track_wrapper1, exit1)
    elif track_wrapper1.track.__class__.__name__=="CurveTrack" and track_wrapper2.track.__class__.__name__=="StraightTrack":
        if exit1 > 0:
            offset=track_wrapper1.track.angle+90
        track_wrapper2.track.set_rotation(track_wrapper1.track.rotation+offset)
        d=getattr(track_wrapper1.track,"_l%s"%exit1).apply(func=operator.sub, point=getattr(track_wrapper2.track,"_l%s"%exit2))
        track_wrapper2.track.set_location(d.apply(func=operator.add, point=track_wrapper2.track.location))
        track_wrapper1.set_exit_point(exit1, track_wrapper2, exit2)
        track_wrapper2.set_exit_point(exit2, track_wrapper1, exit1)
    elif track_wrapper1.track.__class__.__name__=="StraightTrack" and track_wrapper2.track.__class__.__name__=="CurveTrack":
        if exit2 > 0:
            offset=180-track_wrapper2.track.angle-90
        else:
            offset=-90
        track_wrapper2.track.set_rotation(track_wrapper1.track.rotation+offset)
        d=getattr(track_wrapper1.track,"_l%s"%exit1).apply(func=operator.sub, point=getattr(track_wrapper2.track,"_l%s"%exit2))
        track_wrapper2.track.set_location(d.apply(func=operator.add, point=track_wrapper2.track.location))
        track_wrapper1.set_exit_point(exit1, track_wrapper2, exit2)
        track_wrapper2.set_exit_point(exit2, track_wrapper1, exit1)        
    elif track_wrapper1.track.__class__.__name__=="CurveTrack" and track_wrapper2.track.__class__.__name__=="CurveTrack":
        if exit1 > 0:
            offset=track_wrapper1.track.angle
        if exit2 > 0:
            offset=offset-track_wrapper2.track.angle+180
        track_wrapper2.track.set_rotation(track_wrapper1.track.rotation+offset)
        d=getattr(track_wrapper1.track,"_l%s"%exit1).apply(func=operator.sub, point=getattr(track_wrapper2.track,"_l%s"%exit2))
        track_wrapper2.track.set_location(d.apply(func=operator.add, point=track_wrapper2.track.location))
        track_wrapper1.set_exit_point(exit1, track_wrapper2, exit2)
        track_wrapper2.set_exit_point(exit2, track_wrapper1, exit1)        
        
        

def assembly_allowed(track_wrapper1, exit1, track_wrapper2, exit2):
    return assembly_allowed_rot(self, track_wrapper1, exit1, track_wrapper2, exit2) and assembly_allowed_pos(self, track_wrapper1, exit1, track_wrapper2, exit2)

def assembly_allowed_pos(track_wrapper1, exit1, track_wrapper2, exit2):
    comp=getattr(track_wrapper1.track, '_l%s'%exit1).apply(point=getattr(track_wrapper2.track, '_l%s'%exit2), func=operator.eq)
    return comp.x and comp.y and comp.z

def assembly_allowed_rot(track_wrapper1, exit1, track_wrapper2, exit2):
    if track_wrapper2.track.type=="StraightTrack":
        return track_wrapper1.track.rotation-track_wrapper2.track.rotation%180==0
    if track_wrapper2.track.type=="CurveTrack":
         return track_wrapper1.track.rotation-track_wrapper2.track.rotation%180==0 or track_wrapper1.track.rotation==track_wrapper2.track.rotation+track_wrapper2.track.angle
                
def assemble(track_wrapper1, exit1, track_wrapper2, exit2):
    if assembly_allowed(track_wrapper1, exit1, track_wrapper2, exit2):
        track_wrapper1.set_exit_point(exit1, track_wrapper2, exit2)
        track_wrapper2.set_exit_point(exit2, track_wrapper1, exit1)
