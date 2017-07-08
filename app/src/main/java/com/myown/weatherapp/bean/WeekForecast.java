package com.myown.weatherapp.bean;

/**
 * Created by Admin on 7/5/2017.
 */
public class WeekForecast {

    private String day;
    private String date;
    private String weather;
    private String minTemp;
    private String maxTemp;
    private String temp;

    public void setItems(String day, String date, String weather, String minTemp, String maxTemp, String temp) {
        this.day = day;
        this.date = date;
        this.weather = weather;
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.temp = temp;
    }

    public String getDay() {
        return day;
    }

    public String getDate() {
        return date;
    }

    public String getWeather() {
        return weather;
    }

    public String getMinTemp() {
        return minTemp;
    }

    public String getMaxTemp() {
        return maxTemp;
    }

    public String getTemp() {
        return temp;
    }
}
