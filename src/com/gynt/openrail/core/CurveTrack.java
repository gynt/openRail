package com.gynt.openrail.core;

import com.gynt.openrail.core.Location.Operation;

public class CurveTrack extends ExitableTrack {

	double angle;
	private double length;
	private Location center_point;
	private double radius;

	public static CurveTrack getInstanceLength(Location location, double radius, double yaw, double length) {
		double angle = get_angle(length, radius);
		return new CurveTrack(location, radius, yaw, angle, length);
	}

	public static CurveTrack getInstanceAngle(Location location, double radius, double yaw, double angle) {
		double length = get_length(angle, radius);
		return new CurveTrack(location, radius, yaw, angle, length);
	}

	private CurveTrack(Location location, double radius, double yaw, double angle, double length) {
		super(location, yaw, 2);
		this.radius = radius;
		this.angle = angle;
		this.length = length;
		update();
	}

	public void compute() {
		exits[0].location = center_point.apply(Math.cos(Math.toRadians(yaw)) * radius, 0,
				Math.sin(Math.toRadians(yaw)) * radius, Operation.ADD);
		exits[1].location = center_point.apply(Math.cos(Math.toRadians(yaw + angle)) * radius, 0,
				Math.sin(Math.toRadians(yaw + angle)) * radius, Operation.ADD);
	}

	private static double get_length(double angle, double radius) {
		return calc_distance_on_curve(angle, radius);
	}

	private static double get_angle(double length, double radius) {
		return calc_degrees_change(length, radius);
	}

	public double getYaw(int exit) {
		return exit == 0 ? yaw - (.5 * angle) : yaw + (.5 * angle);
	}

	private void updateYaw() {
		exits[0].yaw = this.yaw - (angle / 2.0);
		exits[1].yaw = this.yaw + (angle / 2.0);
	}

	public void setYaw(double yaw, int exit) {
		if (exit == 0) {
			setYaw(yaw + (angle / 2.0));
		} else {
			setYaw(yaw - (angle / 2.0));
		}

	}

	public void setYaw(double yaw) {
		this.yaw = yaw;
		this.updateYaw();
		this.update();
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
		Location a = D ? exits[1].location : exits[0].location;
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
		Location a = D ? exits[0].location : exits[1].location;
		Location dv = point.apply(center_point.x, center_point.y, center_point.z, Operation.SUBTRACT);
		double an = D ? Math.toDegrees(Math.acos(dv.x / radius)) - yaw
				: (yaw + angle) - Math.toDegrees(Math.acos(dv.x / radius));
		return calc_distance_on_curve(an, radius);
	}

	@Override
	protected void onNewLocation() {
		update();
	}

}
