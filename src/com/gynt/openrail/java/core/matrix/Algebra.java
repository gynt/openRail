package com.gynt.openrail.java.core.matrix;

public class Algebra {


	public static class Matrix {
	    public final static Matrix IDENTITY=new Matrix(Vec.X_AXIS, Vec.Y_AXIS, Vec.Z_AXIS);
	    public final Vec xAxis, yAxis, zAxis;
	    
	    public Matrix(double ... e) {
	        if(e.length!=9) throw new RuntimeException();
	        xAxis = new Vec(e[0], e[3], e[6]);
	        yAxis = new Vec(e[1], e[4], e[7]);
	        zAxis = new Vec(e[2], e[5], e[8]);
	    }
	    
	    public Matrix(Vec xAxis, Vec yAxis, Vec zAxis) {
	        this.xAxis = xAxis;
	        this.yAxis = yAxis;
	        this.zAxis = zAxis;
	    }
	    
	    public Matrix(Vec axis, double theta) {
	        Vec u=axis.normalize();
	        double sin=Math.sin(theta);
	        double cos=Math.cos(theta);
	        double uxy=u.x*u.y*(1-cos);
	        double uyz=u.y*u.z*(1-cos);
	        double uxz=u.x*u.z*(1-cos);
	        double ux2=u.x*u.x*(1-cos);
	        double uy2=u.y*u.y*(1-cos);
	        double uz2=u.z*u.z*(1-cos);
	        double uxsin=u.x*sin;
	        double uysin=u.y*sin;
	        double uzsin=u.z*sin;
	        
	        xAxis = new Vec(cos+ux2, uxy+uzsin, uxz-uysin);
	        yAxis = new Vec(uxy-uzsin, cos+uy2, uyz+uxsin);
	        zAxis = new Vec(uxz+uysin, uyz-uxsin, cos+uz2);
	    }
	    
	    static public Matrix xRotationMatrix(double theta) {
	        double cos = Math.cos(theta), sin = Math.sin(theta);
	        return new Matrix(Vec.X_AXIS,
	                          new Vec(0, cos, sin),
	                          new Vec(0, -sin, cos));
	    }
	    
	    static public Matrix yRotationMatrix(double theta) {
	        double cos = Math.cos(theta), sin = Math.sin(theta);
	        return new Matrix(new Vec(cos, 0, -sin),
	                          Vec.Y_AXIS,
	                          new Vec(sin, 0, cos));
	    }
	    
	    static public Matrix zRotationMatrix(double theta) {
	        double cos = Math.cos(theta), sin = Math.sin(theta);
	        return new Matrix(new Vec(cos, sin, 0),
	                          new Vec(-sin, cos, 0),
	                          Vec.Z_AXIS);
	    }
	    
	    public Matrix rotX(double theta) {
	        return xRotationMatrix(theta).mul(this);
	    }
	    
	    public Matrix rotY(double theta) {
	        return yRotationMatrix(theta).mul(this);
	    }
	    
	    public Matrix rotZ(double theta) {
	        return zRotationMatrix(theta).mul(this);
	    }
	    
	    public Matrix mul(double d) {
	        return new Matrix(xAxis.mul(d), yAxis.mul(d), zAxis.mul(d));
	    }
	    
	    public Vec mul(Vec v) {
	        return xAxis.mul(v.x).add(yAxis.mul(v.y)).add(zAxis.mul(v.z));
	    }
	    
	    private Matrix mul(Matrix m) {
	        return new Matrix(mul(m.xAxis), mul(m.yAxis), mul(m.zAxis));
	    }
	    
	    public Matrix rotateRel(Matrix m) {
	        return mul(m);
	    }
	    
	    public Matrix rotateAbs(Matrix m) {
	        return m.mul(this);
	    }
	    
	    public Matrix normalize() {
	        Vec vz=xAxis.crossProduct(yAxis);
	        Vec vy=vz.crossProduct(xAxis);        
	        return new Matrix(xAxis.normalize(),
	                          vy.normalize(),
	                          vz.normalize());
	    }
	    
	    public Matrix transpose() {
	        return new Matrix(xAxis.x, xAxis.y, xAxis.z,
	                          yAxis.x, yAxis.y, yAxis.z,
	                          zAxis.x, zAxis.y, zAxis.z);
	    }
	    
	    public double determinant() {
	        return xAxis.x*(yAxis.y*zAxis.z-zAxis.y*yAxis.z) -
	               yAxis.x*(zAxis.z*xAxis.y-zAxis.y*xAxis.z) +
	               zAxis.x*(xAxis.y*yAxis.z-yAxis.y*xAxis.z);
	    }
	    
	    public Matrix inverse() {
	        Vec A = yAxis.crossProduct(zAxis);
	        Vec B = zAxis.crossProduct(xAxis);
	        Vec C = xAxis.crossProduct(yAxis);
	        return new Matrix(A,B,C).transpose().mul(1/determinant());
	    }
	    
	    public Matrix oppositeRotMatrix() {
	        return transpose();
	    }
	    
	    @Override
	    public boolean equals(Object o) {
	        if(!(o instanceof Matrix)) return false;
	        Matrix m=(Matrix)o;
	        return m==this || xAxis.equals(m.xAxis) && yAxis.equals(m.yAxis) && zAxis.equals(m.zAxis);
	    }
	    
	    public String toString() {
	        return String.format(java.util.Locale.ENGLISH,
	            "[%.3f, %.3f, %.3f]\n[%.3f, %.3f, %.3f]\n[%.3f, %.3f, %.3f]",
	            xAxis.x, yAxis.x, zAxis.x,
	            xAxis.y, yAxis.y, zAxis.y,
	            xAxis.z, yAxis.z, zAxis.z);
	    }
	    
	}

	public static class Vec {
	    public final static Vec X_AXIS=new Vec(1,0,0);
	    public final static Vec Y_AXIS=new Vec(0,1,0);
	    public final static Vec Z_AXIS=new Vec(0,0,1);
	    public final static Vec ORIGIN=new Vec(0,0,0);

	    public final double x,y,z;
	    
	    public Vec(double x, double y, double z) {
	        this.x=x;
	        this.y=y;
	        this.z=z;
	    }
	    
	    public double dotProduct(Vec v) {
	        return x*v.x+y*v.y+z*v.z;
	    }
	    
	    public Vec crossProduct(Vec v) {
	        double rx=y*v.z-z*v.y;
	        double ry=z*v.x-x*v.z;
	        double rz=x*v.y-y*v.x;
	        return new Vec(rx, ry, rz);
	    }
	    
	    public Vec newLength(double newLength) {
	        double length=getLength();
	        if(length==newLength) return this;
	        if(length==0) return X_AXIS.newLength(newLength);
	        return new Vec(x*newLength/length, y*newLength/length, z*newLength/length);
	    }
	    
	    public Vec rotationAxis() {
	        return rotationAxis(X_AXIS);
	    }
	    
	    //The rotation axis to rotate v onto this
	    public Vec rotationAxis(Vec v) {
	        return normalizedCrossProduct(v);
	    }
	    
	    public Vec normalizedCrossProduct(Vec v) {
	        Vec r=crossProduct(v);
	        if(r.getLength()<0.0001) {
	            r=crossProduct(X_AXIS);
	        }
	        if(r.getLength()<0.0001) {
	            r=crossProduct(Y_AXIS);
	        }
	        if(r.getLength()<0.0001) {
	            return X_AXIS;
	        }
	        return r.normalize();
	    }
	    
	    public double angle() {
	        return angleTo(X_AXIS);
	    }
	    
	    public double angleTo(Vec v) {
	        double cosTheta=dotProduct(v)/(getLength()*v.getLength());
	        
	        return Math.acos(cosTheta);
	    }
	    
	    public double getLength() {
	        return Math.sqrt(x*x+y*y+z*z);
	    }        
	    
	    
	    public Vec rotate(Matrix m) {
	        return m.mul(this);
	    }
	    
	    public Vec normalize() {
	        double length=getLength();
	        
	        if(length==1) return this;
	        if(length==0) return X_AXIS;
	        return new Vec(x/length, y/length, z/length);
	    }
	    
	    public Vec addX(double a) {
	        return new Vec(x+a, y, z);
	    }
	    
	    public Vec addY(double a) {
	        return new Vec(x, y+a, z);
	    }
	    
	    public Vec addZ(double a) {
	        return new Vec(x, y, z+a);
	    }
	    
	    public Vec add(Vec v) {
	        return new Vec(x+v.x, y+v.y, z+v.z);
	    }
	            
	    public Vec sub(Vec v) {
	        return new Vec(x-v.x, y-v.y, z-v.z);
	    }
	    
	    public Vec mul(double m) {
	        return new Vec(x*m, y*m, z*m);
	    }
	    
	    public Vec div(double d) {
	        return new Vec(x/d, y/d, z/d);
	    }
	    
	    public Vec neg() {
	        return new Vec(-x, -y, -z);
	    }
	    
	    @Override
	    public boolean equals(Object o) {
	        if(!(o instanceof Vec)) return false;
	        Vec v=(Vec)o;
	        return v==this || sub(v).getLength() < 0.0001;
	    }
	    
	    public String toString() {
	    return String.format(java.util.Locale.ENGLISH,
	        "(%.3f, %.3f, %.3f)", x, y, z);
	    }
	    
	}
	
}
