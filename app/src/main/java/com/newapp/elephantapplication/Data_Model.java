package com.newapp.elephantapplication;

public class Data_Model {




    String IdElephant,Daily_Log_Date;

    long f_latitude,f_longitude;


    public Data_Model(String idElephant, String daily_Log_Date, long f_latitude, long f_longitude) {
        IdElephant = idElephant;
        Daily_Log_Date = daily_Log_Date;
        this.f_latitude = f_latitude;
        this.f_longitude = f_longitude;
    }

    public String getIdElephant() {
        return IdElephant;
    }

    public String getDaily_Log_Date() {
        return Daily_Log_Date;
    }

    public long getF_latitude() {
        return f_latitude;
    }

    public long getF_longitude() {
        return f_longitude;
    }

    public void setIdElephant(String idElephant) {
        IdElephant = idElephant;
    }

    public void setDaily_Log_Date(String daily_Log_Date) {
        Daily_Log_Date = daily_Log_Date;
    }

    public void setF_latitude(long f_latitude) {
        this.f_latitude = f_latitude;
    }

    public void setF_longitude(long f_longitude) {
        this.f_longitude = f_longitude;
    }



    public  Data_Model (){

    }
}
