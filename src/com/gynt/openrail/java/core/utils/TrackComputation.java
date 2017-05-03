package com.gynt.openrail.java.core.utils;

import com.gynt.openrail.java.core.Location;
import com.gynt.openrail.java.core.Position;

public class TrackComputation {

    public static final Location computeStraight(Position center_point, int endpoint, double armlength) {
        if (endpoint == 0) {
            double x = center_point.location.x + (Math.cos(center_point.pitch - Math.toRadians(180)) * armlength);
            double z = center_point.location.z + (Math.sin(center_point.pitch - Math.toRadians(180)) * armlength);
            double y = center_point.location.y + (Math.sin(center_point.yaw - Math.toRadians(180)) * armlength);
            return new Location(x, y, z);
        } else if (endpoint == 1) {
            double x = center_point.location.x + (Math.cos(center_point.pitch) * armlength);
            double z = center_point.location.z + (Math.sin(center_point.pitch) * armlength);
            double y = center_point.location.y + (Math.sin(center_point.yaw) * armlength);
            return new Location(x, y, z);
        }
        throw new RuntimeException("Invalid endpoint: " + endpoint);
    }

    public static final Location computeCurveCenter(Position center_point, double radius) {
        double curvecenterx = Math.cos(Math.toRadians(center_point.pitch)) * radius;
        double curvecentery = 0;
        double curvecenterz = Math.sin(Math.toRadians(center_point.pitch)) * radius;
        return new Location(center_point.location.x - curvecenterx, center_point.location.y - curvecentery, center_point.location.z - curvecenterz);
    }

    public static final Location computeCurve(Position center_point, int endpoint, double armlength, double radius, Position curve_center) {
        double cir = 2 * radius * Math.PI;
        double ang = armlength / cir;
        if (endpoint == 0) {
//TODO: This is left curve only! in case of switches
            double angl = center_point.pitch - ang;

            Location curvecenter = computeCurveCenter(center_point, radius);

            double x = curvecenter.x + (Math.cos(Math.toRadians(angl)) * radius);
            double y = curvecenter.y;
            double z = curvecenter.z + (Math.sin(Math.toRadians(angl)) * radius);

            return new Location(x, y, z);
        } else if (endpoint == 1) {
            //TODO: This is left curve only! in case of switches
            double angl = center_point.pitch + ang;

            Location curvecenter = computeCurveCenter(center_point, radius);

            double x = curvecenter.x + (Math.cos(Math.toRadians(angl)) * radius);
            double y = curvecenter.y;
            double z = curvecenter.z + (Math.sin(Math.toRadians(angl)) * radius);

            return new Location(x, y, z);
        }
        throw new RuntimeException("Invalid endpoint: " + endpoint);
    }

}
