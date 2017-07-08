package com.myown.weatherapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.myown.weatherapp.R;
import com.myown.weatherapp.bean.WeekForecast;

import java.util.List;

/**
 * Created by Admin on 7/6/2017.
 */
public class WeekForecastAdapter extends RecyclerView.Adapter<WeekForecastAdapter.MyViewHolder> {

    private final String TAG = "WeekForecastAdapter";

    Context context;
    View.OnClickListener clickListener = null;
    private List<WeekForecast> weeklyForecast;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView day, date, minTemp, maxTemp, temp;
        public ImageView weather;

        public MyViewHolder(View view) {
            super(view);

            day = (TextView) view.findViewById(R.id.week_day);
            date = (TextView) view.findViewById(R.id.week_date);
            weather = (ImageView) view.findViewById(R.id.week_weather);
            minTemp = (TextView) view.findViewById(R.id.week_min_temp);
            maxTemp = (TextView) view.findViewById(R.id.week_max_temp);
            temp = (TextView) view.findViewById(R.id.week_temp);
        }
    }

    public WeekForecastAdapter(Context context, List<WeekForecast> weeklyForecast, View.OnClickListener clickListener) {
        this.context = context;
        this.weeklyForecast = weeklyForecast;
        this.clickListener = clickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_weekly_report, parent, false);

        int width = Resources.getSystem().getDisplayMetrics().widthPixels/weeklyForecast.size();
        itemView.setLayoutParams(new RecyclerView.LayoutParams(width, RecyclerView.LayoutParams.MATCH_PARENT));

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        WeekForecast weekDay = weeklyForecast.get(position);
        holder.day.setText(weekDay.getDay());
        holder.date.setText(weekDay.getDate());

        if(weekDay.getWeather().equals(context.getString(R.string.rain))) {
            holder.weather.setImageResource(R.drawable.rain);
        } else if(weekDay.getWeather().equals(context.getString(R.string.clear))) {
            holder.weather.setImageResource(R.drawable.clear);
        } else if(weekDay.getWeather().equals(context.getString(R.string.clouds))) {
            holder.weather.setImageResource(R.drawable.cloud);
        }

        holder.minTemp.setText(weekDay.getMinTemp());
        holder.maxTemp.setText(weekDay.getMaxTemp());
        holder.temp.setText(weekDay.getTemp());
    }

    @Override
    public int getItemCount() {
        return weeklyForecast.size();
    }
}

