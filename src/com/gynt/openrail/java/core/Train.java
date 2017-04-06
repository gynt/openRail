package com.gynt.openrail.java.core;

public interface Train {

	public double getSpeed();
	public void setSpeed(double speed);
	
	public Track[] getCurrentTracks();
	public Track getTrackHead();
	public Track getTrackRear();
	public void setTrackHead(Track head);
	public void setTrackRear(Track rear);
	
	public void move();
	
}
