package com.gynt.openrail.java.core;

import java.util.List;

public interface PrecomputedPath {
	
	public Location computeDestination(Location currentlocation, double speed);
	
	public List<Location> computeFullPath();
	
	public List<Location> getFullPath();

}
