from __future__ import division

from collections import namedtuple

import operator, serialization

#Point=namedtuple('Point','x y z')

class Point(serialization.Serializable):

    def __init__(self, x=0, y=0, z=0):
        self.x=x
        self.y=y
        self.z=z

    def __repr__(self):
        return "Point(%s=%f,%s=%f,%s=%f)" % ('x', self.x, 'y', self.y, 'z', self.z)

    def apply(self, x=0, y=0, z=0, func=operator.add, point=None):
        if point:
            x,y,z=point.x,point.y,point.z
        return Point(func(self.x, x),func(self.y, y),func(self.z, z))
        
