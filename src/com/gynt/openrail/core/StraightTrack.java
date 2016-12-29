package com.gynt.openrail.core;

import com.gynt.openrail.core.Location.Operation;

public class StraightTrack extends ExitableTrack {

	private Location location;
	private double yaw;
	private double length;

	StraightTrack(Location location, double yaw, double length) {
		super(location, yaw, 2);
		this.length = length;
		compute();
	}

	public void compute() {
		double halfway = length * 0.5;
		exits[0].location = location.apply(Math.cos(Math.toRadians(yaw + 180)) * halfway, 0,
				Math.sin(Math.toRadians(yaw + 180)) * halfway, Operation.ADD);
		exits[1].location = location.apply(Math.cos(Math.toRadians(yaw)) * halfway, 0,
				Math.sin(Math.toRadians(yaw)) * halfway, Operation.ADD);
	}

	@Override
	public Move compute_move(double offset, double distance, boolean D) {
		double reach = offset + distance;
		Location a = D ? exits[1].location : exits[0].location;
		double df = length - reach;
		if (df > 0) {
			double off = D ? 0 : 180;
			double x = Math.cos(Math.toRadians(yaw + off)) * df;
			double z = Math.sin(Math.toRadians(yaw + off)) * df;
			return new Move(a.apply(x, 0, z, Operation.SUBTRACT), reach, 0);
		} else {
			return new Move(a, length, df * -1);
		}
	}

	private double point_to_offset(Location point, boolean D) {
		Location a = D ? exits[0].location : exits[1].location;
		Location dv = point.apply(a.x, a.y, a.z, Operation.SUBTRACT);
		return Math.abs(dv.x / Math.cos(Math.toRadians(yaw)));
	}

	@Override
	public double getYaw(int exitindex) {
		return yaw;
	}

	@Override
	public void setYaw(double yaw, int exitindex) {
		setYaw(yaw);
	}

	@Override
	public void setYaw(double yaw) {
		this.yaw = yaw;
		updateYaw();
		compute();
	}

	private void updateYaw() {
		exits[0].yaw = this.yaw;
		exits[1].yaw = this.yaw;
	}

	@Override
	protected void onNewLocation() {
		compute();
	}

}
