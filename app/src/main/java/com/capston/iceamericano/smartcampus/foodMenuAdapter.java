package com.capston.iceamericano.smartcampus;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.capston.iceamericano.smartcampus.Beacon.BeaconService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;



public class foodMenuAdapter extends RecyclerView.Adapter<foodMenuAdapter.ViewHolder> {

    List<foodMenu> mList;
    Context context;
    String TAG = getClass().getSimpleName();

    ComponentName mServiceName;


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView foodMenu_time_section,foodMenu_restaurant_name,foodMenu_date, foodMenu_menu_name,foodMenu_price;
        public TextView food_total;

        public ViewHolder(View itemview) {
            super(itemview);
            foodMenu_time_section = (TextView) itemview.findViewById(R.id.foodMenu_time_section);
            foodMenu_restaurant_name = (TextView) itemview.findViewById(R.id.foodMenu_restaurant_name);
            foodMenu_date = (TextView) itemview.findViewById(R.id.foodMenu_date);
            foodMenu_menu_name = (TextView) itemview.findViewById(R.id.foodMenu_menu_name);
            foodMenu_price = (TextView) itemview.findViewById(R.id.foodMenu_price);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public foodMenuAdapter(List<foodMenu> mList, Context context) {
        this.mList = mList;
        this.context = context;
    }


    @Override
    public foodMenuAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_food, parent, false);

        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }


    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your mChat at this position
        // - replace the contents of the view with that element
        holder.foodMenu_time_section.setText(mList.get(position).getTime_section());
        holder.foodMenu_restaurant_name.setText(mList.get(position).getRestaurant_name());
        holder.foodMenu_date.setText(mList.get(position).getDate());
        holder.foodMenu_menu_name.setText(mList.get(position).getMenu_name());
        holder.foodMenu_price.setText(mList.get(position).getPrice());


    }




    // Return the size of your mChat (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mList.size();
    }
}
