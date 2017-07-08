package com.myown.weatherapp.bean;

/**
 * Created by Admin on 7/5/2017.
 */
public class TempCityClass {

    private int id;
    private String name;
    private TempCoord coord;
    private String country;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public TempCoord getCoord() {
        return coord;
    }

    public String getCountry() {
        return country;
    }

    private class TempCoord {
        private String lat;
        private String lon;

        public String getLat() {
            return lat;
        }

        public String getLon() {
            return lon;
        }
    }
}
