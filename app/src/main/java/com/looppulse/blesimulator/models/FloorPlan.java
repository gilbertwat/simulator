package com.looppulse.blesimulator.models;

/**
 * Created by Gilbert on 7/22/2014.
 */
public class FloorPlan {

    //use space instead of CPU cycle
    //0,0 in the top-left corner
    public final Boolean map[][];

    public FloorPlan(int width, int height, int range, int posX, int posY) {
        map = new Boolean[width][height];
        for (int i=0; i< width; i++) {
            for (int j=0; j< height; j++) {
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
