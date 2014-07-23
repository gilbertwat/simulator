package com.looppulse.blesimulator.models;

import java.util.UUID;

/**
 * Created by Gilbert on 7/22/2014.
 */
public class Visitor {
    //For the bonus AI part, the attribute of the visitor like casual, linger, speedo, can be put here

    public final UUID uuid;
    public final String name;
    public int posX;
    public int posY;

    public Visitor(UUID uuid, String name, int initPosX, int initPosY) {
        this.uuid = uuid;
        //Is fun allowed?
        this.name = name;
        this.posX = initPosX;
        this.posY = initPosY;
    }
}
