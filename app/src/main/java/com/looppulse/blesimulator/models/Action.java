package com.looppulse.blesimulator.models;

/**
 * Created by Gilbert on 7/23/2014.
 */
public class Action {

    public enum Type {
        SPAWN,
        GO_LEFT,
        GO_RIGHT,
        GO_UP,
        GO_DOWN
    }

    //to simplify time is in 1s frame
    public final int time;
    public final Type action;

    public Action(int time, Type action) {
        this.time = time;
        this.action = action;
    }

}
