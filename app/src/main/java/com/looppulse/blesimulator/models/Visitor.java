package com.looppulse.blesimulator.models;

import com.google.common.collect.Lists;
import com.looppulse.blesimulator.api.Event;

import java.util.List;
import java.util.UUID;

/**
 * Created by Gilbert on 7/22/2014.
 */
public class Visitor {
    //For the bonus AI part, the attribute of the visitor like casual, linger, speedo, can be put here
    public class VisitorEnterSignal {
        public final Beacon beacon;
        public int count;

        public VisitorEnterSignal(Beacon beacon) {
            this.beacon = beacon;
            count = enterRegionSignalCount;
        }
    }

    public final UUID uuid;
    public final String name;
    public int posX;
    public int posY;
    public final int enterRegionSignalCount = 5;
    public List<VisitorEnterSignal> beaconsContains = Lists.newArrayList();

    public Visitor(UUID uuid, String name, int initPosX, int initPosY) {
        this.uuid = uuid;
        //Is fun allowed?
        this.name = name;
        this.posX = initPosX;
        this.posY = initPosY;
    }

    public void refresh() {
        //may call once per second
        //loop through beaconsContains and fire Event.didRangeBeacons()
    }

    public void enter(Beacon b) {
        //add b to beaconsContains
        final VisitorEnterSignal v = new VisitorEnterSignal(b);
        beaconsContains.add(v);
        Event.didRangeBeacons(v);
    }

    public void exit(Beacon b) {
        //remove b from
    }
}
