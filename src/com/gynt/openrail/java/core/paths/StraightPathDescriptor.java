package com.gynt.openrail.java.core.paths;

public class StraightPathDescriptor extends PathDescriptor {
	
	public static final StraightPathDescriptor ORIGIN = new StraightPathDescriptor(new Location(0, 0, 0), new Location(0,0,1));
	
	public StraightPathDescriptor(Location start, Location end) {
		super(start, end);
	}

	@Override
	public Location compute(double offset) {
		return Computation.Straight.compute(offset, start, end);
	}

	@Override
	public double length() {
		return Computation.Straight.length(start, end);
	}

}
