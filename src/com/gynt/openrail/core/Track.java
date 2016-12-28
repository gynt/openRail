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
	private Location location;

	Track(Location location, double yaw) {
		this.yaw = yaw;
		this.location = location;
	}
	
    public int get_exit_index(Location point) {
        for(int i =0; i < getExits().length; i++){
        	Location exit = getExits()[i];
            Location p=point.apply(exit.x, exit.y, exit.z, Operation.EQUALS);
            if(p.x==0 && p.y==0 && p.z==0){
                return i;
            }
        }
        return -1;
    }

	
	public abstract Move compute_move(double offset, double distance, boolean D);
	
	public abstract Location[] getExits();

	public abstract void setLocation(Location location);
	
	public abstract Location getLocation();
	
}
