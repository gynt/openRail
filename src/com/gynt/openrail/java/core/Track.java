package com.gynt.openrail.java.core;

public interface Track {
	
	public Location translateOffset(double offset);
	
	public double computeOffset(double current_offset, double speed);
	
	public Location computeDestination(double current_offset, double speed);
	
	public Track getTrackAt(double offset);
	
	public Track getTrackAt(int exit);

	public Track connectTrack(int exit, Track track, int exit2);
	
	public void disconnectTrack(int exit, Track track);
	
}
