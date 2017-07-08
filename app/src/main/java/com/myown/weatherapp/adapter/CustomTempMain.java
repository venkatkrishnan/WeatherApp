package com.myown.weatherapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.myown.weatherapp.R;
import com.myown.weatherapp.bean.SelectedItem;

import java.util.List;

/**
 * Created by Admin on 7/5/2017.
 */
public class CustomTempMain  extends BaseAdapter {
    private Context context;
    private List<SelectedItem> selectedItems;
    private View.OnClickListener clickListener = null;

    public CustomTempMain(Context context, List<SelectedItem> selectedItems, View.OnClickListener clickListener) {
        this.context = context;
        this.selectedItems = selectedItems;
        this.clickListener = clickListener;
    }

    @Override
    public int getCount() {
        return selectedItems.size();
    }

    @Override
    public Object getItem(int p) {
        return null;
    }

    @Override
    public long getItemId(int p) {
        return 0;
    }

    @Override
    public View getView(int p, View convertView, ViewGroup parent) {
        View grid;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            grid = inflater.inflate(R.layout.custom_temp_detail, null);

            ImageView image = (ImageView) grid.findViewById(R.id.temp_image);
            TextView value = (TextView) grid.findViewById(R.id.temp_value);
            TextView label = (TextView) grid.findViewById(R.id.temp_label);

            String type = selectedItems.get(p).getType();
            if(type.equals(context.getString(R.string.wind))) {
                image.setImageResource(R.drawable.wind);
            } else if(type.equals(context.getString(R.string.pressure))) {
                image.setImageResource(R.drawable.pressure);
            } else if(type.equals(context.getString(R.string.humidity))) {
            image.setImageResource(R.drawable.humidity);
            }

            value.setText(selectedItems.get(p).getValue());
            label.setText(type);
        } else {
            grid = (View) convertView;
        }
        return grid;
    }}