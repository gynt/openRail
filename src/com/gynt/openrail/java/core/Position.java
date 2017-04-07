package com.gynt.openrail.java.core;

public class Position {

	public Location location;
	public double pitch;
	public double yaw;
	//public double roll;
	
	public Position(Location location, double pitch, double yaw) {
		this.location=location;
		this.pitch=pitch;
		this.yaw=yaw;
	}
	
}
