package com.looppulse.blesimulator.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.google.common.eventbus.AllowConcurrentEvents;

import java.util.UUID;

/**
 * Created by Gilbert on 7/22/2014.
 */
public class Beacon {

    public static final String KEY_UUID = "uuid";
    public static final String KEY_MAJOR = "major";
    public static final String KEY_MINOR = "minor";
    public static final String KEY_AREA_COVERED = "areaCovered";

    public final UUID uuid;
    public final Short major;
    public final Short minor;
    public final FloorPlan areaCovered;

    //posX and posY can be sth like Position(int, int)

    /**
     * Width and Height is not neccessary, but to avoid circular dependency
     * @param uuid
     * @param major
     * @param minor
     * @param range
     * @param posX 0 based
     * @param posY 0 based
     */
    public Beacon(UUID uuid, Short major, Short minor, int width, int height, int range, int posX, int posY) {
        this.uuid = uuid;
        this.major = major;
        this.minor = minor;
        //TODO some validation
        this.areaCovered = new FloorPlan(width, height, range, posX, posY);
    }

    public Beacon(UUID uuid, Short major, Short minor, FloorPlan fp) {
        this.uuid = uuid;
        this.major = major;
        this.minor = minor;
        //TODO some validation
        this.areaCovered = fp;
    }
}
