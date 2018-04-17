package com.capston.iceamericano.smartcampus;

        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.app.AlertDialog;
        import android.content.Context;
        import android.net.Uri;
        import android.os.Bundle;
        import android.support.annotation.Nullable;
        import android.support.v4.app.Fragment;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.ListView;
        import android.widget.RadioGroup;
        import android.widget.Spinner;

        import org.json.JSONArray;
        import org.json.JSONObject;

        import java.util.ArrayList;
        import java.util.List;

public class CourseList extends AppCompatActivity {

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
        setContentView(R.layout.activity_course_list);

        yearSpinner = (Spinner)findViewById(R.id.yearSpinner);
        semesterSpinner = (Spinner)findViewById(R.id.semesterSpinner);
        areaSpinner = (Spinner)findViewById(R.id.areaSpinner);


        yearAdapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.year, android.R.layout.simple_spinner_dropdown_item);
        yearSpinner.setAdapter(yearAdapter);
        semesterAdapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.semester, android.R.layout.simple_spinner_dropdown_item);
        semesterSpinner.setAdapter(semesterAdapter);
        areaAdapter = ArrayAdapter.createFromResource(getApplicationContext(),R.array.area, android.R.layout.simple_spinner_dropdown_item);
        areaSpinner.setAdapter(areaAdapter);

        courseListView = (ListView)findViewById(R.id.courseListView);
        courseList = new ArrayList<Course>();
        adapter = new CourseListAdapter(getApplicationContext(), courseList);
        courseListView.setAdapter(adapter);


    }



}
