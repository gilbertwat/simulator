package com.looppulse.blesimulator.api;

import com.looppulse.blesimulator.models.Visitor;

/**
 * Created by Gilbert on 7/22/2014.
 */
public class Event {

    public enum Type {
        didRangeBeacons,
        didExitRegion;
    }

    public static void didRangeBeacons(Visitor.VisitorEnterSignal v) {
        //firebaseAPI
    }

    public static void didExitRegion(Visitor.VisitorEnterSignal v) {
        //firebaseAPI
    }
}
