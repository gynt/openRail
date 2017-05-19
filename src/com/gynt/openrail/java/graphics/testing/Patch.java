/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gynt.openrail.java.graphics.testing;

import com.jme3.scene.VertexBuffer;
import com.jme3.scene.shape.Quad;
import java.nio.FloatBuffer;

/**
 *
 * @author frank
 */
public class Patch extends Quad {

    static void getHeight(Quad q) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void adjustHeight(int index, float adjust) {
        FloatBuffer old = getFloatBuffer(VertexBuffer.Type.Position);
        setHeight(index, old.array()[(index * 3) + 1] + adjust);
    }

    public void setHeight(int index, float newheight) {
        FloatBuffer old = getFloatBuffer(VertexBuffer.Type.Position);
        old.array()[(index * 3) + 1] = newheight;
        setBuffer(VertexBuffer.Type.Position, 3, old);
        updateBound();
    }

    public static void adjustHeight(Quad q, int index, float adjust) {
        FloatBuffer old = q.getFloatBuffer(VertexBuffer.Type.Position);
        setHeight(q, index, old.array()[(index * 3) + 1] + adjust);
    }

    public static void setHeight(Quad q, int index, float newheight) {
        FloatBuffer old = q.getFloatBuffer(VertexBuffer.Type.Position);
        old.array()[(index * 3) + 1] = newheight;
        q.setBuffer(VertexBuffer.Type.Position, 3, old);
        q.updateBound();
    }

}
