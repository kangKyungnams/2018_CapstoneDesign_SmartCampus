package com.capston.iceamericano.smartcampus;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import java.util.List;


/**
 * Created by bgjuw on 2017-11-25.
 */

public class CourseListAdapter extends RecyclerView.Adapter<CourseListAdapter.ViewHolder> {

    List<Course> mList;
    Context context;
    String TAG = getClass().getSimpleName();



    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView course_List_Credit,course_List_Room,course_List_Professor,course_List_Title;
        public Button course_List_info_button;

        public ViewHolder(View itemview) {
            super(itemview);
            course_List_Credit = (TextView) itemview.findViewById(R.id.course_List_Credit);
            course_List_Room = (TextView) itemview.findViewById(R.id.course_List_Room);
            course_List_Professor = (TextView) itemview.findViewById(R.id.course_List_Professor);
            course_List_Title = (TextView) itemview.findViewById(R.id.course_List_Title);
            course_List_info_button = (Button)itemview.findViewById(R.id.course_List_info_button);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public CourseListAdapter(List<Course> mList, Context context) {
        this.mList = mList;
        this.context = context;
    }


    @Override
    public CourseListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.course, parent, false);

        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your mChat at this position
        // - replace the contents of the view with that element
        holder.course_List_Credit.setText(mList.get(position).getCredit());
        holder.course_List_Professor.setText(mList.get(position).getProfessor_name());
        holder.course_List_Room.setText(mList.get(position).getClassroom_id());
        holder.course_List_Title.setText(mList.get(position).getTitle());

        holder.course_List_info_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String classKey = mList.get(position).getLectureID();
                Log.d(TAG, "Value is: " + classKey);
                Intent in = new Intent(context, InfoCourseActivity.class);
                in.putExtra("lectureIDKey",classKey);
                context.startActivity(in);

            }
        });

    }

    // Return the size of your mChat (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mList.size();
    }
}
