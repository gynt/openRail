import json

def to_JSON(obj):
    return json.dumps(obj, default=lambda o: o.__dict__, 
        sort_keys=True, indent=4)


def to_dict_list(tracks):
    dictlist=[]
    for i in range(len(tracks)):
        t=tracks[i].to_dict()
        t['exitsmap']=[(tracks.index(e[0]) if e else None) for e in tracks[i]._exitsmap]
        dictlist.append(t)
    return dictlist
        
def from_dict_list(dictlist):
    tracks=[]
    for i in range(len(dictlist)):
        t=dictlist[i].from_dict()
    

def to_dict(self):
    a={}
    for k, v in self.__dict__.iteritems():
        if k[0]=="_":
           continue
        if hasattr(v, "to_dict"):
            a[k]=v.to_dict()
        else:
            a[k]=v
    a['type']=self.__module__+"."+self.__class__.__name__
    return a
    

def from_dict(dic):
    path, clss=dic['type'].rsplit(".",1)
    import importlib
    i=importlib.import_module(path)
    c=getattr(i, clss)

    o=c()
    

class Serializable(object):

    def from_dict(self):
        pass

    def to_dict(self):
        a={}
        for k, v in self.__dict__.iteritems():
            if k[0]=="_":
               continue
            if hasattr(v, "to_dict"):
                a[k]=v.to_dict()
            else:
                a[k]=v
        a['type']=self.__module__+"."+self.__class__.__name__
        return a

    
    def to_JSON(self):
        return json.dumps(self, default=lambda o: o.__dict__, 
            sort_keys=True, indent=4)



if __name__=="__main__":
    from objects.tracks import StraightTrack, CurveTrack
    s=StraightTrack(10)
    c=CurveTrack(radius=5)
    s2=StraightTrack(20)
    c2=CurveTrack(radius=10)
    s.set_exit_point(1, c, 0)
    c.set_exit_point(1, s2, 0)
    s2.set_exit_point(1, c2, 0)
    c2.set_exit_point(1, s, 0)
    tracks=[s,c,s2,c2]    
