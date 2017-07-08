package com.myown.weatherapp.bean;

import java.util.List;

/**
 * Created by Admin on 7/5/2017.
 */
public class TempListClass {
    private String dt;
    private TempWind wind;
    private List<TempWeather> weather;
    private TempCloud cloud;
    private TempMain main;
    private String dt_txt;

    public String getDt() {
        return dt;
    }

    public TempWind getWind() {
        return wind;
    }

    public List<TempWeather> getWeather() {
        return weather;
    }

    public TempCloud getCloud() {
        return cloud;
    }

    public TempMain getMain() {
        return main;
    }

    public String getDt_txt() {
        return dt_txt;
    }
}
