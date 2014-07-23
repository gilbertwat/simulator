package com.looppulse.blesimulator.models;

/**
 * Created by Gilbert on 7/23/2014.
 */
public class Action {

    public enum Type {
        SPAWN,
        DESPAWN,
        GO_LEFT,
        GO_RIGHT,
        GO_UP,
        GO_DOWN
    }

    //to simplify time is in 1s frame
    public final int time;
    public final Type action;
    public final Visitor visitor;

    public Action(Visitor v, int time, Type action) {
        this.visitor = v;
        this.time = time;
        this.action = action;
    }

}
