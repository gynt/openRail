package com.gynt.openrail.java.core.utils;

import com.gynt.openrail.java.core.Location;
import com.gynt.openrail.java.core.Position;

public class TrackComputation {
	
	public static final Location computeStraight(Position center_point, int endpoint, double armlength) {
		if(endpoint==0) {
			double x = center_point.location.x+(Math.cos(center_point.pitch-Math.toRadians(180))*armlength);
			double z = center_point.location.z+(Math.sin(center_point.pitch-Math.toRadians(180))*armlength);
			double y = center_point.location.y+(Math.sin(center_point.yaw-Math.toRadians(180))*armlength);
			return new Location(x,y,z);
		} else if(endpoint==1) {
			double x = center_point.location.x+(Math.cos(center_point.pitch)*armlength);
			double z = center_point.location.z+(Math.sin(center_point.pitch)*armlength);
			double y = center_point.location.y+(Math.sin(center_point.yaw)*armlength);
			return new Location(x,y,z);
		}
		throw new RuntimeException("Invalid endpoint: " + endpoint);
	}
	
	public static final Location computeCurve(Position center_point, int endpoint, double armlength, double radius, Position curve_center) {
		if(endpoint==0) {
			double x = center_point.location.x+(Math.cos(center_point.pitch-Math.toRadians(180))*armlength);
			double z = center_point.location.z+(Math.sin(center_point.pitch-Math.toRadians(180))*armlength);
			double y = center_point.location.y+(Math.sin(center_point.yaw-Math.toRadians(180))*armlength);
			return new Location(x,y,z);
		} else if(endpoint==1) {
			double x = center_point.location.x+(Math.cos(center_point.pitch)*armlength);
			double z = center_point.location.z+(Math.sin(center_point.pitch)*armlength);
			double y = center_point.location.y+(Math.sin(center_point.yaw)*armlength);
			return new Location(x,y,z);
		}
		throw new RuntimeException("Invalid endpoint: " + endpoint);
	}

}
