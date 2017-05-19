/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gynt.openrail.java.graphics.testing;

import com.jme3.material.Material;
import com.jme3.math.FastMath;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.shape.Quad;
import com.jme3.terrain.ProgressMonitor;
import java.util.List;

/**
 *
 * @author frank
 */
public class Field implements com.jme3.terrain.Terrain {
    
    public Quad[][] quads;
    private final int width;
    private final int depth;
    
    public Field(int width, int depth) {
        quads = new Quad[width][depth];
        this.width=width;
        this.depth=depth;
    }
    
    public Quad[] getQuads(int x, int z) {
        return new Quad[]{(z==depth||x>0)?quads[x-1][z]:null, (x>0&&z>0)?quads[x-1][z-1]:null, (x==width||z>0)?quads[x][z-1]:null, (z==depth||x==width)?quads[x][z]:null};
    }

    @Override
    public float getHeight(Vector2f xz) {
        int xlow = (int) FastMath.floor(xz.x);
        int xhigh = (int) FastMath.ceil(xz.x);

        int zlow = (int) FastMath.floor(xz.y);
        int zhigh = (int) FastMath.ceil(xz.y);
        if(xlow==xhigh && zlow==zhigh) {
            for(Quad q : getQuads(xlow, zlow)) {
                if(q==null) continue;
                Patch.getHeight(q);
            }
        }
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Vector3f getNormal(Vector2f xz) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public float getHeightmapHeight(Vector2f xz) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setHeight(Vector2f xzCoordinate, float height) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setHeight(List<Vector2f> xz, List<Float> height) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void adjustHeight(Vector2f xzCoordinate, float delta) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void adjustHeight(List<Vector2f> xz, List<Float> height) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public float[] getHeightMap() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getMaxLod() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setLocked(boolean locked) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void generateEntropy(ProgressMonitor monitor) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Material getMaterial() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Material getMaterial(Vector3f worldLocation) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getTerrainSize() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getNumMajorSubdivisions() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
