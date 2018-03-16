package com.gynt.openrail.java.core.tracks;

import java.util.List;

import javax.management.RuntimeErrorException;

import com.gynt.openrail.java.core.paths.Location;
import com.gynt.openrail.java.core.paths.PathDescriptor;

public class TrackDescriptor {
	
	private List<PathDescriptor> paths;
	private PathDescriptor active;

	public TrackDescriptor(List<PathDescriptor> paths) {
		this.paths = paths;
		this.active = paths.get(0);
	}
	
	public void setActive(int index) {
		this.active = paths.get(0);
	}
	
	private void validateOffset(double offset) {
		if(offset > active.length()) {
			throw new RuntimeException("Offset too large for track: " + offset + " max: " + active.length());
		}
	}
	
	public Location compute(double offset, boolean reversed) {
		validateOffset(offset);
		return reversed ? active.compute(active.length()-offset) : active.compute(offset);
	}
	
	public Location compute(double offset) {
		return compute(offset, false);
	}

}
