package com.looppulse.blesimulator.models;

import android.content.Context;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.UUID;

/**
 * Created by Gilbert on 7/23/2014.
 */
public class World {

    @JsonIgnore
    public final Context context;

    public final Integer width;
    public final Integer height;

    public final List<Action> actions;

    public final List<Beacon> beacons;
    public final List<Visitor> visitors;

    public World(final Context context, final List<Action> actions, final List<Beacon> beacons, final List<Visitor> visitors) {
        this.context = context;
        this.width = 0; //not important
        this.height = 0; //not important
        this.actions = actions;
        this.beacons = beacons;
        this.visitors = visitors;
    }

    public World(Context context) {
        this.context = context;
        this.width = 0;
        this.height = 0;
        this.actions = null;
        this.beacons = null;
        this.visitors = null;
    }

    public World(final Context context, final Integer width, final Integer height, final List<Action> actions, final List<Beacon> beacons, final List<Visitor> visitors) {
        this.context = context;
        this.width = width;
        this.height = height;
        this.actions = actions;
        this.beacons = beacons;
        this.visitors = visitors;
    }

    public static World getSampleWorld(Context context) {
        final UUID uuid = UUID.randomUUID();
        final Beacon maleShoe = new Beacon(uuid, (short)0, (short)0, 5, 5, 2, 4, 0);
        final Beacon femaleShoe = new Beacon(uuid, (short)0, (short)1, 5, 5, 2, 4, 4);
        final Beacon maleClothes = new Beacon(uuid, (short)0, (short)2, 5, 5, 2, 0, 2);
        final Beacon femaleClothes = new Beacon(uuid, (short)0, (short)3, 5, 5, 2, 4, 2);
        final Beacon tryingArea = new Beacon(uuid, (short)1, (short)0, 5, 5, 1, 0, 0);
        final Beacon cashier = new Beacon(uuid, (short)1, (short)1, 5, 5, 1, 4, 0);
        final List<Beacon> beacons = Lists.newArrayList();
        beacons.add(maleShoe);
        beacons.add(femaleShoe);
        beacons.add(maleClothes);
        beacons.add(femaleClothes);
        beacons.add(tryingArea);
        beacons.add(cashier);

        final Visitor hannibal = new Visitor(UUID.randomUUID(), "Hannibal", 2, 4);
        final Visitor will = new Visitor(UUID.randomUUID(), "Will", 3, 4);
        final Visitor alana = new Visitor(UUID.randomUUID(), "Alana", 1, 4);
        final List<Visitor> visitors = Lists.newArrayList();
        visitors.add(hannibal);
        visitors.add(will);
        visitors.add(alana);

        List<Action> actions = Lists.newArrayList();
        actions.add(new Action(hannibal, 0, Action.Type.SPAWN));
        actions.add(new Action(hannibal, 1, Action.Type.GO_LEFT));
        actions.add(new Action(hannibal, 2, Action.Type.GO_UP));
        actions.add(new Action(hannibal, 10, Action.Type.GO_UP));
        actions.add(new Action(hannibal, 11, Action.Type.GO_UP));
        actions.add(new Action(hannibal, 12, Action.Type.GO_UP));
        actions.add(new Action(hannibal, 13, Action.Type.GO_LEFT));
        actions.add(new Action(hannibal, 30, Action.Type.GO_RIGHT));
        actions.add(new Action(hannibal, 31, Action.Type.GO_DOWN));
        actions.add(new Action(hannibal, 32, Action.Type.GO_DOWN));
        actions.add(new Action(hannibal, 33, Action.Type.GO_DOWN));
        actions.add(new Action(hannibal, 34, Action.Type.GO_DOWN));
        actions.add(new Action(hannibal, 35, Action.Type.DESPAWN));

        actions.add(new Action(will, 5, Action.Type.SPAWN));
        actions.add(new Action(will, 6, Action.Type.GO_LEFT));
        actions.add(new Action(will, 7, Action.Type.GO_LEFT));
        actions.add(new Action(will, 8, Action.Type.GO_LEFT));
        actions.add(new Action(will, 9, Action.Type.GO_UP));
        actions.add(new Action(will, 15, Action.Type.GO_RIGHT));
        actions.add(new Action(will, 16, Action.Type.GO_UP));
        actions.add(new Action(will, 17, Action.Type.GO_RIGHT));
        actions.add(new Action(will, 18, Action.Type.GO_UP));
        actions.add(new Action(will, 19, Action.Type.GO_RIGHT));
        actions.add(new Action(will, 20, Action.Type.GO_UP));
        actions.add(new Action(will, 21, Action.Type.GO_RIGHT));
        actions.add(new Action(will, 25, Action.Type.GO_LEFT));
        actions.add(new Action(will, 26, Action.Type.GO_DOWN));
        actions.add(new Action(will, 27, Action.Type.GO_DOWN));
        actions.add(new Action(will, 28, Action.Type.GO_DOWN));
        actions.add(new Action(will, 29, Action.Type.GO_DOWN));
        actions.add(new Action(will, 30, Action.Type.GO_DOWN));
        actions.add(new Action(hannibal, 31, Action.Type.DESPAWN));

        actions.add(new Action(alana, 10, Action.Type.SPAWN));
        actions.add(new Action(alana, 11, Action.Type.GO_RIGHT));
        actions.add(new Action(alana, 12, Action.Type.GO_RIGHT));
        actions.add(new Action(alana, 13, Action.Type.GO_RIGHT));
        actions.add(new Action(alana, 14, Action.Type.DESPAWN));

        return new World(context, 5, 5, actions, beacons, visitors);
    }

    public void update(Integer time) {
        for (Action a : actions) {
            if (a.time == time) {
                switch(a.action) {
                    case SPAWN:
                        a.visitor.isAlive = Boolean.TRUE;
                    break;
                    case DESPAWN:
                        a.visitor.isAlive = Boolean.FALSE;
                        break;
                    case GO_UP:
                        a.visitor.posY--;
                        break;
                    case GO_RIGHT:
                        a.visitor.posX++;
                        break;
                    case GO_DOWN:
                        a.visitor.posY++;
                        break;
                    case GO_LEFT:
                        a.visitor.posX--;
                        break;
                    default:
                        throw new UnsupportedOperationException();


                }
            }
        }
        for (Visitor v : visitors) {
            if (v.isAlive) {
                for (Beacon b : beacons) {
                    if (b.areaCovered.map[v.posX][v.posY]) {
                        Boolean isAlreadyAdded = Boolean.FALSE;
                        for (Visitor.VisitorEnterSignal vns : v.beaconsContains) {
                            if (vns.beacon.equals(b)) {
                                isAlreadyAdded = Boolean.TRUE;
                            }
                        }
                        if (!isAlreadyAdded) {
                            v.beaconsContains.add(new Visitor.VisitorEnterSignal(v, b));
                        }
                    }
                }

                for (Visitor.VisitorEnterSignal vns : v.beaconsContains) {
                    v.isIn(context, vns.beacon);
                }
            }
        }
    }

}

