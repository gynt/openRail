
import operator
from util import Point

class TrackWrapper(object):

    def __init__(self, track):
        self.track=track
        self._exitsmap=[]*track._exits

    def get_exit_index(self, point):
        for i in range(len(self._exitsmap)):
            p=point.apply(point=getattr(self.track,"_l%s"%i), func=operator.eq)
            if p.x==True and p.y==True and p.z==True:
                return i

    def get_exit(self, index):
        return self._exitsmap[index], not index > 0

    def set_exit_point(self, index, track):
        self._exitsmap[index]=track

def assembly_allowed(self, track_wrapper1, exit1, track_wrapper2, exit2):
    return assembly_allowed_rot(self, track_wrapper1, exit1, track_wrapper2, exit2) and assembly_allowed_pos(self, track_wrapper1, exit1, track_wrapper2, exit2)

def assembly_allowed_pos(self, track_wrapper1, exit1, track_wrapper2, exit2):
    return True

def assembly_allowed_rot(self, track_wrapper1, exit1, track_wrapper2, exit2):
    if track_wrapper2.track.type=="StraightTrack":
        return track_wrapper1.track.rotation-track_wrapper2.track.rotation%180==0:
    if track_wrapper2.track.type=="CurveTrack":
         return track_wrapper1.track.rotation-track_wrapper2.track.rotation%180==0 or track_wrapper1.track.rotation==track_wrapper2.track.rotation+track_wrapper2.track.angle
        
            
                
def assemble(track_wrapper1, exit1, track_wrapper2, exit2):
    if assem
    if track_wrapper2.track.type=="StraightTrack":
        if track_wrapper1.track.rotation-track_wrapper2.track.rotation%180==0:
            track_wrapper1.set_exit_point(exit1, track_wrapper2)
            track_wrapper2.set_exit_point(exit2, track_wrapper1)
    if track_wrapper2.track.type=="CurveTrack":
        pass
