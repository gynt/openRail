package com.gynt.openrail.java.core.paths;

public abstract class PathDescriptor {

	protected Location start;
	protected Location end;

	public PathDescriptor(Location start, Location end) {
		this.start = start;
		this.end = end;
	}

	public abstract Location compute(double offset);

	public abstract double length();

	public void translate(Location delta) {
		start = start.translate(delta);
		end = end.translate(delta);
	}
	
	public void rotate(Location relative, double angle) {
		start = start.rotate(relative, angle);
		end = end.rotate(relative, angle);
	}

}
