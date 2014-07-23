package com.looppulse.blesimulator.models;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.UUID;

/**
 * Created by Gilbert on 7/23/2014.
 */
public class World {
    public final Integer width;
    public final Integer height;

    public final List<Action> actions;

    public final List<Beacon> beacons;
    public final List<Visitor> visitors;

    public World(Integer width, Integer height, List<Action> actions, List<Beacon> beacons, List<Visitor> visitors) {
        this.width = width;
        this.height = height;
        this.actions = actions;
        this.beacons = beacons;
        this.visitors = visitors;
    }

    public static World getSampleWorld() {
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

        return new World(5, 5, actions, beacons, visitors);
    }
}

