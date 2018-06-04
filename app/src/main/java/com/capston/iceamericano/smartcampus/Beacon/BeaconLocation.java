package com.capston.iceamericano.smartcampus.Beacon;

/**
 * Created by 경남 on 2018-05-24.
 */

public class BeaconLocation {
    String name;
    double mX;
    double mY;
    double[] mXY;
    public BeaconLocation(String name, double mX, double mY){
        this.name = name;
        this.mX = mX;
        this.mY = mY;
    }
    public String getName(){
        return this.name;
    }
    public double getmX(){
        return mX;
    }
    public double getmY(){
        return mY;
    }


}
