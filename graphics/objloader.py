import pyglet
from OpenGL.GL import *
 
def MTL(filename):
    contents = {}
    mtl = None
    for line in open(filename, "r"):
        if line.startswith('#'): continue
        values = line.split()
        if not values: continue
        if values[0] == 'newmtl':
            mtl = contents[values[1]] = {}
        elif mtl is None:
            raise ValueError, "mtl file doesn't start with newmtl stmt"
        elif values[0] == 'map_Kd':
            # load the texture referred to by this declaration
            mtl[values[0]] = values[1]
            surf = pyglet.image.load(mtl['map_Kd'].replace('\\','/'))
            image = surf.data
            ix, iy = i.width, i.height
            texid = mtl['texture_Kd'] = glGenTextures(1)
            glBindTexture(GL_TEXTURE_2D, texid)
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER,
                GL_LINEAR)
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER,
                GL_LINEAR)
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, ix, iy, 0, GL_RGBA,
                GL_UNSIGNED_BYTE, image)
        else:
            mtl[values[0]] = map(float, values[1:])
    return contents

def Material(material):
    import resourcemanager, os
    #path=resourcemanager.find_file(os.getcwd(), material['file'])
    path='graphics/test.dds'
    surf = pyglet.image.load(path)
    image = surf.data
    ix, iy = surf.width, surf.height
    texid = glGenTextures(1)
    glBindTexture(GL_TEXTURE_2D, texid)
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER,
        GL_LINEAR)
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER,
        GL_LINEAR)
    glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, ix, iy, 0, GL_RGBA,
        GL_UNSIGNED_BYTE, image)
    return texid

class FullObject(object):

    def __init__(self, scene):
        self.scene=scene
        

class Mesh(object):

    def __init__(self, mesh):
        self.mesh=mesh

        self.texid=Material(self.mesh.material)

        self.hascolors=self.mesh.colors.size>0
        self.hastexcoords=self.mesh.texturecoords.size>0
        self.hasnormals=self.mesh.normals.size>0

        self.gl_list = glGenLists(1)
        glNewList(self.gl_list, GL_COMPILE)
        glEnable(GL_TEXTURE_2D)
        glFrontFace(GL_CCW)

        #glBindTexture(GL_TEXTURE_2D, self.texid)

        for face in self.mesh.faces:
            faceMode=None
            size=face.size
            if size==1:
                faceMode=GL_POINTS
            elif size==2:
                faceMode==GL_LINES
            elif size==3:
                faceMode==GL_TRIANGLES
            else:
                faceMode==GL_POLYGON

            glBindTexture(GL_TEXTURE_2D, self.texid)

            glBegin(GL_POLYGON)
            for i in range(size):
                indice=face[i]
                if(self.hascolors):
                    glColor4fv(self.mesh.colors[0][indice].tolist())
                if(self.hasnormals):
                    glNormal3fv(self.mesh.normals[indice].tolist())
                if(self.hastexcoords):
                    glTexCoord2fv(self.mesh.texturecoords[0][indice].tolist()[:2])
                glVertex3fv(self.mesh.vertices[indice].tolist())
            glEnd()

        glDisable(GL_TEXTURE_2D)
        glEndList()

    def draw(self):
        glCallList(obj.gl_list)
                    
                
 
class OBJ:
    def __init__(self, filename, swapyz=False):
        """Loads a Wavefront OBJ file. """
        self.vertices = []
        self.normals = []
        self.texcoords = []
        self.faces = []
 
        material = None
        for line in open(filename, "r"):
            if line.startswith('#'): continue
            values = line.split()
            if not values: continue
            if values[0] == 'v':
                v = map(float, values[1:4])
                if swapyz:
                    v = v[0], v[2], v[1]
                self.vertices.append(v)
            elif values[0] == 'vn':
                v = map(float, values[1:4])
                if swapyz:
                    v = v[0], v[2], v[1]
                self.normals.append(v)
            elif values[0] == 'vt':
                self.texcoords.append(map(float, values[1:3]))
            elif values[0] in ('usemtl', 'usemat'):
                material = values[1]
            elif values[0] == 'mtllib':
                self.mtl = MTL(values[1])
            elif values[0] == 'f':
                face = []
                texcoords = []
                norms = []
                for v in values[1:]:
                    w = v.split('/')
                    face.append(int(w[0]))
                    if len(w) >= 2 and len(w[1]) > 0:
                        texcoords.append(int(w[1]))
                    else:
                        texcoords.append(0)
                    if len(w) >= 3 and len(w[2]) > 0:
                        norms.append(int(w[2]))
                    else:
                        norms.append(0)
                self.faces.append((face, norms, texcoords, material))
 
        self.gl_list = glGenLists(1)
        glNewList(self.gl_list, GL_COMPILE)
        glEnable(GL_TEXTURE_2D)
        glFrontFace(GL_CCW)
        for face in self.faces:
            vertices, normals, texture_coords, material = face
 
            mtl = self.mtl[material]
            if 'texture_Kd' in mtl:
                # use diffuse texmap
                glBindTexture(GL_TEXTURE_2D, mtl['texture_Kd'])
            else:
                # just use diffuse colour
                glColor(*mtl['Kd'])
 
            glBegin(GL_POLYGON)
            for i in range(len(vertices)):
                if normals[i] > 0:
                    glNormal3fv(self.normals[normals[i] - 1])
                if texture_coords[i] > 0:
                    glTexCoord2fv(self.texcoords[texture_coords[i] - 1])
                glVertex3fv(self.vertices[vertices[i] - 1])
            glEnd()
        glDisable(GL_TEXTURE_2D)
        glEndList()
