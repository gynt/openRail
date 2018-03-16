package com.gynt.openrail.java.core.paths;

public class CurvedPathDescriptor extends PathDescriptor {
	
	public static final CurvedPathDescriptor ORIGIN = new CurvedPathDescriptor(0, 22.5, 1, new Location(0,0,0));

	private Location circle_center;

	public CurvedPathDescriptor(double angle1, double angle2, double radius, Location circle_center) {
		super(createLocation(angle1, radius, circle_center), createLocation(angle2, radius, circle_center));
		this.circle_center = circle_center;
	}
	
	private static final Location createLocation(double angle, double radius, Location center) {
		return new Location(Computation.x(angle, radius), 0, Computation.z(angle, radius)).add(center);
	}
	
	public CurvedPathDescriptor(Location start, Location end, Location circle_center) {
		super(start, end);
		this.circle_center = circle_center;
	}
	
	@Override
	public void translate(Location delta) {
		super.translate(delta);
		circle_center = circle_center.translate(delta);
	}
	
	@Override
	public void rotate(Location relative, double angle) {
		super.rotate(relative, angle);
		circle_center = circle_center.rotate(relative, angle);
	}

	@Override
	public Location compute(double offset) {
		return Computation.Curve.compute(offset, start, end, circle_center);
	}

	@Override
	public double length() {
		return Computation.Curve.length(start, end, circle_center);
	}
	
	public static void main(String[] args) {
		double radius = 1;
		CurvedPathDescriptor p = new CurvedPathDescriptor(
				new Location(Math.cos(0.25*Math.PI), 0, Math.sin(0.25*Math.PI)), 
				new Location(-1*Math.cos(0.25*Math.PI), 0, -1*Math.sin(0.25*Math.PI)), 
				new Location(0,0,0));
		
		double offset = ((Math.PI*2*radius)/8)*4;
		System.out.println(p.compute(offset));
		
		System.out.println(p.length());
	}

}
