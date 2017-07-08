package com.myown.weatherapp;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.myown.weatherapp.utils.Globals;

/**
 * Created by Admin on 7/7/2017.
 */
public class SettingsActivity extends AppCompatActivity {

    final String TAG = "SettingsActivity";

    Context context = null;
    Globals globals = null;

    Toolbar toolBar;
    Button celsius, fahrenheit, h24, h12, notifOn, notifOff;
    View.OnClickListener clickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        initializeWidgets();
        setClickListener();

        setStatus();
    }

    private void initializeWidgets() {

        context = getApplicationContext();
        globals = Globals.getInstance(context);

        celsius = (Button) findViewById(R.id.celsius);
        fahrenheit = (Button) findViewById(R.id.fahrenheit);
        h24 = (Button) findViewById(R.id.h24);
        h12 = (Button) findViewById(R.id.h12);
        notifOn = (Button) findViewById(R.id.on);
        notifOff = (Button) findViewById(R.id.off);

        toolBar = (Toolbar) findViewById(R.id.move_back);
        setSupportActionBar(toolBar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getString(R.string.settings));
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void setClickListener() {
        clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.celsius:
                        setTemp(true);
                        break;
                    case R.id.fahrenheit:
                        setTemp(false);
                        break;
                    case R.id.h24:
                        setTime(true);
                        break;
                    case R.id.h12:
                        setTime(false);
                        break;
                    case R.id.on:
                        setNotif(true);
                        break;
                    case R.id.off:
                        setNotif(false);
                        break;
                }
            }
        };
        celsius.setOnClickListener(clickListener);
        fahrenheit.setOnClickListener(clickListener);
        h24.setOnClickListener(clickListener);
        h12.setOnClickListener(clickListener);
        notifOn.setOnClickListener(clickListener);
        notifOff.setOnClickListener(clickListener);
    }

    private void setStatus() {
        setStoredTemp();
        setStoredTime();
        setStoredNotif();
    }

    private void setStoredTemp() {
        if(globals.sharedPref.getTemp()) {
            celsius.setBackgroundResource(R.drawable.left_curved20dp);
            fahrenheit.setBackgroundColor(Color.TRANSPARENT);
        } else {
            celsius.setBackgroundColor(Color.TRANSPARENT);
            fahrenheit.setBackgroundResource(R.drawable.right_curved20dp);
        }
    }

    private void setStoredTime() {
        if(globals.sharedPref.getTime()) {
            h24.setBackgroundResource(R.drawable.left_curved20dp);
            h12.setBackgroundColor(Color.TRANSPARENT);
        } else {
            h24.setBackgroundColor(Color.TRANSPARENT);
            h12.setBackgroundResource(R.drawable.right_curved20dp);
        }
    }

    private void setStoredNotif() {
        if(globals.sharedPref.getNotif()) {
            notifOn.setBackgroundResource(R.drawable.left_curved5dp);
            notifOff.setBackgroundColor(Color.TRANSPARENT);
        } else {
            notifOn.setBackgroundColor(Color.TRANSPARENT);
            notifOff.setBackgroundResource(R.drawable.right_curved5dp);
        }
    }

    private void setTemp(boolean status) {
        globals.sharedPref.setTemp(status);
        setStoredTemp();
    }

    private void setTime(boolean status) {
        globals.sharedPref.setTime(status);
        setStoredTime();
    }

    private void setNotif(boolean status) {
        globals.sharedPref.setNotif(status);
        setStoredNotif();
    }
}
