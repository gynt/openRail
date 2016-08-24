from __future__ import division

if __name__=="__main__":
    import sys
    sys.path.insert(0, "..\\")

import serialization

from util import Point


import math as  T
import operator

class Track(serialization.Serializable):

    def __init__(self, rotation=0, location=Point(0,0,0)):
        self.type="t"
        self.rotation=rotation
        self.location=location
        self.set_location(location)
        self.set_rotation(rotation)
        self._exits=0

    def set_rotation(self, rotation):
        self.rotation=rotation
        self.update()

    def set_location(self, location):
        self.location=location
        self.update()

    def update(self):
        raise NotImplementedError("Not implemented.")

class StraightTrack(Track):

    def __init__(self, rotation=0, location=Point(0,0,0), length=0):
        Track.__init__(self, rotation)
        self.length=length
        self.update()
        self._exits=2
        
    def get_location(self):
        return self.location

    def update(self):
        if hasattr(self, "length"):
            self._compute_locs()

    def _compute_locs(self):
        halfway=self.length/2
        x=T.cos(T.radians(self.rotation))*halfway
        z=T.sin(T.radians(self.rotation))*halfway
        self._l0=self.location.apply(x, 0, z, func=operator.sub)
        self._l1=self.location.apply(x, 0, z, func=operator.add)

    def compute_move(self, offset, distance, D=True):
        reach=distance+offset
        a=self._l1 if D else self._l0
        df=self.length-reach
        if df > 0:
            off=0 if D else 180
            x=T.cos(T.radians(self.rotation+off))*df
            z=T.sin(T.radians(self.rotation+off))*df
            return a.apply(func=operator.sub, x=x, y=0, z=z), reach, 0
        else:
            return a, self.length, df*-1

    def point_to_offset(self, point, D=True):
        a=self._l0 if D else self._l1
        dv=point.apply(point=a,func=operator.sub)
        return abs(dv.x/T.cos(T.radians(self.rotation)))
            

        
class CurveTrack(Track):

    def __init__(self, rotation=0, location=Point(0,0,0), radius=0, angle=360/16):
        Track.__init__(self, rotation)
        self.radius=radius
        self.angle=angle
        self.length=self._get_length()
        self.update()
        self._exits=2

    def _get_length(self):
        return self._calc_distance_on_curve(self.angle)

    def set_location(self,point):
        self.location=point
        self.update()

    def set_rotation(self, rotation):
        self.rotation=rotation
        self.update()

    def update(self):
        if hasattr(self, "length") and hasattr(self, "angle"):
            self.center_point=self._get_center_point()
            self._compute_locs()

    def _compute_locs(self):
        self._l0=self.center_point.apply(T.cos(T.radians(self.rotation))*self.radius, 0, T.sin(T.radians(self.rotation))*self.radius, func=operator.add)
        self._l1=self.center_point.apply(T.cos(T.radians(self.rotation+self.angle))*self.radius, 0, T.sin(T.radians(self.rotation+self.angle))*self.radius, func=operator.add)

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
        a=self._l1 if D else self._l0
        df=self.length-reach
        if df > 0:
            dc=self._calc_degrees_change(reach)
            dco=self.rotation+(dc) if D else self.rotation+(self.angle-dc)
            return self.center_point.apply(T.cos(T.radians(dco))*self.radius, 0, T.sin(T.radians(dco))*self.radius, func=operator.add), reach, 0
        else:
            return a, self.length, df*-1

    def point_to_offset(self, point, D=True):
        a=self._l0 if D else self._l1
        dv=point.apply(point=self.center_point,func=operator.sub)
        an=T.degrees(T.acos(dv.x/self.radius))-self.rotation if D else (self.rotation+self.angle)-T.degrees(T.acos(dv.x/self.radius))
        return self._calc_distance_on_curve(an)
         


if __name__=="__main__":
    #Test battery

    s=StraightTrack(length=10)
    res2=s.compute_move(0,3)

    s1=StraightTrack(90, length=10)
    res_12=s1.compute_move(0,3)

    s2=StraightTrack(292.5, length=10)
    res_22=s2.compute_move(0,3)

    res_2=s.compute_move(0,3,False)
