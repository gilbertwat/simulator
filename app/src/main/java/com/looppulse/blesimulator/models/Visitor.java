package com.looppulse.blesimulator.models;

import android.content.Context;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import com.looppulse.blesimulator.api.Event;

import java.util.List;
import java.util.UUID;

/**
 * Created by Gilbert on 7/22/2014.
 */
public class Visitor {
    //TODO For the bonus AI part, the attribute of the visitor like casual, linger, speedo, can be put here
    public static class VisitorEnterSignal {
        public final Beacon beacon;
        public final Visitor visitor;
        public int count;

        public VisitorEnterSignal(Visitor visitor, Beacon beacon) {
            this.beacon = beacon;
            this.visitor = visitor;
            count = enterRegionSignalCount;
        }
    }

    public static final String KEY_UUID = "uuid";
    public static final String KEY_NAME = "name";
    public static final String KEY_POS_X = "posX";
    public static final String KEY_POS_Y = "posY";

    public final UUID uuid;
    public final String name;
    public int posX;
    public int posY;
    public Boolean isAlive;


    public static final int enterRegionSignalCount = 5;

    public List<VisitorEnterSignal> beaconsContains = Lists.newArrayList();

    public Visitor(UUID uuid, String name, int initPosX, int initPosY) {
        this.uuid = uuid;
        //Is fun allowed?
        this.name = name;
        this.posX = initPosX;
        this.posY = initPosY;
        this.isAlive = Boolean.FALSE;
    }

    public void isIn(Context context, Beacon b) {
        //TODO not efficient
        for (VisitorEnterSignal v : beaconsContains) {
            if (v.beacon.equals(b)) {
                Event.didRangeBeacons(context, v);
            }
        }
    }

    public void exit(Context context, Beacon b) {
        for (VisitorEnterSignal v : beaconsContains) {
            if (v.beacon.equals(b)) {
                beaconsContains.remove(v);
                Event.didExitRegion(context, v);
                return;
            }
        }
    }
}
