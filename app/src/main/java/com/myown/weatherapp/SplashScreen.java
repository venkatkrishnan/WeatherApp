package com.myown.weatherapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.myown.weatherapp.bean.TempClass;
import com.myown.weatherapp.utils.Globals;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Admin on 7/8/2017.
 */
public class SplashScreen extends Activity {

    final String TAG = "SplashScreen";

    Context context = null;
    Globals globals = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initializeWidgets();

        serverCall();
    }

    private void initializeWidgets() {
        context = getApplicationContext();
        globals = Globals.getInstance(context);
    }

    private void serverCall() {

        if(!globals.checkNetworkConnection(context)) {
            return;
        }

        StringRequest stringRequest = new StringRequest(Request.Method.GET, globals.ipPort,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "Response from server " + response);
                        try {

                            loadHomeActivity(response);
                        } catch(Exception e) {
                            Log.e(TAG, "Exception in response" + e.toString());
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, error.toString());
                        Toast.makeText(context, getString(R.string.err_msg1), Toast.LENGTH_LONG).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    private void loadHomeActivity(String response) {
        Intent homeActivity = new Intent(this, HomeActivity.class);
        homeActivity.putExtra(globals.jsonKey, response);
        startActivity(homeActivity);
        this.finish();
    }
}
