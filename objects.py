from __future__ import division

import serialization

from util import Point


import math as  T

class Track(serialization.Serializable):

    def __init__(self, rotation=0, location=Point(0,0,0)):
        self.type="t"
        self.rotation=rotation
        self.location=location


class StraightTrack(Track):

    def __init__(self, rotation=0, location=Point(0,0,0), length=0):
        Track.__init__(self, rotation)
        self.length=length
        self.set_location(location)

    def set_location(self, point):
        self.location=location
        self.update()

    def get_location(self):
        return self.location

    def update(self):
        self._compute_locs()

    def _compute_locs(self):
        halfway=self.length/2
        x=T.cos(T.radians(self.rotation))*halfway
        z=T.sin(T.radians(self.rotation))*halfway
        self._l1=self.location.subtract(x, 0, z)
        self._l2=self.location.add(x, 0, z)

    def compute_move(self, from_point, distance, D=True):
        if D:
            distance_point=self._l2.subtract(from_point)
            distance_left=T.cos(self.rotation)*distance
            
            

