package com.looppulse.blesimulator.models;

/**
 * Created by Gilbert on 7/22/2014.
 */
public class Beacon {
    public final String uuid;
    public final Short major;
    public final Short minor;

    public Beacon(String uuid, Short major, Short minor) {
        this.uuid = uuid;
        this.major = major;
        this.minor = minor;
    }
}
