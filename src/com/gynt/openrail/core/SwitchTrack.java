package com.gynt.openrail.core;

public class SwitchTrack extends ExitableTrack {


	SwitchTrack(Location location, double rotation) {
		super(location, rotation, 3);
	}


	@Override
	public Move compute_move(double offset, double distance, boolean D) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public double getYaw(int exitindex) {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public void setYaw(double yaw, int exitindex) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void setYaw(double yaw) {
		// TODO Auto-generated method stub
		
	}


	@Override
	protected void onNewLocation() {
		// TODO Auto-generated method stub
		
	}

}
