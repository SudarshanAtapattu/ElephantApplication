package com.newapp.elephantapplication;

public class Data_Model {


    String Age,FullName,LastName;

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







    //----------------------------------------------------------------------
    public Data_Model(String age, String fullName, String lastName) {
        Age = age;
        FullName = fullName;
        LastName = lastName;
    }


    public String getAge() {
        return Age;
    }

    public String getFullName() {
        return FullName;
    }
    public String getLastName() {
        return LastName;
    }


    public void setAge(String age) {
        Age = age;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public  Data_Model (){

    }
}
