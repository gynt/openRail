package com.gynt.openrail.core;

public class Location {
	
	public enum Operation {
		ADD,SUBTRACT,MULTIPLY, EQUALS;
		
		public double perform(double a, double b) {
			switch(this) {
			case ADD:
				return a+b;
			case SUBTRACT:
				return a-b;
			case MULTIPLY:
				return a*b;
			case EQUALS:
				return a==b?0:1;
			default:
				throw new RuntimeException();
			}
		}
		
	}

	public double x;
	public double y;
	public double z;
	
	Location(double x, double y, double z) {
		this.x=x;
		this.y=y;
		this.z=z;
	}
	
	public Location apply(double x, double y, double z, Operation op) {
		return new Location(op.perform(this.x, x),op.perform(this.y, y),op.perform(this.z, z));
	}
	
}
