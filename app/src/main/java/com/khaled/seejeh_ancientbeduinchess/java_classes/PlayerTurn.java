package com.khaled.seejeh_ancientbeduinchess.java_classes;

/**
 * Created by Khaled on 12/13/2016.
 */
public enum PlayerTurn {
    BLACK_TURN(0),
    RED_TURN(1);

    private int numVal;

    PlayerTurn(int val) {
        numVal = val;
    }

    public int getNumVal() {
        return numVal;
    }
}
