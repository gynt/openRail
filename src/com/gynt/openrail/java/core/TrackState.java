package com.gynt.openrail.java.core;

public interface TrackState {

	public Track getTrack();
	public double getOffset();
	public Location getLocation();
	
	public void setLocation(Track track, double offset);
	
}
