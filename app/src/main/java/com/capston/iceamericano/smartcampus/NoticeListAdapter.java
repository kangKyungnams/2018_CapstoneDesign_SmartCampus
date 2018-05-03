package com.capston.iceamericano.smartcampus;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.List;



public class NoticeListAdapter extends RecyclerView.Adapter<NoticeListAdapter.ViewHolder> {

    List<Notice> mList;
    Context context;
    String TAG = getClass().getSimpleName();


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView notice_adapter_writer,notice_adapter_date,notice_adapter_title;

        public ViewHolder(View itemview) {
            super(itemview);
            notice_adapter_writer = (TextView) itemview.findViewById(R.id.notice_adapter_writer);
            notice_adapter_date = (TextView) itemview.findViewById(R.id.notice_adapter_date);
            notice_adapter_title = (TextView) itemview.findViewById(R.id.notice_adapter_title);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public NoticeListAdapter(List<Notice> mList, Context context) {
        this.mList = mList;
        this.context = context;
    }


    @Override
    public NoticeListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notice, parent, false);

        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }


    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your mChat at this position
        // - replace the contents of the view with that element
        holder.notice_adapter_writer.setText(mList.get(position).getWriter());
        holder.notice_adapter_date.setText(mList.get(position).getTime());
        holder.notice_adapter_title.setText(mList.get(position).getTitle());

//        holder.course_List_info_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String classKey = mList.get(position).getLectureID();
//                Log.d(TAG, "Value is: " + classKey);
//                Intent in = new Intent(context, InfoCourseActivity.class);
//                in.putExtra("lectureIDKey",classKey);
//                context.startActivity(in);
//
//            }
//        });

    }

    // Return the size of your mChat (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mList.size();
    }
}
