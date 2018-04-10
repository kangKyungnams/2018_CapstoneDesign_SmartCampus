package com.capston.iceamericano.smartcampus;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class CourseListAdapter extends BaseAdapter {
    private Context context;
    private List<Course> courseList;

    public CourseListAdapter(Context context, List<Course> courseList) {
        this.context = context;
        this.courseList = courseList;
    }

    @Override
    public int getCount() {
        return courseList.size();
    }

    @Override
    public Object getItem(int i) {
        return courseList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.course, null);
        TextView courseTitle = (TextView) v.findViewById(R.id.courseTitle);
        TextView courseCredit = (TextView) v.findViewById(R.id.courseCredit);
        TextView courseProfessor = (TextView) v.findViewById(R.id.courseProfessor);
        TextView coursePersonnel = (TextView) v.findViewById(R.id.coursePersonnel);
        TextView courseTime = (TextView) v.findViewById(R.id.courseTime);


        courseTitle.setText(courseList.get(i).getCourseTitle());
        courseCredit.setText(courseList.get(i).getCourseCredit()+"학점");
        coursePersonnel.setText(courseList.get(i).getCoursePersonnel()+"명");
        courseProfessor.setText(courseList.get(i).getCoursePersonnel()+"교수");
        courseTime.setText(courseList.get(i).getCourseTime()+"");


        v.setTag(courseList.get(i).getCourseID());
        return v;
    }
}
