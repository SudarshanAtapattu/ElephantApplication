package com.newapp.elephantapplication;

public class DataPoint {

    int xValue,yValue;

    public DataPoint(int xValue, int yValue) {
        this.xValue = xValue;
        this.yValue = yValue;
    }

    public int getxValue() {
        return xValue;
    }

    public int getyValue() {
        return yValue;
    }

    public void setxValue(int xValue) {
        this.xValue = xValue;
    }

    public void setyValue(int yValue) {
        this.yValue = yValue;
    }

    public DataPoint() {

    }


}
