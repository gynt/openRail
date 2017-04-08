package com.gynt.openrail.java.core;

public interface TrackState {

	public TrackPath getTrackPath();
        
	public double getOffset();
        
        public int getEndPoint();
	
	public Location computeLocation();
        
        public Track getTrack();
        
        public void setOffset(double offset);
	
	public void setLocation(Track track, int endpoint, double offset);
	
}
