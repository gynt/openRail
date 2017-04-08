/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gynt.openrail.java.core.basic;

import com.gynt.openrail.java.core.Location;
import com.gynt.openrail.java.core.Track;
import com.gynt.openrail.java.core.TrackPath;
import com.gynt.openrail.java.core.TrackState;

/**
 *
 * @author frank
 */
public class BasicTrackState implements TrackState {

    private double offset;
    private Track track;
    private int endpoint;

    @Override
    public TrackPath getTrackPath() {
        return track.getTrackPath(endpoint);
    }

    @Override
    public double getOffset() {
        return offset;
    }

    @Override
    public Location computeLocation() {
        return track.getTrackPath(endpoint).translateOffset(offset);
    }

    @Override
    public int getEndPoint() {
        return this.endpoint;
    }

    @Override
    public void setLocation(Track track, int endpoint, double offset) {
        this.track=track;
        this.endpoint=endpoint;
        this.offset=offset;
    }

    @Override
    public Track getTrack() {
        return track;
    }

    @Override
    public void setOffset(double offset) {
        this.offset=offset;
    }

}
