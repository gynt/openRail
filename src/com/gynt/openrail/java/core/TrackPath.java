package com.gynt.openrail.java.core;

public interface TrackPath {
	
	public double getPathLength();
	
	public Track getTrack();
	
	public Track getTrackAt(double offset);
	
	public Track getTrackAt(int endpoint);

	public Location translateOffset(double offset);
	
}
