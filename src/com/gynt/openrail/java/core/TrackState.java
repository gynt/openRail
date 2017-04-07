package com.gynt.openrail.java.core;

public interface TrackState {

	public TrackPath getTrackPath();
	public double getOffset();
	
	public Location computeLocation();
	
	public void setLocation(TrackPath track, double offset);
	
}
