package com.gynt.openrail.core;

import com.gynt.openrail.core.Location.Operation;

public class CurveTrack extends Track {

	private double angle;
	private double length;
	private Location center_point;
	private double radius;
	private Location[] exits;

	public static CurveTrack getInstanceLength(Location location, double radius, double yaw, double length) {
		double angle = get_angle(length, radius);
		return new CurveTrack(location, radius, yaw, angle, length);
	}

	public static CurveTrack getInstanceAngle(Location location, double radius, double yaw, double angle) {
		double length = get_length(angle, radius);
		return new CurveTrack(location, radius, yaw, angle, length);
	}

	private CurveTrack(Location location, double radius, double yaw, double angle, double length) {
		super(location, yaw);
		this.radius = radius;
		this.angle = angle;
		this.length = length;
		this.exits = new Location[2];
		update();
	}

	
	public void compute() {
		exits[0] = center_point.apply(Math.cos(Math.toRadians(yaw)) * radius, 0, Math.sin(Math.toRadians(yaw)) * radius,
				Operation.ADD);
		exits[1] = center_point.apply(Math.cos(Math.toRadians(yaw + angle)) * radius, 0,
				Math.sin(Math.toRadians(yaw + angle)) * radius, Operation.ADD);
	}

	private static double get_length(double angle, double radius) {
		return calc_distance_on_curve(angle, radius);
	}

	private static double get_angle(double length, double radius) {
		return calc_degrees_change(length, radius);
	}

	private double get_yaw(int exit) {
		return exit == 0 ? yaw - (.5 * angle) : yaw + (.5 * angle);
	}

	private void set_yaw(double yaw, int exit) {
		this.yaw = yaw;
		this.update();
		// if exit==None:
		// self.update()
		// return
		// self.yaw=yaw+(self.angle/2) if exit==0 else yaw-(self.angle/2)
		// self.update()
	}

	private void update() {
		center_point = get_center_point();
		compute();
	}

	private Location get_center_point() {
		double x = Math.cos(Math.toRadians(yaw + 180 - (angle / 2.0))) * radius;
		double z = Math.sin(Math.toRadians(yaw + 180 - (angle / 2.0))) * radius;
		return getLocation().apply(x, 0, z, Operation.ADD);
	}

	private static double calc_distance_on_curve(double degrees, double radius) {
		return 2 * radius * Math.PI * degrees / 360.0;
	}

	private static double calc_degrees_change(double distance, double radius) {
		return distance * 360 / (2 * radius * Math.PI);
	}

	@Override
	public Move compute_move(double offset, double distance, boolean D) {
		double reach = offset + distance;
		Location a = D ? exits[1] : exits[0];
		double df = length - reach;
		if (df > 0) {
			double dc = calc_degrees_change(reach, radius);
			double dco = D ? yaw + (dc) : yaw + (angle - dc);
			return new Move(center_point.apply(Math.cos(Math.toRadians(dco)) * radius, 0,
					Math.sin(Math.toRadians(dco)) * radius, Operation.ADD), reach, 0);
		} else {
			return new Move(a, length, df * -1);
		}
	}

	private double point_to_offset(Location point, boolean D) {
		Location a = D ? exits[0] : exits[1];
		Location dv = point.apply(center_point.x, center_point.y, center_point.z, Operation.SUBTRACT);
		double an = D ? Math.toDegrees(Math.acos(dv.x / radius)) - yaw
				: (yaw + angle) - Math.toDegrees(Math.acos(dv.x / radius));
		return calc_distance_on_curve(an, radius);
	}

	@Override
	public Location[] getExits() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setLocation(Location location) {
		// TODO Auto-generated method stub

	}

	@Override
	public Location getLocation() {
		// TODO Auto-generated method stub
		return null;
	}

}
