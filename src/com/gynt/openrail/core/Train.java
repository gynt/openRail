package com.gynt.openrail.core;

import com.gynt.openrail.core.ExitableTrack.Exit;
import com.gynt.openrail.core.Location.Operation;
import com.gynt.openrail.core.Track.Move;

public class Train {

	public static Train getInstance(Location center, double length) {
		Location front_axis = calc_front_wheel(center, length, 0, 0);
		Location back_axis = calc_back_wheel(center, length, 0, 0);
		return new Train(center, length, front_axis, back_axis);
	}

	public static Train getInstance(Location front_axis, Location back_axis) {
		Location center = calc_location(front_axis, back_axis);
		double length = calc_length(front_axis, back_axis);
		return new Train(center, length, front_axis, back_axis);
	}

	public Location location;
	public Location front_axis;
	public Location back_axis;
	private double length;
	private ExitableTrack track1;
	private ExitableTrack track2;
	private double offset;
	private double speed;
	private boolean D;
	private boolean D2;
	private double offset2;
	private Board board;
	private Object yaw;

	public Train(Location center, double length, Location front_axis, Location back_axis) {
		this.front_axis = front_axis;
		this.back_axis = back_axis;
		this.location = center;
		this.length = length;
		this.offset = 0;
	}

	public Location getLocation() {
		return location;
	}

	public void setOffset(double offset) {
		this.offset = offset;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public void setDirection(boolean direction) {
		this.D = direction;
	}

	public void setDirection2(boolean direction) {
		this.D2 = direction;
	}

	public void setTracks(ExitableTrack front_wheel, ExitableTrack back_wheel) {
		this.track1 = front_wheel;
		this.track2 = back_wheel;
	}

	public static double calc_length(Location wheel1, Location wheel2) {
		Location temp = wheel1.apply(wheel2.x, wheel2.y, wheel2.z, Operation.SUBTRACT);
		double length = Math.sqrt(Math.pow(temp.x, 2) + Math.pow(temp.z, 2));
		return length;
	}

	private static Location calc_front_wheel(Location location, double length, double yaw, double pitch) {
		return location.apply(0.5 * length * (Math.cos(Math.toRadians(yaw))),
				0.5 * length * Math.sin(Math.toRadians(pitch)), 0.5 * length * (Math.sin(Math.toRadians(yaw))),
				Operation.ADD);
	}

	private static Location calc_back_wheel(Location location, double length, double yaw, double pitch) {
		return location.apply(0.5 * length * (Math.cos(Math.toRadians(yaw + 180))),
				0.5 * length * Math.sin(Math.toRadians(pitch + 180)),
				0.5 * length * (Math.sin(Math.toRadians(yaw + 180))), Operation.ADD);
	}

	private static double calc_rotation(Location wheel1, Location wheel2) {
		Location temp = wheel1.apply(wheel2.x, wheel2.y, wheel2.z, Operation.SUBTRACT);
		double yaw = Math.toDegrees(Math.atan(temp.z / temp.x));
		// pitch=Math.degrees(Math.atan(temp.y/temp.x))
		return yaw
		// , pitch
		;

	}

	private static Location calc_location(Location wheel1, Location wheel2) {
		Location temp = wheel1.apply(wheel2.x, wheel2.y, wheel2.z, Operation.SUBTRACT);
		Location half = temp.apply(.5, .5, .5, Operation.MULTIPLY);
		Location location = wheel1.apply(half.x, half.y, half.z, Operation.ADD);
		return location;
	}

	private void update() {
		location = calc_location(front_axis, back_axis);
		yaw = calc_rotation(front_axis, back_axis);
	}

	public void do_move() {
		Move m = track1.compute_move(offset, speed, D);
		Move m2 = track2.compute_move(offset2, speed, D2);

		while (m.remainder > 0) {
			// print(r)
			Exit i = track1.getExit(m.newlocation);
			// print("%s "%i)
			if (i != null) {

				ExitableTrack track_new = i.connection.owner;
				D = i.connection.owner.getD(i.connection.index);

				track1 = track_new;

				m = track1.compute_move(0, m.remainder, D);
			} else {
				speed = 0;
				break;
			}
		}

		while (m2.remainder > 0) {
			Exit i = track2.getExit(m2.newlocation);
			// print("%s" % i)
			if (i != null) {

				ExitableTrack track_new = i.connection.owner;
				D = i.connection.owner.getD(i.connection.index);

				track2 = track_new;

				m2 = track2.compute_move(0, m2.remainder, D2);
			} else {
				speed = 0;
				break;
			}
		}
		front_axis = m.newlocation;
		offset = m.newoffset;
		back_axis = m2.newlocation;
		offset2 = m.newoffset;
		update();
	}

}
