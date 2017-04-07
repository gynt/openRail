package com.gynt.openrail.java.core;

public interface Track {
	
	public Track getTrackAt(int endpoint);

	public Track connectTrack(int endpoint, Track track, int endpoint2);
	
	public void disconnectTrack(int endpoint, Track track);
	
	public TrackPath getTrackPath(int endpoint);
	
	public void setConnector(int endpoint, Track track);
	
	public int getConnectedEndpoint(Track track);
	
	public void setPosition(Position l);
	
	public Position getPosition();
	
	public Location getLocation(int endpoint);
	
}
