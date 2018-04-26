package com.capston.iceamericano.smartcampus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.capston.iceamericano.smartcampus.Lecture.Course;
import com.capston.iceamericano.smartcampus.Lecture.CourseListAdapter;

import java.util.List;

public class StudentClassActivity extends AppCompatActivity {

    private ArrayAdapter yearAdapter;
    private Spinner yearSpinner;
    private ArrayAdapter semesterAdapter;
    private Spinner semesterSpinner;
    private ArrayAdapter areaAdapter;
    private Spinner areaSpinner;

    private String courseYear = "";
    private String courseSemester = "";
    private String courseMajor = "";

    private ListView courseListView;
    private CourseListAdapter adapter;
    private List<Course> courseList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_course_list);



    }



}
