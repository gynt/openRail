
import sys
if 'win' in sys.platform:
    import setup_env
import pyglet
from pyglet.gl import *

class GameWindow(pyglet.window.Window):

    def __init__(self, *args, **kwargs):
        super(GameWindow, self).__init__(*args, **kwargs)
        self.set_size(1024, 768)
        self.set_caption("openRail")
        pyglet.clock.schedule_interval(self.update, 1.0/30.0)

    def update(self, dt):
        #do loop
        pass

    def on_resize(self, width, height):
        glViewport(0, 0, width, height)
        glMatrixMode(GL_PROJECTION)
        glLoadIdentity()
        gluPerspective(65, width / float(height), .1, 1000)
        glMatrixMode(GL_MODELVIEW)
        return pyglet.event.EVENT_HANDLED    

    def on_key_press(self, symbol, modifiers):
        if symbol==pyglet.window.key.ESCAPE:
            pyglet.clock.unschedule(self.update)
            gamewindow.close()
            pyglet.app.exit()
        elif symbol==pyglet.window.key.F11:
            self.set_fullscreen(fullscreen=not self.fullscreen)

    def draw_triangle(self):
        glClear(GL_COLOR_BUFFER_BIT)
        glLoadIdentity()
        glBegin(GL_TRIANGLES)
        glVertex3f(0, 0, 0)
        glVertex3f(self.width, 0, 0)
        glVertex3f(self.width, self.height, 0)
        glEnd()

    def draw_objects(self):
        pass

    def draw_terrain(self):
        pass

    def draw_gui(self):
        pass

    def on_draw(self):
        self.clear()
        self.draw_objects()
        self.draw_terrain()
        self.draw_gui()
        #self.draw_triangle()

if __name__=="__main__":
    
    gamewindow=GameWindow()

    pyglet.app.run()
