

def find_file(basedir, path):
    path=path.replace("\\","/").replace("//","/")
    import os
    pathsegs=path.split(os.path.sep)
    for i in range(len(pathsegs)-1):
        if os.path.isdir(os.path.join(basedir, *pathsegs[i:-1])):
            return os.path.join(basedir, *pathsegs[i:])
    return None
