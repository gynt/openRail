package com.gynt.openrail.java.core;

public interface Train {

	public double getSpeed();
	public void setSpeed(double speed);
	
	public TrackState[] getTrackStates();
        public Position computePosition();
	
//	public Track[] getCurrentTracks();
//	public Track getTrackHead();
//	public Track getTrackRear();	
//	public double getOffsetHead();
//	public double getOffsetRear();
//	
//	public void setLocationHead(Track t, double offset);
//	public void setLocationRear(Track t, double offset);
//	
//	
//	public Location getLocationHead();
//	public Location getLocationRear();
//	
//	public void setLocationHead(Location l);
//	public void setLocationRear(Location l);

	
	public void move();
	
}
