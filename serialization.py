import json

def to_JSON(obj):
    return json.dumps(obj, default=lambda o: o.__dict__, 
        sort_keys=True, indent=4)


class Serializable(object):

    def to_dict(self):
        a={k : self.__dict__[k] for k in self.__dict__.keys() if not k[0]=="_"}
        a['type']=self.__class__.__name__
        return a
    
    def to_JSON(self):
        return json.dumps(self, default=lambda o: o.__dict__, 
            sort_keys=True, indent=4)
