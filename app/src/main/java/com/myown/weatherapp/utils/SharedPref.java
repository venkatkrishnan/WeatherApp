package com.myown.weatherapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.myown.weatherapp.R;

public class SharedPref {

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    private final String TEMP_UNIT = "tempUnit";
    private final String TIME_FORMAT = "timeFormat";
    private final String NOTIF = "notif";

    public SharedPref(Context context) {
        pref = context.getSharedPreferences(context.getString(R.string.app_name), context.MODE_PRIVATE);
        editor= pref.edit();
    }

    public void clearAllPref() {
        editor.clear();
        editor.commit();
    }

    public void setTemp(boolean status) {
        editor.putBoolean(TEMP_UNIT, status);
        editor.commit();
    }

    public void setTime(boolean status) {
        editor.putBoolean(TIME_FORMAT, status);
        editor.commit();
    }

    public void setNotif(boolean status) {
        editor.putBoolean(NOTIF, status);
        editor.commit();
    }

    public boolean getTemp() {
        return pref.getBoolean(TEMP_UNIT, true);
    }

    public boolean getTime() {
        return pref.getBoolean(TIME_FORMAT, true);
    }

    public boolean getNotif() {
        return pref.getBoolean(NOTIF, true);
    }
}
