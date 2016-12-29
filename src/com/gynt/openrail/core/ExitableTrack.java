package com.gynt.openrail.core;

import com.gynt.openrail.core.Location.Operation;

public abstract class ExitableTrack extends Track {

	public static class Exit {
		public Location location;
		public double yaw;
		public final int index;
		public Exit connection;
		public final ExitableTrack owner;

		public Exit(ExitableTrack owner, int index) {
			this.owner = owner;
			this.index = index;
		}

		public boolean canConnect(Exit exit2) {
			return canConnectRotation(exit2) && canConnectLocation(exit2);
		}

		public boolean canConnectRotation(Exit exit2) {
			if (yaw % 180 == exit2.yaw % 180)
				return true;
			return false;
		}

		public boolean canConnectLocation(Exit exit2) {
			return location.isEqual(exit2.location);
		}
	}

	protected Exit[] exits;

	ExitableTrack(Location location, double yaw, int exitamount) {
		super(location, yaw);
		this.exits = new Exit[exitamount];
		for (int i = 0; i < exits.length; i++) {
			this.exits[i] = new Exit(this, i);
		}
	}

	public Exit getExit(Location newlocation) {
		for (Exit e : exits) {
			if (e.location.isEqual(newlocation))
				return e;
		}
		return null;
	}

	@Deprecated
	public int get_exit_index(Location point) {
		for (int i = 0; i < getExits().length; i++) {
			Exit exit = getExits()[i];
			Location p = point.apply(exit.location.x, exit.location.y, exit.location.z, Operation.EQUALS);
			if (p.x == 0 && p.y == 0 && p.z == 0) {
				return i;
			}
		}
		return -1;
	}

	protected void updateLocations(Location... newlocations) {
		for (int i = 0; i < newlocations.length; i++) {
			exits[i].location = newlocations[i];
		}
	}

	public boolean getD(int exit) {
		return exit == 0;
	}

	public Exit[] getExits() {
		return exits;
	}

	public void connectExit(int index, Exit exit) {
		connectExit(exits[index], exit);
	}

	public static void connectExit(Exit exit1, Exit exit2) {
		exit1.connection = exit2;
		exit2.connection = exit1;
	}

	public void disconnectExit(int index) {
		disconnectExit(exits[index]);
	}

	public static void disconnectExit(Exit exit) {
		exit.connection.connection = null;
		exit.connection = null;

	}

}
