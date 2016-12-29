package com.gynt.openrail.core;

import com.gynt.openrail.core.Location.Operation;

public abstract class Track {
	

	
	public static class Move {
		public Location newlocation;
		public double newoffset;
		public double remainder;

		Move(Location newlocation, double newoffset, double remainder) {
			this.newlocation = newlocation;
			this.newoffset = newoffset;
			this.remainder = remainder;
		}
	}

	protected double yaw;
	protected Location location;

	Track(Location location, double yaw) {
		this.yaw = yaw;
		this.location = location;
	}
	
	public abstract double getYaw(int exitindex);
	public abstract void setYaw(double yaw, int exitindex);
	public abstract void setYaw(double yaw);
	


	
	public abstract Move compute_move(double offset, double distance, boolean D);
	

	protected abstract void onNewLocation();
	
	public void setLocation(Location location) {
		this.location = location;
		onNewLocation();
	}
	
	public Location getLocation() {
		return this.location;
	}
	
}
