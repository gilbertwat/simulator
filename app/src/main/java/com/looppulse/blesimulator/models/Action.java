package com.looppulse.blesimulator.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreType;
import com.google.common.collect.Maps;

import java.util.Map;
import java.util.UUID;

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
    public final UUID iVisitor;
    @JsonIgnore
    public final Visitor visitor;

    public static final String KEY_TIME = "time";
    public static final String KEY_ACTION = "action";
    public static final String KEY_I_VISITOR = "iVisitor";

    public Action(Visitor v, int time, Type action) {
        this.iVisitor = v.uuid;
        this.visitor = v;
        this.time = time;
        this.action = action;
    }

    public Action(UUID v, int time, Type action) {
        this.iVisitor = v;
        this.visitor = null;
        this.time = time;
        this.action = action;
    }

}
