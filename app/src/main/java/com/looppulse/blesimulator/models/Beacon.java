package com.looppulse.blesimulator.models;

import java.util.UUID;

/**
 * Created by Gilbert on 7/22/2014.
 */
public class Beacon {
    public final UUID uuid;
    public final Short major;
    public final Short minor;
    public final FloorPlan areaCovered;

    //posX and posY can be sth like Position(int, int)

    /**
     *
     * @param uuid
     * @param major
     * @param minor
     * @param range
     * @param posX 0 based
     * @param posY 0 based
     */
    public Beacon(UUID uuid, Short major, Short minor, int range, int posX, int posY) {
        this.uuid = uuid;
        this.major = major;
        this.minor = minor;
        //TODO some validation
        this.areaCovered = new FloorPlan(range, posX, posY);
    }
}
