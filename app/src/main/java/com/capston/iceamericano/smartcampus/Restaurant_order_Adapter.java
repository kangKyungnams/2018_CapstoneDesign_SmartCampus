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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;



public class Restaurant_order_Adapter extends RecyclerView.Adapter<Restaurant_order_Adapter.ViewHolder> {

    List<Restaurant_order> mList;
    Context context;
    String TAG = getClass().getSimpleName();

    DatabaseReference uReference = FirebaseDatabase.getInstance().getReference();
    DatabaseReference userdata = uReference.child("order");


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView order_num,order_menu_name,bt_order_status,tv_order_count;

        public ViewHolder(View itemview) {
            super(itemview);
            order_num = (TextView) itemview.findViewById(R.id.order_num);
            order_menu_name = (TextView) itemview.findViewById(R.id.order_menu_name);
            bt_order_status = (TextView) itemview.findViewById(R.id.bt_order_status);
            tv_order_count = (TextView) itemview.findViewById(R.id.tv_order_count);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public Restaurant_order_Adapter(List<Restaurant_order> mList, Context context) {
        this.mList = mList;
        this.context = context;
    }


    @Override
    public Restaurant_order_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_restaurant_order, parent, false);

        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }


    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // - get element from your mChat at this position
        // - replace the contents of the view with that element
        holder.order_num.setText(String.valueOf(mList.get(position).getNum()));
        holder.order_menu_name.setText(mList.get(position).getMenu_name());
        holder.bt_order_status.setText(mList.get(position).getStatus());
        holder.tv_order_count.setText(String.valueOf(mList.get(position).getCount()));
        holder.bt_order_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.bt_order_status.setText("조리 완료");
                userdata.child(mList.get(position).getHigh_level()).child(mList.get(position).getRestaurant_name()).child(mList.get(position).getDate()).child(mList.get(position).getEmail()).child("status").setValue("조리완료");
            }
        });
    }




    // Return the size of your mChat (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mList.size();
    }
}
