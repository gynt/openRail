package com.gynt.openrail.java.graphics.testing;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.ParseException;
import java.util.Collection;

import com.gynt.openrail.java.rtr.io.Structs.SaveFile;
import com.gynt.openrail.java.rtr.io.StructsIO;
import com.jme3.app.SimpleApplication;
import com.jme3.asset.AssetKey;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.VertexBuffer;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture.WrapMode;
import com.jme3.util.BufferUtils;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class TableTop2 extends Geometry {

	public static void main(String[] args) {
		new SimpleApplication() {

			@Override
			public void simpleInitApp() {
                               
                            
				Material mat_terrain = new Material(assetManager, "Common/MatDefs/Terrain/Terrain.j3md");
//				Material mat_terrain = new Material(assetManager,
//					    "Common/MatDefs/Misc/Unshaded.j3md");
//				mat_terrain.setColor("Color", ColorRGBA.Green);
				
				//mat_terrain.getAdditionalRenderState().setWireframe(true);

				/**
				 * 1.1) Add ALPHA map (for red-blue-green coded splat textures)
				 */
				mat_terrain.setTexture("Alpha", assetManager.loadTexture("Textures/Terrain/splat/alphamap.png"));

				/** 1.2) Add GRASS texture into the red layer (Tex1). */
				Texture grass = assetManager.loadTexture("Textures/Terrain/splat/grass.jpg");
				grass.setWrap(WrapMode.Repeat);
				mat_terrain.setTexture("Tex1", grass);
				mat_terrain.setFloat("Tex1Scale", 64f);

				/** 1.3) Add DIRT texture into the green layer (Tex2) */
				Texture dirt = assetManager.loadTexture("Textures/Terrain/splat/dirt.jpg");
				dirt.setWrap(WrapMode.Repeat);
				mat_terrain.setTexture("Tex2", dirt);
				mat_terrain.setFloat("Tex2Scale", 32f);

				/** 1.4) Add ROAD texture into the blue layer (Tex3) */
				Texture rock = assetManager.loadTexture("Textures/Terrain/splat/road.jpg");
				rock.setWrap(WrapMode.Repeat);
				mat_terrain.setTexture("Tex3", rock);
				mat_terrain.setFloat("Tex3Scale", 128f);

				//TableTop2 t = new TableTop2(4, 4);
				TableTop2 t;
				try {
					SaveFile sf = StructsIO.deserialize(Files.readAllBytes(new File("terrainhill.roa").toPath()));
					t = TableTop2.fromSaveFile(sf);
					t.setMaterial(mat_terrain);
					rootNode.attachChild(t);
					
					cam.setAxes(Vector3f.UNIT_Z, new Vector3f(0, -1, 0), Vector3f.UNIT_X);
					flyCam.setMoveSpeed(20F);
					cam.setLocation(new Vector3f(sf.view.x, sf.view.y, sf.view.z));
					cam.lookAt(new Vector3f(4,0,2), new Vector3f(0, -1, 0));
					
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	
			}

		}.start();
	}
	
	public static final TableTop2 fromSaveFile(SaveFile sf) {
		int width=(int) Math.sqrt(sf.terrain.width_height_multiplied);
		TableTop2 result = new TableTop2(width,width);
		
		int i = 0;
		for(int z= 0; z<width;z++) {
			for(int x= 0; x<width;x++) {
				result.setHeight(x, z, sf.terrain.terrain[i].y);
				i++;
			}
		}
		
		
		return result;
	}

	private TableTopMesh myMesh;

	public TableTop2(int width, int depth) {
		myMesh = new TableTopMesh(width, depth);
		myMesh.generate();
		setMesh(myMesh);
		updateModelBound();
	}

	public int getWidth() {
		return myMesh.width;
	}

	public int getHeight() {
		return myMesh.depth;
	}

	public void setHeights(Collection<HeightMapping> heights) {
		myMesh.setHeights(heights);
		updateModelBound();
	}

	public void setHeight(int x, int z, float height) {
		myMesh.setHeight(x, z, height);
		updateModelBound();
	}

	public static class HeightMapping {
		public int x;
		public int z;
		public float height;

		public HeightMapping(int x, int z, float height) {
			this.x = x;
			this.z = z;
			this.height = height;
		}
	}

	public static class TableTopMesh extends Mesh {

		private int width;
		private int depth;
		private int rwidth;
		private int rdepth;
		private Vector3f[] vertices;
		private Vector2f[] texCoords;
		private int[] indexes;

		public TableTopMesh(int width, int depth) {
			this.width = width;
			this.depth = depth;
			this.rwidth = width + 1;
			this.rdepth = depth + 1;
		}

		public void generate() {

			// TODO: Test
			vertices = new Vector3f[rwidth * rdepth];
			for (int z = 0; z < rdepth; z++) {
				for (int x = 0; x < rwidth; x++) {
					vertices[(z * rwidth) + x] = new Vector3f(x, 0, z);
				}
			}
			setBuffer(VertexBuffer.Type.Position, 3, BufferUtils.createFloatBuffer(vertices));

			// TODO: Test
			texCoords = new Vector2f[rwidth * rdepth];
			float texwidth = 1.0F / width;
			float texdepth = 1.0F / depth;
			for (int z = 0; z < rdepth; z++) {
				for (int x = 0; x < rwidth; x++) {
					texCoords[(z * rwidth) + x] = new Vector2f(texwidth * x, texdepth * z);
				}
			}
			
			setBuffer(VertexBuffer.Type.TexCoord, 2, BufferUtils.createFloatBuffer(texCoords));

			// TODO: Test
			indexes = new int[width*depth * 3 * 2]; //Each quad is 2 triangles of 3 vertices.
			int quad = 0;
			while (quad<(depth*width)) {
				
				int quadz=quad/width;
				int quadx=quad+quadz;
				
				
				int i = quad*6;
				
				indexes[i]=quadx;
				indexes[i+1]=quadx+1;
				indexes[i+2]=quadx+rwidth;
				
				indexes[i+3]=quadx+1;
				indexes[i+4]=quadx+1+rwidth;
				indexes[i+5]=quadx+rwidth;
				
				quad++;
			}
			
			setBuffer(VertexBuffer.Type.Index, 3, indexes);
			
			updateBound();
		}

		public void setHeight(int x, int z, float height) {
			vertices[((z * rwidth) + x)].y = height;
			setBuffer(VertexBuffer.Type.Position, 3, BufferUtils.createFloatBuffer(vertices));
		}

		public void setHeights(Collection<HeightMapping> heights) {
			for (HeightMapping h : heights) {
				vertices[((h.z * rwidth) + h.x)].y = h.height;
			}
			setBuffer(VertexBuffer.Type.Position, 3, BufferUtils.createFloatBuffer(vertices));
		}

	}
}
