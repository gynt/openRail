package com.gynt.openrail.java.core;

public class StraightTrack implements Track {
	
	private Track[] connection_map;
	private TrackPath path;
	private int length;
	private Position position;

	public StraightTrack() {
		connection_map = new Track[2];
		length = 16;
		path = new TrackPath() {
			
			@Override
			public Location translateOffset(double offset) {
				if(offset < 0) {
					offset=length-offset;
				}
				double doffset = offset-(length*0.5);
				if(doffset==0) return StraightTrack.this.getPosition().location;
				if(doffset < 0) return StraightTrack.this.computeLocation(0, doffset*-1);
				if(doffset > 0) return StraightTrack.this.computeLocation(1, doffset);
				throw new RuntimeException(String.format("Invalid doffset {} based on offset {}.", doffset, offset));
			}
			
			@Override
			public Track getTrackAt(int endpoint) {
				return getTrack().getTrackAt(endpoint);
			}
			
			@Override
			public Track getTrackAt(double offset) {
				if(offset==length) return StraightTrack.this.connection_map[1];
				if(offset==-1*length) return StraightTrack.this.connection_map[0];
				throw new RuntimeException("Invalid offset: " + offset);
			}
			
			@Override
			public Track getTrack() {
				return StraightTrack.this;
			}
			
			@Override
			public double getPathLength() {
				return length;
			}

		};
	}

	@Override
	public Track getTrackAt(int endpoint) {
		return connection_map[endpoint];
	}

	@Override
	public Track connectTrack(int endpoint, Track track, int endpoint2) {
		setConnector(endpoint, track);
		track.setConnector(endpoint2, this);
		return track;
	}

	@Override
	public void disconnectTrack(int endpoint, Track track) {
		if(connection_map[endpoint]!=null) connection_map[endpoint].setConnector(getConnectedEndpoint(this), null);
		setConnector(endpoint, null);
	}

	@Override
	public TrackPath getTrackPath(int endpoint) {
		return path;
	}

	@Override
	public void setConnector(int endpoint, Track track) {
		connection_map[endpoint]=track;
	}

	@Override
	public int getConnectedEndpoint(Track track) {
		if(track==connection_map[0]) return 0;
		if(track==connection_map[1]) return 1;
		return -1;
	}

	@Override
	public void setPosition(Position l) {
		position = l;
	}

	@Override
	public Position getPosition() {
		return position;
	}
	
	private Location computeLocation(int endpoint, double armlength) {
		if(endpoint==0) {
			double x = position.location.x+(Math.cos(position.pitch-Math.toRadians(180))*armlength);
			double z = position.location.z+(Math.sin(position.pitch-Math.toRadians(180))*armlength);
			double y = position.location.y+(Math.sin(position.yaw-Math.toRadians(180))*armlength);
			return new Location(x,y,z);
		} else if(endpoint==1) {
			double x = position.location.x+(Math.cos(position.pitch)*armlength);
			double z = position.location.z+(Math.sin(position.pitch)*armlength);
			double y = position.location.y+(Math.sin(position.yaw)*armlength);
			return new Location(x,y,z);
		}
		throw new RuntimeException("Invalid endpoint: " + endpoint);
	}

	@Override
	public Location getLocation(int endpoint) {
		return computeLocation(endpoint, length*0.5);
	}

}
