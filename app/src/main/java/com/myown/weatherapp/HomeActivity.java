package com.myown.weatherapp;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.myown.weatherapp.adapter.CustomTempMain;
import com.myown.weatherapp.adapter.WeekForecastAdapter;
import com.myown.weatherapp.bean.SelectedItem;
import com.myown.weatherapp.bean.TempClass;
import com.myown.weatherapp.bean.TempListClass;
import com.myown.weatherapp.bean.TempMain;
import com.myown.weatherapp.bean.TempWeather;
import com.myown.weatherapp.bean.TempWind;
import com.myown.weatherapp.bean.WeekForecast;
import com.myown.weatherapp.utils.Globals;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {

    final String TAG = "HomeActivity";

    Context context = null;
    Globals globals = null;
    TempClass temp = null;

    boolean isCelsius, isTimeIn24Hrs, weekGraphVisible = false;

    ImageButton settings;
    GridView mainList = null;
    GraphView weekGraph = null;
    RecyclerView weekForecast = null;
    LinearLayout activityLayout = null;
    ImageView skyImage;
    TextView tempText, minTemp, maxTemp, sky, location;
    TextView uiTime, uiDate, viewGraph;
    View.OnClickListener clickListener = null;

    SimpleDateFormat h24Format = new SimpleDateFormat(("HH"));
    SimpleDateFormat h12Format = new SimpleDateFormat(("hh"));
    SimpleDateFormat hours = null;
    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd");
    SimpleDateFormat getDateFormat = new SimpleDateFormat("dd");

    SimpleDateFormat today24Time = new SimpleDateFormat("HH:mm");
    SimpleDateFormat today12Time = new SimpleDateFormat("hh:mm a");
    SimpleDateFormat todayDate = new SimpleDateFormat("dd/MM/yyyy");

    String[] weekDays = new String[]{"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initializeWidgets();
        setClickListener();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        isCelsius = globals.sharedPref.getTemp();
        isTimeIn24Hrs = globals.sharedPref.getTime();

        setGraphIcon();
        getTimeFormat();
        loadData();
    }

    private void initializeWidgets() {
        context = getApplicationContext();
        globals = Globals.getInstance(context);

        settings = (ImageButton) findViewById(R.id.settings);
        tempText = (TextView) findViewById(R.id.temp);
        minTemp = (TextView) findViewById(R.id.minTemp);
        maxTemp = (TextView) findViewById(R.id.maxTemp);
        sky = (TextView) findViewById(R.id.sky);
        location = (TextView) findViewById(R.id.location);

        skyImage = (ImageView) findViewById(R.id.sky_image);

        mainList = (GridView) findViewById(R.id.temp_list);

        uiTime = (TextView) findViewById(R.id.time);
        uiDate = (TextView) findViewById(R.id.date);
        viewGraph = (TextView) findViewById(R.id.view_graph);

        weekGraph = (GraphView) findViewById(R.id.weekGraph);
        weekForecast = (RecyclerView) findViewById(R.id.weekForecast);
        activityLayout = (LinearLayout) findViewById(R.id.activityLayout);
    }

    private void setClickListener() {
        clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.settings:
                        Intent settingsIntent = new Intent(HomeActivity.this, SettingsActivity.class);
                        startActivity(settingsIntent);
                        break;
                    case R.id.view_graph:
                        weekGraphVisible = !weekGraphVisible;
                        if(weekGraphVisible)
                            weekGraph.setVisibility(View.VISIBLE);
                        else
                            weekGraph.setVisibility(View.GONE);
                        setGraphIcon();
                        break;
                }
            }
        };
        settings.setOnClickListener(clickListener);
        viewGraph.setOnClickListener(clickListener);
    }

    private void loadData() {

        if(temp == null) {
            Bundle bundle = getIntent().getExtras();
            if(bundle == null || !bundle.containsKey(globals.jsonKey) || bundle.get(globals.jsonKey) == null) {
                return;
            }

            String response = bundle.get(globals.jsonKey).toString();

            Gson gson = new Gson();
            temp = gson.fromJson(response, TempClass.class);
        }

        List<SelectedItem> selectedItems = new ArrayList<>();
        TempListClass list = temp.getList().get(temp.getList().size() - 1);
        TempMain tempMain = list.getMain();

        long today = Long.parseLong(list.getDt()) * 1000;
        Calendar c = Calendar.getInstance();

        location.setText(temp.getCity().getName());
        String showTime = "";
        if(isTimeIn24Hrs) {
            showTime = today24Time.format(today);
        } else {
            showTime = today12Time.format(today);
        }
        uiTime.setText(showTime);

        c.setTime(new Date(today));
        uiDate.setText(todayDate.format(today) + ", " + weekDays[c.get(Calendar.DAY_OF_WEEK) - 1]);

        int hrs = Integer.parseInt(h24Format.format(System.currentTimeMillis()));

        int backImg;
        if (hrs > 6 && hrs < 18)
            backImg = R.drawable.day;
        else
            backImg = R.drawable.night;

        activityLayout.setBackgroundResource(backImg);
        activityLayout.getBackground().setAlpha(120);

        String tempUnit = convertKelvinToUnit(Float.parseFloat(tempMain.getTemp()));

        tempText.setText(tempUnit);

        TempWeather weather = list.getWeather().get(0);

        sky.setText(weather.getMain());
        int weatherDrawable = 0;
        if(weather.getMain().equals(getString(R.string.clouds)))
            weatherDrawable = R.drawable.cloud;
        else if(weather.getMain().equals(getString(R.string.clear)))
            weatherDrawable = R.drawable.clear;
        else if(weather.getMain().equals(getString(R.string.rain)))
            weatherDrawable = R.drawable.rain;

        if(weatherDrawable != 0)
            skyImage.setImageResource(weatherDrawable);

        String tempMin = convertKelvinToUnit(Float.parseFloat(tempMain.getTemp_min()));
        String tempMax = convertKelvinToUnit(Float.parseFloat(tempMain.getTemp_max()));

        setSpannableText(" " + tempMin, minTemp, R.drawable.down);
        setSpannableText(" " + tempMax, maxTemp, R.drawable.up);

        SelectedItem humidity = new SelectedItem();
        humidity.setType(getString(R.string.humidity));
        humidity.setValue(tempMain.getHumidity() + getString(R.string.humidity_default));

        SelectedItem pressure = new SelectedItem();
        pressure.setType(getString(R.string.pressure));
        pressure.setValue(tempMain.getPressure() + getString(R.string.pressure_default));

        TempWind tempWind = list.getWind();
        SelectedItem wind = new SelectedItem();
        wind.setType(getString(R.string.wind));
        wind.setValue(tempWind.getSpeed() + getString(R.string.wind_default));

        selectedItems.add(humidity);
        selectedItems.add(pressure);
        selectedItems.add(wind);

        ArrayList<Integer> days = new ArrayList<>();
        ArrayList<WeekForecast> weeklyForecast = new ArrayList<>();
        LineGraphSeries<DataPoint> weekGraphForecast = new LineGraphSeries<>();

        for (TempListClass classItem : temp.getList()) {

            long dtTime = Long.parseLong(classItem.getDt()) * 1000;

            Date curDate = new Date(dtTime);

            String date = dateFormat.format(curDate);

            c.setTime(curDate);
            int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

            if (!days.contains(dayOfWeek)) {
                days.add(dayOfWeek);
                String day = weekDays[dayOfWeek - 1];

                String dayWeather = classItem.getWeather().get(0).getMain();

                String minTemp = convertKelvinToUnit(Float.parseFloat(classItem.getMain().getTemp_min()));
                String maxTemp = convertKelvinToUnit(Float.parseFloat(classItem.getMain().getTemp_max()));
                String dayTemp = convertKelvinToUnit(Float.parseFloat(classItem.getMain().getTemp()));

                WeekForecast forecast = new WeekForecast();
                forecast.setItems(day, date, dayWeather, minTemp, maxTemp, dayTemp);

                weeklyForecast.add(forecast);

                int time = Integer.parseInt(getDateFormat.format(curDate));

                String splitWith = "";
                if(isCelsius) {
                    splitWith = getString(R.string.celsius);
                } else {
                    splitWith = getString(R.string.fahrenheit);
                }
                int value = (int) Float.parseFloat(dayTemp.split(splitWith)[0]);

                weekGraphForecast.appendData(new DataPoint(time, value), true, 20, true);
            }
        }

        mainList.setAdapter(new CustomTempMain(context, selectedItems, clickListener));

        WeekForecastAdapter weekAdapter = new WeekForecastAdapter(context, weeklyForecast, clickListener);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        weekForecast.setLayoutManager(mLayoutManager);
        weekForecast.setItemAnimator(new DefaultItemAnimator());
        weekForecast.setAdapter(weekAdapter);

        weekGraph.removeAllSeries();
        weekGraph.addSeries(weekGraphForecast);

        customNotification(tempUnit, showTime, weather.getMain(), temp.getCity().getName());
    }

    private void setSpannableText(String text, TextView textView, int icon) {
        SpannableStringBuilder ssb = new SpannableStringBuilder(text);
        Bitmap smiley = BitmapFactory.decodeResource(getResources(), icon);
        ssb.setSpan(new ImageSpan(smiley), 0, 1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        textView.setText(ssb, TextView.BufferType.SPANNABLE);
    }

    private void setGraphIcon() {
        int icon = R.drawable.expand;
        if(weekGraphVisible) {
            icon = R.drawable.collapse;
        }
        viewGraph.setCompoundDrawablesWithIntrinsicBounds(0, 0, icon, 0);
    }

    private String convertKelvinToUnit(float kelvin) {

        String returnTemp = "";
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);

        float celsius = Float.parseFloat(df.format(kelvin - 273.15F));

        if(isCelsius) {  // celsius
            returnTemp = celsius + getString(R.string.celsius);
        } else {        // fahrenheit
            double temp = (celsius * 9.0/5.0) + 32.0;
            returnTemp = df.format(temp) + getString(R.string.fahrenheit);
        }

        return returnTemp;
    }

    private void getTimeFormat() {
        if(isTimeIn24Hrs) {     // 24 hours format
            hours = h24Format;
        } else {            // 12 hours format
            hours = h12Format;
        }
    }

    public void customNotification(String tempUnit, String time, String weather, String location) {

        RemoteViews remoteViews = new RemoteViews(getPackageName(),
                R.layout.custom_notification);

        Intent intent = new Intent(this, HomeActivity.class);

        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setTicker(getString(R.string.app_name))
                .setAutoCancel(false)
                .setContentIntent(pIntent)
                .setContent(remoteViews);

        int weatherDrawable = 0;
        if(weather.equals(getString(R.string.clouds)))
            weatherDrawable = R.drawable.cloud;
        else if(weather.equals(getString(R.string.clear)))
            weatherDrawable = R.drawable.clear;
        else if(weather.equals(getString(R.string.rain)))
            weatherDrawable = R.drawable.rain;


        remoteViews.setImageViewResource(R.id.app_image, R.mipmap.ic_launcher);
        remoteViews.setImageViewResource(R.id.sky_image, weatherDrawable);
        remoteViews.setTextViewText(R.id.temp, tempUnit);
        remoteViews.setTextViewText(R.id.dateTime,time);
        remoteViews.setTextViewText(R.id.sky, weather);
        remoteViews.setTextViewText(R.id.location, location);

        NotificationManager notificationmanager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationmanager.notify(0, builder.build());
    }
}
