package com.gynt.openrail.core;

import java.util.HashMap;

public class Board {
	
	public static class TrackExit {
		public Track track;
		public int exit;
		public TrackExit(Track t1, int exit1) {
			this.track = t1;
			this.exit = exit1;
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + exit;
			result = prime * result + ((track == null) ? 0 : track.hashCode());
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			TrackExit other = (TrackExit) obj;
			if (exit != other.exit)
				return false;
			if (track == null) {
				if (other.track != null)
					return false;
			} else if (!track.equals(other.track))
				return false;
			return true;
		}
	}
	
	private HashMap<TrackExit,TrackExit> meta;
	
	public TrackExit getTrackExit(TrackExit t) {
		return meta.get(t);
	}
	
	public void setTrackExit(TrackExit t1, TrackExit t2) {
		meta.put(t1, t2);
		meta.put(t2, t1);
	}
	
	public void setTrackExit(Track t1, int exit1, Track t2, int exit2) {
		TrackExit tt1=new TrackExit(t1, exit1);
		TrackExit tt2 =new TrackExit(t2, exit2);
		setTrackExit(tt1, tt2);
	}

	public Track getTrack(Location location) {
		return null;
	}
	
}
