package com.looppulse.blesimulator.models;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

import static com.google.common.base.Preconditions.checkState;

/**
 * Created by Gilbert on 7/22/2014.
 */
public class FloorPlan {

    public static final Object KEY_MAP = "map";
    //use space instead of CPU cycle
    //0,0 in the top-left corner
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public final Boolean map[][];

    //Limitation only rectangle for now
    public FloorPlan(List<List<Boolean>> fp) {
        checkState(fp.size() > 0);
        checkState(fp.get(0).size() > 0);
        this.map = new Boolean[fp.size()][fp.get(0).size()];
        int i = 0;
        for (List<Boolean> bs : fp) {
            int j = 0;
            for (Boolean b : bs) {
                map[i][j] = b;
                j++;
            }
            i++;
        }
    }

    public FloorPlan(int width, int height, int range, int posX, int posY) {
        map = new Boolean[width][height];
        for (int i=0; i< width; i++) {
            for (int j=0; j< height; j++) {
                if(i >= posX - range && i <= posX + range
                        && j >= posY - range && j <= posY + range
                        ) {
                    map[i][j] = Boolean.TRUE;
                } else {
                    map[i][j] = Boolean.FALSE;
                }
            }
        }
    }



    //O(1) for the win
    public Boolean isInRange(int posX, int posY) {
        return map[posX][posY];
    }
}
