/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gynt.openrail.java.core.basic;

import com.gynt.openrail.java.core.Location;
import com.gynt.openrail.java.core.Position;
import com.gynt.openrail.java.core.Track;
import com.gynt.openrail.java.core.TrackPath;
import com.gynt.openrail.java.core.TrackState;
import com.gynt.openrail.java.core.Train;

/**
 *
 * @author frank
 */
public class BasicTrain implements Train {

    private double speed;
    private TrackState[] trackstates;

    public BasicTrain() {
        trackstates = new TrackState[2];
        trackstates[0] = new BasicTrackState();
        trackstates[1] = new BasicTrackState();
    }

    @Override
    public double getSpeed() {
        return speed;
    }

    @Override
    public void setSpeed(double speed) {
        this.speed = speed;
    }

    @Override
    public TrackState[] getTrackStates() {
        return trackstates;
    }
    
    private class P {
        private double displacement;
        private double offset;
        private TrackPath trackpath;
        private Track track;
        private P(Track track, double offset, TrackPath trackpath, double displacement) {
            this.track=track;
            this.offset=offset;
            this.trackpath=trackpath;
            this.displacement=displacement;
        }
    }
    
    private P computeDisplacement(TrackState t, double speed) {
        double displac0 = 0.0;
        if (t.getOffset() + speed > t.getTrackPath().getPathLength()) {
            double remainder = t.getOffset() + speed - t.getTrackPath().getPathLength();
            
            Track oldtrack = t.getTrack();
            Track newtrack = t.getTrackPath().getTrackAt(t.getTrackPath().getPathLength());
            if (newtrack == null) {
                displac0 = t.getTrackPath().getPathLength() - t.getOffset();
                return new P(oldtrack, t.getTrackPath().getPathLength(), t.getTrackPath(), displac0);
            } else {
                TrackPath tp = newtrack.getTrackPath(newtrack.getConnectedEndpoint(oldtrack));
                remainder -= tp.getPathLength();
                while (remainder > 0) {
                    oldtrack = newtrack;
                    newtrack = tp.getTrackAt(tp.getPathLength());
                    if (newtrack == null) {
                        break;
                    }
                    tp = newtrack.getTrackPath(newtrack.getConnectedEndpoint(oldtrack));
                    remainder -= tp.getPathLength();
                }
                if(remainder > 0) {
                    displac0=speed-remainder;
                    return new P(oldtrack, tp.getPathLength(), tp, displac0);
                } else {
                    displac0=speed;
                    return new P(newtrack, remainder+tp.getPathLength(), tp, displac0);
                }
            }
        } else if (t.getOffset() + speed < 0) {
            double remainder = -1*(t.getOffset() + speed);
            
            Track oldtrack = t.getTrack();
            Track newtrack = t.getTrackPath().getTrackAt(0);
            if (newtrack == null) {
                displac0 = t.getOffset();
                return new P(oldtrack, t.getTrackPath().getPathLength(), t.getTrackPath(), displac0);
            } else {
                TrackPath tp = newtrack.getTrackPath(newtrack.getConnectedEndpoint(oldtrack));
                remainder -= tp.getPathLength();
                while (remainder > 0) {
                    oldtrack = newtrack;
                    newtrack = tp.getTrackAt(tp.getPathLength());
                    if (newtrack == null) {
                        break;
                    }
                    tp = newtrack.getTrackPath(newtrack.getConnectedEndpoint(oldtrack));
                    remainder -= tp.getPathLength();
                }
                if(remainder > 0) {
                    displac0=speed-remainder;
                    return new P(oldtrack, tp.getPathLength(), tp, displac0);
                } else {
                    displac0=speed;
                    return new P(newtrack, remainder+tp.getPathLength(), tp, displac0);
                }
            }
        } else {
            displac0 = speed;
            return new P(t.getTrack(), t.getOffset()+speed, t.getTrackPath(), displac0);
        }
    }
    
    private void commitDisplacement(P d1, P d2) {
        trackstates[0].setLocation(d1.track, d1.track.getEndpoint(d1.trackpath), d1.offset);
        trackstates[1].setLocation(d2.track, d2.track.getEndpoint(d2.trackpath), d2.offset);
    }
    
    private void commitDisplacement(double displacement) {
        commitDisplacement(trackstates[0], displacement);
        commitDisplacement(trackstates[1], displacement);

    }
    
    private void commitDisplacement(TrackState t, double displacement) {
        if (t.getOffset() + displacement > t.getTrackPath().getPathLength()) {
            Track newtrack = t.getTrackPath().getTrackAt(t.getTrackPath().getPathLength());
            if (newtrack == null) {
                t.setOffset(t.getTrackPath().getPathLength());
            } else {
                t.setLocation(newtrack, newtrack.getConnectedEndpoint(t.getTrack()), t.getOffset() + displacement - t.getTrackPath().getPathLength());
            }
        } else if (t.getOffset() + displacement < 0) {
            Track newtrack = t.getTrackPath().getTrackAt(0);
            if (newtrack == null) {
                t.setOffset(0);
            } else {
                t.setLocation(newtrack, newtrack.getConnectedEndpoint(t.getTrack()), -1 * (t.getOffset() + displacement));
            }
        } else {
            t.setOffset(t.getOffset() + displacement);
        }
    }

    @Override
    public void move() {

        P d1 = computeDisplacement(trackstates[0], speed);
                P d2 = computeDisplacement(trackstates[1], speed);

        if(d1.displacement<d2.displacement) {
            d2=computeDisplacement(trackstates[1], d1.displacement);
        } else {
            d1=computeDisplacement(trackstates[0], d2.displacement);
        }
        
        commitDisplacement(d1,d2);
        
    }

    @Override
    public Position computePosition() {
        Location l0 = trackstates[0].computeLocation();
        Location l1 = trackstates[1].computeLocation();
        return new Position(new Location((l0.x-l1.x)*0.5, (l0.y-l1.y)*0.5, (l0.z-l1.z)*0.5), Math.atan((l0.z-l1.z)/(l0.x-l1.x)), Math.atan(l0.y/l1.y));
    }

}
