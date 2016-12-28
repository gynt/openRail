from variables import vars_dict as __V__

def configure_for(module):
    import sys
    if not module in __V__:
        raise Exception("Did not find key in variables dictionary for module: %s" % module)
    for path in __V__[module].values():
        sys.path.append(path)

def unconfigure_for(module):
    import sys
    if not module in __V__:
        raise Exception("Did not find key in variables dictionary for module: %s" % module)
    for path in __V__[module].values():
        if path in sys.path:
            for i in range(len(sys.path), 0, -1):
                if path==sys.path[i-1]:
                    del sys.path[i-1]