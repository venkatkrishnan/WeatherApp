package com.myown.weatherapp.bean;

/**
 * Created by Admin on 7/5/2017.
 */
public class HourlyForecast {

    private String time;
    private String temp;

    public void setItems(String time, String temp) {
        this.time = time;
        this.temp = temp;
    }

    public String getTime() {
        return time;
    }

    public String getTemp() {
        return temp;
    }
}
