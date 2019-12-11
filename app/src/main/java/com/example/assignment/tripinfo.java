package com.example.assignment;

public class tripinfo {

    //all the values of trip details
    String startLocation;
    String startLocationTime;
    String endLocation;
    String endLocationTime;
    String Amt;
    String TotalTime;
    String Currency;

    public tripinfo(String startLocation, String startLocationTime, String endLocation, String endLocationTime, String amt, String totalTime, String currency) {
        this.startLocation = startLocation;
        this.startLocationTime = startLocationTime;
        this.endLocation = endLocation;
        this.endLocationTime = endLocationTime;
        Amt = amt;
        TotalTime = totalTime;
        Currency = currency;
    }

    public String getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(String startLocation) {
        this.startLocation = startLocation;
    }

    public String getStartLocationTime() {
        return startLocationTime;
    }

    public void setStartLocationTime(String startLocationTime) {
        this.startLocationTime = startLocationTime;
    }

    public String getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(String endLocation) {
        this.endLocation = endLocation;
    }

    public String getEndLocationTime() {
        return endLocationTime;
    }

    public void setEndLocationTime(String endLocationTime) {
        this.endLocationTime = endLocationTime;
    }

    public String getAmt() {
        return Amt;
    }

    public void setAmt(String amt) {
        Amt = amt;
    }

    public String getTotalTime() {
        return TotalTime;
    }

    public void setTotalTime(String totalTime) {
        TotalTime = totalTime;
    }

    public String getCurrency() {
        return Currency;
    }

    public void setCurrency(String currency) {
        Currency = currency;
    }
}
