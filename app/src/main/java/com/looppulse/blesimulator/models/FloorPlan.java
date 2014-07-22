package com.looppulse.blesimulator.models;

/**
 * Created by Gilbert on 7/22/2014.
 */
public class FloorPlan {

    //HERE of coz it is better to use config file or describe in the json
    public static final Integer WIDTH = 5;
    public static final Integer HEIGHT = 5;

    //use space instead of CPU cycle
    public final Boolean map[][];

    public FloorPlan(int range, int posX, int posY) {
        map = new Boolean[WIDTH][HEIGHT];
        for (int i=0; i< WIDTH; i++) {
            for (int j=0; j< HEIGHT; j++) {
                if(i >= posX - range && i <= posX + range
                        && j >= posY - range && j <= posY + range
                        ) {
                    map[i][j] = true;
                } else {
                    map[i][j] = false;
                }
            }
        }
    }

    //O(1) for the win
    public Boolean isInRange(int posX, int posY) {
        return map[posX][posY];
    }
}
