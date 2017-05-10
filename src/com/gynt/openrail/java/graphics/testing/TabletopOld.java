package com.gynt.openrail.java.graphics.testing;

import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.VertexBuffer;
import com.jme3.scene.shape.Quad;

public class TabletopOld extends Node {
	
	public static int QUADSIZE = 16;
	@Deprecated
	/**Use single geometry for complete terrain?*/
	public Quad[][] quads;
	public Geometry[][] geometries;
	public float[] heights;
	private int width;
	private int depth;
	
	public TabletopOld(int width, int depth) {
		if(width%2!=0 || depth%2!=0) {
			throw new RuntimeException("Illegal dimensions tabletop, must be even: " + width + " " + depth);
		}
		
		this.width=width;
		this.depth=depth;
		quads=new Quad[width][depth];
		geometries=new Geometry[width][depth];
		heights = new float[(width+1)*(depth+1)];
		
		for(int wi = 0; wi<width; wi++) {
			for(int di = 0; di<depth; di++) {
				geometries[wi][di]=new Geometry("quad", new Quad(QUADSIZE, QUADSIZE));
				geometries[wi][di].setLocalTranslation((wi*QUADSIZE)-(width*QUADSIZE/2), 0, (di*QUADSIZE)-(depth*QUADSIZE/2));
				attachChildAt(geometries[wi][di], (di*(width+1))+wi);
			}
		}
	}
	
	public void adjustHeight(int ix, int iz, float height) {
		setHeight(ix, iz, getHeight(ix, iz)+height);
	}
	
	public float getHeight(int ix, int iz) {
		return heights[(iz*(width+1))+ix];
	}
	
	public void setHeight(int ix, int iz, float height) {
		heights[(iz*(width+1))+ix]=height;
		Geometry[] neighbors = getGeometries(ix, iz);
		if(neighbors[0]!=null) {
			neighbors[0].getMesh().getFloatBuffer(VertexBuffer.Type.Position).array()[(2*3)+1]=height;
			neighbors[0].getMesh().updateBound();
			neighbors[0].updateModelBound();
		}
		if(neighbors[1]!=null) {
			neighbors[1].getMesh().getFloatBuffer(VertexBuffer.Type.Position).array()[(3*3)+1]=height;
			neighbors[1].getMesh().updateBound();
			neighbors[1].updateModelBound();
		}
		if(neighbors[2]!=null) {
			neighbors[2].getMesh().getFloatBuffer(VertexBuffer.Type.Position).array()[(0*3)+1]=height;
			neighbors[2].getMesh().updateBound();
			neighbors[2].updateModelBound();
		}
		if(neighbors[3]!=null) {
			neighbors[3].getMesh().getFloatBuffer(VertexBuffer.Type.Position).array()[(1*3)+1]=height;
			neighbors[3].getMesh().updateBound();
			neighbors[3].updateModelBound();
		}
	}
	
	public Geometry[] getGeometries(int ix, int iz) {
		if(ix<0||iz<0||ix>width+1||iz>depth+1) throw new RuntimeException("Illegal request for geometries: " + ix + " " + iz);
		return new Geometry[]{
				(ix>0&&iz<=depth)?geometries[ix-1][iz]:null,
				(ix>0&&iz>0)?geometries[ix-1][iz-1]:null,
				(ix<=width&&iz>0)?geometries[ix][iz-1]:null,
				(ix<=width&&iz<=depth)?geometries[ix][iz]:null,
		};
	}
	
	@Deprecated
	public Quad[] getQuads(int ix, int iz) {
		if(ix<0||iz<0||ix>width+1||iz>depth+1) throw new RuntimeException("Illegal request for quads: " + ix + " " + iz);
		return new Quad[]{
				(ix>0&&iz<=depth)?quads[ix-1][iz]:null,
				(ix>0&&iz>0)?quads[ix-1][iz-1]:null,
				(ix<=width&&iz>0)?quads[ix][iz-1]:null,
				(ix<=width&&iz<=depth)?quads[ix][iz]:null,
		};
	}

}
