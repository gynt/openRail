from __future__ import division

import serialization

from util import Point


import math as  T
import operator

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
        self.location=point
        self.update()

    def get_location(self):
        return self.location

    def update(self):
        self._compute_locs()

    def _compute_locs(self):
        halfway=self.length/2
        x=T.cos(T.radians(self.rotation))*halfway
        z=T.sin(T.radians(self.rotation))*halfway
        self._l1=self.location.apply(x, 0, z, func=operator.sub)
        self._l2=self.location.apply(x, 0, z, func=operator.add)

    def compute_move(self, offset, distance, D=True):
        reach=distance+offset
        a=self._l2 if D else self._l1
        df=self.length-reach
        if df < 0:
            return a, df*-1
        else:
            off=0 if D else 180
            x=T.cos(T.radians(self.rotation+off))*df
            z=T.sin(T.radians(self.rotation+off))*df
            return a.apply(func=operator.sub, x=x, y=0, z=z), 0

    def point_to_offset(self, point, D=True):
        pass
            
    def compute_move_(self, from_point, distance, D=True):
        pass
        
class CurveTrack(Track):

    def __init__(self, rotation=0, location=Point(0,0,0), radius=0, angle=360/16.0):
        Track.__init__(self, rotation)
        self.radius=radius
        self.angle=angle
        self.length=self._get_length()
        self.set_location(location)

    def _get_length(self):
        return self._calc_distance_on_curve(self.angle)

    def set_location(self,point):
        self.location=point
        self.update()

    def update(self):
        self.center_point=self._get_center_point()
        self._compute_locs()

    def _compute_locs(self):
        self._l1=self.center_point.apply(T.cos(T.radians(self.rotation))*self.radius, 0, T.sin(T.radians(self.rotation))*self.radius, func=operator.add)
        self._l2=self.center_point.apply(T.cos(T.radians(self.rotation+self.angle))*self.radius, 0, T.sin(T.radians(self.rotation+self.angle))*self.radius, func=operator.add)

    def _get_center_point(self):
        x=T.cos(T.radians(self.rotation+180+self.angle/2.0))*self.radius
        z=T.sin(T.radians(self.rotation+180+self.angle/2.0))*self.radius
        return self.location.apply(func=operator.add, x=x, y=0, z=z)

    def _calc_distance_on_curve(self, degrees):
        return 2*self.radius*T.pi*degrees/360.0
    
    def _calc_degrees_change(self, distance):
        return distance*360/(2*self.radius*T.pi)

    def compute_move(self, offset, distance, D=True):
        reach=offset+distance
        a=self._l2 if D else self._l1
        df=self.length-reach
        if df < 0:
            return a, df
        else:
            dc=self._calc_degrees_change(reach)
            dco=self.rotation+(dc-(self.angle/2)) if D else self.rotation-(dc+(self.angle/2))
            return self.center_point.apply(T.cos(T.radians(dco))*self.radius, 0, T.sin(T.radians(dco))*self.radius, func=operator.add), 0
        
    

if __name__=="__main__":
    #Test battery

    s=StraightTrack(length=10)
    res2=s.compute_move(0,3)

    s1=StraightTrack(90, length=10)
    res_12=s1.compute_move(0,3)

    s2=StraightTrack(292.5, length=10)
    res_22=s2.compute_move(0,3)

    res_2=s.compute_move(0,3,False)
