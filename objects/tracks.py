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
        self._exits=[]
        self._exitsmap=[]
        self.set_location(location)
        self.set_rotation(rotation)
        self.track=self

    def get_exit_index(self, point):
        for i in range(len(self._exitsmap)):
            p=point.apply(point=self._exits[i], func=operator.eq)
            if p.x==True and p.y==True and p.z==True:
                return i

    def get_exit(self, index):
        return self._exitsmap[index]

    def set_exit_point(self, index, track, index2):
        self._exitsmap[index]=(track, index2)

    def _init_exits(self, size):
        self._exits=[None]*size
        self._exitsmap=[None]*size

    def get_rotation(self, exit):
        raise NotImplementedError("Not implemented.")

    def set_rotation(self, rotation, exit=None):
        self.rotation=rotation
        self.update()

    def set_location(self, location):
        self.location=location
        self.update()

    def update(self):
        raise NotImplementedError("Not implemented.")

class StraightTrack(Track):

    def __init__(self, rotation=0, location=Point(0,0,0), length=0):
        Track.__init__(self, rotation, location)
        self.length=length
        self._r=90 #HMmmm
        self._init_exits(2)
        self.update()

    def get_location(self):
        return self.location

    def get_rotation(self, exit):
        return self.rotation
        #return self.rotation if exit==0 else (self.rotation+180)%360

    def update(self):
        if hasattr(self, "length"):
            self._compute_locs()

    def _compute_locs(self):
        halfway=self.length/2
        x=T.cos(T.radians(self.rotation))*halfway
        z=T.sin(T.radians(self.rotation))*halfway
        self._exits[0]=self.location.apply(x, 0, z, func=operator.sub)
        self._exits[1]=self.location.apply(x, 0, z, func=operator.add)

    def compute_move(self, offset, distance, D=True):
        reach=distance+offset
        a=self._exits[1] if D else self._exits[0]
        df=self.length-reach
        if df > 0:
            off=0 if D else 180
            x=T.cos(T.radians(self.rotation+off))*df
            z=T.sin(T.radians(self.rotation+off))*df
            return a.apply(func=operator.sub, x=x, y=0, z=z), reach, 0
        else:
            return a, self.length, df*-1

    def point_to_offset(self, point, D=True):
        a=self._exits[0] if D else self._exits[1]
        dv=point.apply(point=a,func=operator.sub)
        return abs(dv.x/T.cos(T.radians(self.rotation)))
            

        
class CurveTrack(Track):

    def __init__(self, rotation=0, location=Point(0,0,0), radius=0, angle=360/16, length=None):
        Track.__init__(self, rotation, location)
        self.radius=radius
        
        if angle and not length:
            self.angle=angle
            self.length=self._get_length()
        if not angle or length:
            self.length=length
            self.angle=self._get_angle()
            
        self._init_exits(2)
        self.update()

    def _get_length(self):
        return self._calc_distance_on_curve(self.angle)

    def _get_angle(self):
        return self._calc_degrees_change(self.length)

    def get_rotation(self, exit):
        return self.rotation-(.5*self.angle) if exit==0 else self.rotation+(.5*self.angle)

    def set_rotation(self, rotation, exit=None):
        self.rotation=rotation
        if exit==None:
            self.update()
            return
        self.rotation=rotation+(self.angle/2) if exit==0 else rotation-(self.angle/2)
        self.update()
            

    def update(self):
        if hasattr(self, "length") and hasattr(self, "angle"):
            self.center_point=self._get_center_point()
            self._compute_locs()

    def _compute_locs(self):
        self._exits[0]=self.center_point.apply(T.cos(T.radians(self.rotation))*self.radius, 0, T.sin(T.radians(self.rotation))*self.radius, func=operator.add)
        self._exits[1]=self.center_point.apply(T.cos(T.radians(self.rotation+self.angle))*self.radius, 0, T.sin(T.radians(self.rotation+self.angle))*self.radius, func=operator.add)

    def _get_center_point(self):
        x=T.cos(T.radians(self.rotation+180-(self.angle/2.0)))*self.radius
        z=T.sin(T.radians(self.rotation+180-(self.angle/2.0)))*self.radius
        return self.location.apply(func=operator.add, x=x, y=0, z=z)

    def _calc_distance_on_curve(self, degrees):
        return 2*self.radius*T.pi*degrees/360.0
    
    def _calc_degrees_change(self, distance):
        return distance*360/(2*self.radius*T.pi)

    def compute_move(self, offset, distance, D=True):
        reach=offset+distance
        a=self._exits[1] if D else self._exits[0]
        df=self.length-reach
        if df > 0:
            dc=self._calc_degrees_change(reach)
            dco=self.rotation+(dc) if D else self.rotation+(self.angle-dc)
            return self.center_point.apply(T.cos(T.radians(dco))*self.radius, 0, T.sin(T.radians(dco))*self.radius, func=operator.add), reach, 0
        else:
            return a, self.length, df*-1

    def point_to_offset(self, point, D=True):
        a=self._exits[0] if D else self._exits[1]
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
