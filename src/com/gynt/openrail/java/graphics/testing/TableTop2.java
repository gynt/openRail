package com.gynt.openrail.java.graphics.testing;

import java.util.Collection;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.VertexBuffer;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture.WrapMode;
import com.jme3.util.BufferUtils;

public class TableTop2 extends Geometry {

	public static void main(String[] args) {
		new SimpleApplication() {

			@Override
			public void simpleInitApp() {
				Material mat_terrain = new Material(assetManager, "Common/MatDefs/Terrain/Terrain.j3md");

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

				TableTop2 t = new TableTop2(20, 20);
				t.setMaterial(mat_terrain);
				rootNode.attachChild(t);
			}

		}.start();
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

		public TableTopMesh(int width, int depth) {
			this.width = width;
			this.depth = depth;
			this.rwidth = width + 1;
			this.rdepth = depth + 1;
		}

		public void generate() {

			// TODO: Test
			Vector3f[] vertices = new Vector3f[rwidth * rdepth];
			for (int z = 0; z < rwidth; z++) {
				for (int x = 0; x < rdepth; x++) {
					vertices[(z * rwidth) + x] = new Vector3f(x, 0, z);
				}
			}
			setBuffer(VertexBuffer.Type.Position, 3, BufferUtils.createFloatBuffer(vertices));

			// TODO: Test
			Vector2f[] texCoords = new Vector2f[rwidth * rdepth];
			float texwidth = 1 / width;
			float texdepth = 1 / depth;
			for (int z = 0; z < rwidth; z++) {
				for (int x = 0; x < rdepth; x++) {
					texCoords[(z * rwidth) + x] = new Vector2f(texwidth * x, texdepth * z);
				}
			}

			// TODO: Test
			int[] indexes = new int[vertices.length * 6];
			for (int z = 0; z < rwidth; z++) {
				for (int x = 0; x < rdepth; x++) {
					int b = (z * rwidth) + x;
					int base = b * 6;
					indexes[base + 0] = b;
					indexes[base + 1] = b + 1;
					indexes[base + 2] = b + rwidth;
					indexes[base + 3] = b + 1;
					indexes[base + 4] = b + 1 + rwidth;
					indexes[base + 5] = b + rwidth;
				}
			}
			setBuffer(VertexBuffer.Type.Index, 3, indexes);

		}

		public void setHeight(int x, int z, float height) {
			getFloatBuffer(VertexBuffer.Type.Position).array()[((z * rwidth) + x) * 3 + 1] = height;
		}

		public void setHeights(Collection<HeightMapping> heights) {
			float[] array = getFloatBuffer(VertexBuffer.Type.Position).array();
			for (HeightMapping h : heights) {
				array[((h.z * rwidth) + h.x) * 3 + 1] = h.height;
			}
		}

	}
}