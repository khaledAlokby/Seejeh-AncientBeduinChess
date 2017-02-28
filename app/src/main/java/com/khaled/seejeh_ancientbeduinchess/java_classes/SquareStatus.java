package com.khaled.seejeh_ancientbeduinchess.java_classes;

/**
 * Created by Khaled on 12/12/2016.
 */

public enum SquareStatus {
    FORBIDDEN(3),
    BlLACK(0),
    RED(1),
    EMPTY(2);

    private int numVal;

    SquareStatus(int numVal) {
        this.numVal = numVal;
    }

    public int getNumVal() {
        return numVal;
    }
}
