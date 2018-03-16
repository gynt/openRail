package com.gynt.openrail.java.core.paths;

public class Location {
	
	public final double x;
	public final double y;
	public final double z;
	public final double pitch;
	public final double roll;
	public final double yaw;

	public Location(double x, double y, double z, double pitch, double roll, double yaw) {
		this.x = x;
		this.y=y;
		this.z=z;
		this.pitch=pitch;
		this.roll=roll;
		this.yaw=yaw;
	}
	
	public Location(double x, double y, double z) {
		this(x,y,z,0,0,0);
	}
	
	public Location translate(Location o) {
		return subtract(o);
	}
	
	public Location subtract(Location o) {
		return new Location(x-o.x, y-o.y, z-o.z);
	}
	
	public Location add(Location o) {
		return new Location(x+o.x, y+o.y, z+o.z);
	}
	
	public Location rotate(Location r, double angle) {
		Location rstart = this.subtract(r);
		double dstart = Computation.distance(rstart, r);
		double newx = Computation.x(angle, dstart);
		double newz = Computation.z(angle, dstart);
		return new Location(newx, y, newz).add(r);
	}
	
	@Override
	public String toString() {
		return "x: "+x+", y: "+y+", z: "+z;
	}

}
