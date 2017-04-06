package com.gynt.openrail.java.core;

import java.util.List;

public interface Track {
	
	public Location computeDestination(Location currentlocation, double speed);
	
	public List<Location> computeTrackPath();
	
	public PrecomputedPath getPath();

}
