package com.gynt.openrail.core;

public class SwitchTrack extends Track {

	private Location[] exits;

	SwitchTrack(Location location, double rotation) {
		super(location, rotation);
	}


	@Override
	public Location[] getExits() {
		return exits;
	}

	@Override
	public void setLocation(Location location) {

	}

	@Override
	public Location getLocation() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Move compute_move(double offset, double distance, boolean D) {
		// TODO Auto-generated method stub
		return null;
	}

}
