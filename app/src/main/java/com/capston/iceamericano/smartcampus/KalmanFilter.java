package com.capston.iceamericano.smartcampus;

/**
 * Created by 경남 on 2018-05-14.
 */

public class KalmanFilter {
    private double Q = 0.00001;
    private double R = 0.001;
    private double X = 0, P = 1, K;

    public KalmanFilter(double initValue){
        X = initValue;
    }

    public double update(double measurement){
        measurementUpdate();
        X = X + (measurement - X) * K;
        return X;
    }

    private void measurementUpdate(){
        K = (P + Q) / (P + Q + R);
        P = R * (P + Q) / (R + P + Q);
    }

    public void resetFilter(){
        Q = 0.00001;
        R = 0.001;
        P = 1;
        X = 0;
    }
}
