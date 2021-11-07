package com.group6.cleansweep.models.roomba;

//class to determine the amount of power being used
public class TileToPower {

    public static float convert(String type) {
        float powerUse = 0;

        if (type.equals("tile") || type.equals("station"))
            powerUse = 1;
        else if (type.equals("carpet"))
            powerUse = 2;
        else if (type.equals("heavy_carpet"))
            powerUse = 3;

        return powerUse;
    }
}
