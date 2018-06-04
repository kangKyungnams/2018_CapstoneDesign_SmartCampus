package com.capston.iceamericano.smartcampus;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.capston.iceamericano.smartcampus.Beacon.BeaconService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;



public class AttAdapter extends RecyclerView.Adapter<AttAdapter.ViewHolder> {

    List<AttState> mList;
    Context context;
    String TAG = getClass().getSimpleName();



    ComponentName mServiceName;


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView att_adapter_num,att_adapter_name,att_adapter_date, att_adapter_state;
        public Button att_adapter_start;

        public ViewHolder(View itemview) {
            super(itemview);
            att_adapter_num = (TextView) itemview.findViewById(R.id.att_adapter_num);
            att_adapter_name = (TextView) itemview.findViewById(R.id.att_adapter_name);
            att_adapter_date = (TextView) itemview.findViewById(R.id.att_adapter_date);
            att_adapter_state = (TextView) itemview.findViewById(R.id.att_adapter_state);
            att_adapter_start = (Button) itemview.findViewById(R.id.att_adapter_start);

        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public AttAdapter(List<AttState> mList, Context context) {
        this.mList = mList;
        this.context = context;

    }


    @Override
    public AttAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_att_state, parent, false);

        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }


    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your mChat at this position
        // - replace the contents of the view with that element
        String num = String.valueOf(mList.get(position).getNum());
        holder.att_adapter_num.setText(num.toString());
        holder.att_adapter_name.setText(mList.get(position).getName());
        holder.att_adapter_date.setText(mList.get(position).getDate());
        holder.att_adapter_state.setText(mList.get(position).getState_Check());

        holder.att_adapter_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date = new Date();
                long now = System.currentTimeMillis();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String getTime = sdf.format(now);

                if (mList.get(position).getDate().equals(getTime.substring(0, 10))) {
                    Intent mIntent = new Intent(context, BeaconService.class);
                    mIntent.putExtra("lectureID", mList.get(position).getLectureID());
                    mIntent.putExtra("lectureDateKey", mList.get(position).getLectureDateKey());
                    mServiceName = context.startService(mIntent);
                } else {
                    Toast.makeText(context, "오늘 날짜에 해당하는 출석이 아닙니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }




    // Return the size of your mChat (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mList.size();
    }
}
