package com.gynt.openrail.java.game;

import com.gynt.openrail.java.core.paths.Location;
import com.gynt.openrail.java.core.tracks.TrackDescriptor;

public class Track {
	
	private TrackDescriptor desc;
	private boolean trained;

	public Track(TrackDescriptor t) {
		this.desc = t;
	}
	
	public Location compute(double offset) {
		return this.desc.compute(offset);
	}
	
	public void containsTrain(boolean b) {
		this.trained = b;
	}
	
	public boolean containsTrain() {
		return this.trained;
	}

}
