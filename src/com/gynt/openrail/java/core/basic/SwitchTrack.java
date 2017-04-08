package com.gynt.openrail.java.core.basic;

import com.gynt.openrail.java.core.Location;
import com.gynt.openrail.java.core.Position;
import com.gynt.openrail.java.core.Track;
import com.gynt.openrail.java.core.TrackPath;
import com.gynt.openrail.java.core.utils.TrackComputation;

public class SwitchTrack implements Track {

	private Track[] connection_map;
	
	private TrackPath[] paths;
	private boolean curved;
	
	private double straightlength;
	private double curvelength;
	
	private Position position;

	public SwitchTrack() {
		connection_map = new Track[3];
		/** There are two paths for 1 endpoint. */
		paths = new TrackPath[4];
		paths[0] = new TrackPath() {

			@Override
			public Location translateOffset(double offset) {
				double doffset = offset - (straightlength * 0.5);
				if (doffset == 0)
					return SwitchTrack.this.getPosition().location;
				if (doffset < 0)
					return TrackComputation.computeStraight(position, 0, doffset * -1);
				if (doffset > 0)
					return TrackComputation.computeStraight(position, 1, doffset);
				throw new RuntimeException(String.format("Invalid doffset {} based on offset {}.", doffset, offset));
			}

			@Override
			public Track getTrackAt(int endpoint) {
				return getTrack().getTrackAt(endpoint);
			}

			@Override
			public Track getTrackAt(double offset) {
				if (offset == 0)
					return SwitchTrack.this.connection_map[0];
				if (offset == straightlength)
					return SwitchTrack.this.connection_map[1];
				throw new RuntimeException("Invalid offset: " + offset);
			}

			@Override
			public Track getTrack() {
				return SwitchTrack.this;
			}

			@Override
			public double getPathLength() {
				return straightlength;
			}

		};
		paths[1] = new TrackPath() {
			@Override
			public Location translateOffset(double offset) {
				double doffset = offset - (straightlength * 0.5);
				if (doffset == 0)
					return SwitchTrack.this.getPosition().location;
				if (doffset < 0)
					return TrackComputation.computeStraight(position, 1, doffset * -1);
				if (doffset > 0)
					return TrackComputation.computeStraight(position, 0, doffset);
				throw new RuntimeException(String.format("Invalid doffset {} based on offset {}.", doffset, offset));
			}

			@Override
			public Track getTrackAt(int endpoint) {
				return getTrack().getTrackAt(endpoint);
			}

			@Override
			public Track getTrackAt(double offset) {
				if (offset == 0)
					return SwitchTrack.this.connection_map[1];
				if (offset == straightlength)
					return SwitchTrack.this.connection_map[0];
				throw new RuntimeException("Invalid offset: " + offset);
			}

			@Override
			public Track getTrack() {
				return SwitchTrack.this;
			}

			@Override
			public double getPathLength() {
				return straightlength;
			}
		};
	}

	@Override
	public Track getTrackAt(int endpoint) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Track connectTrack(int endpoint, Track track, int endpoint2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void disconnectTrack(int endpoint, Track track) {
		// TODO Auto-generated method stub

	}

	@Override
	public TrackPath getTrackPath(int endpoint) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setConnector(int endpoint, Track track) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getConnectedEndpoint(Track track) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setPosition(Position l) {
		// TODO Auto-generated method stub

	}

	@Override
	public Position getPosition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Location getLocation(int endpoint) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void setState(boolean curved) {
		
	}
	
	public boolean getState() {
		return curved;
	}

        //TODO: muy importante!
    @Override
    public int getEndpoint(TrackPath tp) {
        if(tp==paths[0]) return 0;
        if(tp==paths[1]) return 1;
        if(tp==paths[2]) return 0;
        if(tp==paths[3]) return 2;
        return -1;
    }

}
