package com.gynt.openrail.java.core;

public class SwitchTrack implements Track {
	
	private TrackPath[] paths;
	
	public SwitchTrack() {
		paths = new TrackPath[3];
	}

	@Override
	public Track getTrackAt(int endpoint) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Track connectTrack(int endpoint, Track track, int endpoint2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void disconnectTrack(int endpoint, Track track) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public TrackPath getTrackPath(int endpoint) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setConnector(int endpoint, Track track) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getConnectedEndpoint(Track track) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setPosition(Position l) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Position getPosition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Location getLocation(int endpoint) {
		// TODO Auto-generated method stub
		return null;
	}

}
