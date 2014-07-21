package com.looppulse.blesimulator.models;

/**
 * Created by Gilbert on 7/22/2014.
 */
public class Event {

    public Event(Integer sequence, Visitor visitor, Beacon beacon, Type event) {
        this.sequence = sequence;
        this.visitor = visitor;
        this.beacon = beacon;
        this.event = event;
    }

    public enum Type {
        didRangeBeacons,
        didExitRegion;
    }

    public final Integer sequence;
    public final Visitor visitor;
    public final Beacon beacon;
    public final Event.Type event;
}
