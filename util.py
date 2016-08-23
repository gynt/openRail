from __future__ import division

from collections import namedtuple

#Point=namedtuple('Point','x y z')

class Point(object):

    def __init__(self, x=0, y=0, z=0):
        self.x=x
        self.y=y
        self.z=z

    def __repr__(self):
        return "Point(%s=%f,%s=%f,%s=%f)" % ('x', self.x, 'y', self.y, 'z', self.z)

    def _apply(self, x=0, y=0, z=0, subtract=False):
        if not subtract:
            return Point(self.x+x,self.y+y,self.z+z)
        else:
            return Point(self.x-x,self.y-y,self.z-z)
        
    def subtract_point(self, point):
        return self.subtract(point.x, point.y, point.z)

    def subtract(self, x=0,y=0,z=0):
        return self._apply(x,y,z,True)

    def add_point(self, point):
        return self.add(point.x, point.y, point.z)

    def add(self, x=0, y=0, z=0):
        return self._apply(x,y,z)
