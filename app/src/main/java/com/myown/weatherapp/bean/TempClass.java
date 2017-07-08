package com.myown.weatherapp.bean;

import java.util.List;

/**
 * Created by Admin on 7/5/2017.
 */
public class TempClass {

    private String cod;
    private String message;
    private String cnt;
    private List<TempListClass> list;
    private TempCityClass city;

    public String getCod() {
        return cod;
    }

    public String getMessage() {
        return message;
    }

    public String getCnt() {
        return cnt;
    }

    public List<TempListClass> getList() {
        return list;
    }

    public TempCityClass getCity() {
        return city;
    }
}
