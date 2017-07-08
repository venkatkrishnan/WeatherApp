package com.myown.weatherapp.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Admin on 7/5/2017.
 */
public class TempCloud {

    @SerializedName("all")
    private String cloud;

    public String getCloud() {
        return cloud;
    }
}
