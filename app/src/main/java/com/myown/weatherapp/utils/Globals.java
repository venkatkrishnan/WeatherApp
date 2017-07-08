package com.myown.weatherapp.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.myown.weatherapp.R;

/**
 * Created by Admin on 6/2/2017.
 */
public class Globals {

    public static Globals globals;
    public SharedPref sharedPref = null;
    public static Context appContext = null;
    public ConnectionDetector connectionDetector = null;
    public boolean debugMode = false;

    private static final String TAG = "Globals";
    final public String jsonKey = "jsonData";
    final public String ipPort = "http://samples.openweathermap.org/data/2.5/forecast?q=Bangalore,IN&appid=b1b15e88fa797225412429c1c50c122a1";

    public static Globals getInstance(Context context) {
        if(globals == null) {
            globals = new Globals();
            globals.init(context);
        }
        if(appContext == null)
            appContext = context;
        return globals;
    }

    public boolean init(Context context) {
        try {
            appContext = context;
            sharedPref = new SharedPref(context);
            connectionDetector = new ConnectionDetector(context);

        } catch(Exception e) {
            if(debugMode)
                Log.e(TAG, e.toString());
            return false;
        }
        return true;
    }

    public void customToast(Context context, String toastMsg, int duration) {
        Toast.makeText(context, toastMsg, duration).show();
    }

    public boolean checkNetworkConnection(Context context) {
        if(!globals.connectionDetector.isConnectingToInternet()) {
            globals.customToast(context, context.getString(R.string.no_network), Toast.LENGTH_LONG);
            return false;
        }
        return true;
    }
}
