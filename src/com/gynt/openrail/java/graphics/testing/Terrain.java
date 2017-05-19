/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gynt.openrail.java.graphics.testing;

import com.gynt.openrail.java.rtr.io.Structs;
import com.gynt.openrail.java.rtr.io.StructsIO;
import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.FastMath;
import com.jme3.math.Matrix3f;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Quad;
import com.jme3.terrain.geomipmap.TerrainLodControl;
import com.jme3.terrain.geomipmap.TerrainQuad;
import com.jme3.terrain.heightmap.AbstractHeightMap;
import com.jme3.terrain.heightmap.ImageBasedHeightMap;
import com.jme3.texture.Texture;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author frank
 */
public class Terrain extends SimpleApplication {
    
    Material mat_terrain;
    TerrainQuad terrain;
    
    public Terrain() {
        super();
        int width = 1;
        int height = 1;
        int rwidth=width*20;
        int rheight=height*20;
    }

  public static void main(String[] args) {
    Terrain app = new Terrain();
    app.start();
  }
    

  @Override
  public void simpleInitApp() {
      flyCam.setMoveSpeed(50);

      
    
    /** 1. Create terrain material and load four textures into it. */
    mat_terrain = new Material(assetManager, 
            "Common/MatDefs/Terrain/Terrain.j3md");

    /** 1.1) Add ALPHA map (for red-blue-green coded splat textures) */
    mat_terrain.setTexture("Alpha", assetManager.loadTexture(
            "Textures/Terrain/splat/alphamap.png"));

    /** 1.2) Add GRASS texture into the red layer (Tex1). */
    Texture grass = assetManager.loadTexture(
            "Textures/grass1.jpg");
    grass.setWrap(Texture.WrapMode.Repeat);
    mat_terrain.setTexture("Tex1", grass);
    mat_terrain.setFloat("Tex1Scale", 20f);

    /** 1.3) Add DIRT texture into the green layer (Tex2) */
    Texture dirt = assetManager.loadTexture(
            "Textures/Terrain/splat/dirt.jpg");
    dirt.setWrap(Texture.WrapMode.Repeat);
    mat_terrain.setTexture("Tex2", dirt);
    mat_terrain.setFloat("Tex2Scale", 32f);

    /** 1.4) Add ROAD texture into the blue layer (Tex3) */
    Texture rock = assetManager.loadTexture(
            "Textures/Terrain/splat/road.jpg");
    rock.setWrap(Texture.WrapMode.Repeat);
    mat_terrain.setTexture("Tex3", rock);
    mat_terrain.setFloat("Tex3Scale", 128f);
    
    mat_terrain.getAdditionalRenderState().setWireframe(true);

    Quad q = new Quad(16, 16);
    
    Geometry g = new Geometry("q1",q);
    g.rotate(FastMath.DEG_TO_RAD*-90,0,0);
    g.setMaterial(mat_terrain);
    
    Node pivotNode = new Node("pivot1");
    //g.setLocalRotation(new Quaternion(new float[]{-90,0,0}));
    rootNode.attachChild(pivotNode);
    
    pivotNode.attachChild(g);
    //g.rotate(FastMath.DEG_TO_RAD*-90, 0.0f, 0f);
//    
//    int heightmapSize = 32;
//    int patchSize = 16;
//    terrain = new TerrainQuad("my terrain", patchSize+1, heightmapSize+1, null);
//
//    /** 4. We give the terrain its material, position & scale it, and attach it. */
//    terrain.setMaterial(mat_terrain);
    cam.setLocation(new Vector3f(0, 10, 0));
    //terrain.setLocalTranslation(0, 0, 0);
    //terrain.setLocalScale(2f, 1f, 2f);
//    rootNode.attachChild(terrain);
//    
//    terrain.setHeight(new Vector2f(8f,8f), 5f);
//
//    /** 5. The LOD (level of detail) depends on were the camera is: */
//    TerrainLodControl control = new TerrainLodControl(terrain, getCamera());
//    terrain.addControl(control);
  }
    
}
