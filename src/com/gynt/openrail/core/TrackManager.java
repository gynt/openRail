package com.gynt.openrail.core;

import com.gynt.openrail.core.Location.Operation;

public class TrackManager {

	private Board board;
	
	public void force_assemble(Track track_wrapper1, int exit1, Track track_wrapper2, int exit2) {
	    double offset=0;
	    if(track_wrapper1 instanceof StraightTrack && track_wrapper2 instanceof StraightTrack) {
	    	if(exit1==exit2) {
	    		offset=180;
	    	}
	    	track_wrapper2.yaw=track_wrapper1.yaw+offset;
	    	Location e2 = track_wrapper2.getExits()[exit2];
	    	Location d = track_wrapper1.getExits()[exit1].apply(e2.x, e2.y, e2.z, Operation.SUBTRACT);
	    	track_wrapper2.setLocation(d.apply(track_wrapper2.getLocation().x, track_wrapper2.getLocation().y, track_wrapper2.getLocation().z, Operation.ADD));
	    	board.setTrackExit(track_wrapper1, exit1, track_wrapper2, exit2);
	    	board.setTrackExit(track_wrapper2, exit2, track_wrapper1, exit1);
	    }
	    else if track_wrapper1.track.__class__.__name__=="CurveTrack" and track_wrapper2.track.__class__.__name__=="StraightTrack":
	        if exit1 > 0:
	            offset=90
	        track_wrapper2.track.set_yaw(track_wrapper1.track.get_yaw(exit1)+offset, exit2)
	        d=track_wrapper1.track._exits[exit1].apply(func=operator.sub, point=track_wrapper2.track._exits[exit2])
	        track_wrapper2.track.set_location(d.apply(func=operator.add, point=track_wrapper2.track.location))
	        track_wrapper1.set_exit_point(exit1, track_wrapper2, exit2)
	        track_wrapper2.set_exit_point(exit2, track_wrapper1, exit1)
	    elif track_wrapper1.track.__class__.__name__=="StraightTrack" and track_wrapper2.track.__class__.__name__=="CurveTrack":
	        if exit2 > 0:
	            offset=180-track_wrapper2.track.angle-90
	        else:
	            offset=-90
	        track_wrapper2.track.set_yaw(track_wrapper1.track.get_yaw(exit1)+offset, exit2)
	        d=track_wrapper1.track._exits[exit1].apply(func=operator.sub, point=track_wrapper2.track._exits[exit2])
	        track_wrapper2.track.set_location(d.apply(func=operator.add, point=track_wrapper2.track.location))
	        track_wrapper1.set_exit_point(exit1, track_wrapper2, exit2)
	        track_wrapper2.set_exit_point(exit2, track_wrapper1, exit1)        
	    elif track_wrapper1.track.__class__.__name__=="CurveTrack" and track_wrapper2.track.__class__.__name__=="CurveTrack":
	        if exit1==exit2:
	            offset=180
	        track_wrapper2.track.set_yaw(track_wrapper1.track.get_yaw(exit1)+offset, exit2)
	        d=track_wrapper1.track._exits[exit1].apply(func=operator.sub, point=track_wrapper2.track._exits[exit2])
	        track_wrapper2.track.set_location(d.apply(func=operator.add, point=track_wrapper2.track.location))
	        track_wrapper1.set_exit_point(exit1, track_wrapper2, exit2)
	        track_wrapper2.set_exit_point(exit2, track_wrapper1, exit1)        
	        
	} 

	def assembly_allowed(track_wrapper1, exit1, track_wrapper2, exit2):
	    return assembly_allowed_rot(self, track_wrapper1, exit1, track_wrapper2, exit2) and assembly_allowed_pos(self, track_wrapper1, exit1, track_wrapper2, exit2)

	def assembly_allowed_pos(track_wrapper1, exit1, track_wrapper2, exit2):
	    comp=track_wrapper1.track._exits[exit1].apply(point=track_wrapper2.track._exits[exit2], func=operator.eq)
	    return comp.x and comp.y and comp.z

	def assembly_allowed_rot(track_wrapper1, exit1, track_wrapper2, exit2):
	    if track_wrapper2.track.type=="StraightTrack":
	        return track_wrapper1.track.yaw-track_wrapper2.track.yaw%180==0
	    if track_wrapper2.track.type=="CurveTrack":
	         return track_wrapper1.track.yaw-track_wrapper2.track.yaw%180==0 or track_wrapper1.track.yaw==track_wrapper2.track.yaw+track_wrapper2.track.angle
	                
	def assemble(track_wrapper1, exit1, track_wrapper2, exit2):
	    if assembly_allowed(track_wrapper1, exit1, track_wrapper2, exit2):
	        track_wrapper1.set_exit_point(exit1, track_wrapper2, exit2)
	        track_wrapper2.set_exit_point(exit2, track_wrapper1, exit1)

	
}
