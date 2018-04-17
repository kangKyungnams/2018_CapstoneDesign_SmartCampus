package com.capston.iceamericano.smartcampus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.List;

public class AttCheckActivity extends AppCompatActivity {

    private ListView courseListView;
    private CourseListAdapter adapter;
    private List<Course> courseList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_att_check);
    }
}
