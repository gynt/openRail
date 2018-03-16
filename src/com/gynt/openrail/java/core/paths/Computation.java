package com.gynt.openrail.java.core.paths;

public class Computation {
	
	public static class Straight {
		public static Location compute(double offset, Location start, Location end) {
			double angle = computeAngle(start, end);
			double rx = offset*Math.cos(angle);
			double rz = offset*Math.sin(angle);
			if(pythagoras(rx, rz)!=offset) {
				throw new RuntimeException("Unequality: "+rx+" (x) "+rz+" (y) "+offset+" (offset)");
			}
			return new Location(rx, 0, rz, 0, 0, 0);
		}
		
		public static double length(Location start, Location end) {
			return pythagoras(dx(start, end),dy(start, end), dz(start, end));
		}
	}
	
	
	
	public static double angle(double x, double z) {
		return Math.atan(z/x);
	}
	
	public static double d(double a, double b) {
		return b-a;
	}
	
	public static double pythagorash(double c, double q) {
		return Math.sqrt((c*c)-(q*q));
	}
	
	public static double pythagoras(double a, double b) {
		return Math.sqrt((a*a)+(b*b));
	}
	
	public static double pythagoras(double a, double b, double c) {
		return Math.sqrt((a*a)+(b*b)+(c*c));
	}
	
	public static double dx(Location a, Location b) {
		return b.x-a.x;
	}
	
	public static double dy(Location a, Location b) {
		return b.y-a.y;
	}
	
	public static double dz(Location a, Location b) {
		return b.z-a.z;
	}
	
	public static double distance(Location a, Location b) {
		return pythagoras(dx(a,b), dy(a,b), dz(a,b));
	}
	
	public static double circumference(double radius) {
		return circumference()*radius;
	}
	
	public static double circumference() {
		return Math.PI*2;
	}
	
	public static double fullCircle() {
		return Math.PI*2;
	}
	
	public static double x(double angle, double radius) {
		return Math.cos(angle)*radius;
	}
	
	public static double z(double angle, double radius) {
		return Math.sin(angle)*radius;
	}
	
	/**Computes angle of a relative to b.*/
	public static double computeAngle(Location a, Location b) {
		double angle =  angle(dx(b, a), dz(b, a));
		if(dx(b, a) < 0) {
			return angle + Math.PI;
		}
		return angle;
	}
	
	
	public static class Curve {
		public static Location compute(double offset, Location start, Location end, Location circle_center, double radius, double angle) {
			double angle_change = (offset/circumference(radius))*2*Math.PI;
			double new_angle = angle + angle_change;
			double x = x(new_angle, radius);
			double z = z(new_angle, radius);
			return new Location(x, circle_center.y, z, 0, 0, 0);
			
		}
		
		public static Location compute(double offset, Location start, Location end, Location circle_center, double radius) {
			double angle_start = angle(dx(start, circle_center), dz(start, circle_center));
			return compute(offset, start, end, circle_center, radius, angle_start);
		}
		
		public static Location compute(double offset, Location start, Location end, Location circle_center) {
			double radius = pythagoras(dx(start, circle_center), dy(start, circle_center), dz(start, circle_center));
			double angle_start = angle( dx(start, circle_center),dz(start, circle_center));
			return compute(offset, start, end, circle_center, radius, angle_start);
		}
		
		/**Computes the radius of a circle given a midpoint and a point on the circle.*/
		public static double computeRadius(Location a, Location b) {
			return pythagoras(dx(a, b), dy(a, b), dz(a, b));
		}
		

		public static double length(Location start, Location end, Location circle_center) {
			double radius = computeRadius(start, circle_center);
			double angle = computeAngle(start, circle_center);
			double angle_end = computeAngle(end, circle_center);
			double diff = angle_end - angle;
			double part = diff / fullCircle();
			double result = part * circumference(radius);
			return result;
		}
	}
	
	
	
	

}
