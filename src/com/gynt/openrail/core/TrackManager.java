package com.gynt.openrail.core;

import com.gynt.openrail.core.ExitableTrack.Exit;
import com.gynt.openrail.core.Location.Operation;

public class TrackManager {

	@Deprecated
	public static void force_assemble(ExitableTrack track_wrapper1, int exit1, ExitableTrack track_wrapper2, int exit2) {
		double offset = 0;
		if (track_wrapper1 instanceof StraightTrack && track_wrapper2 instanceof StraightTrack) {
			if (exit1 == exit2) {
				offset = 180;
			}
			track_wrapper2.setYaw(track_wrapper1.getYaw(exit1) + offset, exit2);
			Location d = track_wrapper1.getExits()[exit1].location.apply(track_wrapper2.exits[exit2].location,
					Operation.SUBTRACT);
			track_wrapper2.setLocation(d.apply(track_wrapper2.getLocation(), Operation.ADD));
			track_wrapper1.connectExit(exit1, track_wrapper2.exits[exit2]);// board.setTrackExit(track_wrapper1,
																			// exit1,
																			// track_wrapper2,
																			// exit2);
			// Automagically: track_wrapper2.connectExit(exit2,
			// track_wrapper1.exits[exit1]);//board.setTrackExit(track_wrapper2,
			// exit2, track_wrapper1, exit1);
		} else if (track_wrapper1 instanceof CurveTrack && track_wrapper2 instanceof StraightTrack) {
			if (exit1 > 0) {
				offset = 90;
			}
			track_wrapper2.setYaw(track_wrapper1.getYaw(exit1) + offset, exit2);
			Location d = track_wrapper1.exits[exit1].location.apply(track_wrapper2.exits[exit2].location,
					Operation.SUBTRACT);
			track_wrapper2.setLocation(d.apply(track_wrapper2.getLocation(), Operation.ADD));
			track_wrapper1.connectExit(exit1, track_wrapper2.exits[exit2]);
		} else if (track_wrapper1 instanceof StraightTrack && track_wrapper2 instanceof CurveTrack) {
			CurveTrack tw2 = (CurveTrack) track_wrapper2;
			if (exit2 > 0) {
				offset = 180 - tw2.angle - 90;
			} else {
				offset = -90;
			}
			track_wrapper2.setYaw(track_wrapper1.getYaw(exit1) + offset, exit2);
			Location d = track_wrapper1.exits[exit1].location.apply(track_wrapper2.exits[exit2].location,
					Operation.SUBTRACT);
			track_wrapper2.setLocation(d.apply(track_wrapper2.location, Operation.ADD));
			track_wrapper1.connectExit(exit1, track_wrapper2.exits[exit2]);
		} else if (track_wrapper1 instanceof CurveTrack && track_wrapper2 instanceof CurveTrack) {
			if (exit1 == exit2) {
				offset = 180;
			}
			track_wrapper2.setYaw(track_wrapper1.getYaw(exit1) + offset, exit2);
			Location d = track_wrapper1.exits[exit1].location.apply(track_wrapper2.exits[exit2].location,
					Operation.SUBTRACT);
			track_wrapper2.setLocation(d.apply(track_wrapper2.location, Operation.ADD));
			track_wrapper1.connectExit(exit1, track_wrapper2.exits[exit2]);
		}
	}
	
	public static void assemble(ExitableTrack track_wrapper1, int exit1, ExitableTrack track_wrapper2, int exit2) {
		
		Exit e1 = track_wrapper1.exits[exit1];
		Exit e2 = track_wrapper2.exits[exit2];
		
		assemble(e1, e2);
		
	}
	
	public static void assemble(Exit e1, Exit e2) {
		e2.owner.setYaw(e1.yaw+180, e2.index);
		
		e2.owner.setLocation(e1.location.apply(e2.location, Operation.SUBTRACT).apply(e2.owner.location, Operation.ADD));
	}

}
