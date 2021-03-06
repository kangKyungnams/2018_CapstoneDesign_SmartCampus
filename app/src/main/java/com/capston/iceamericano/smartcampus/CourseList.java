package com.capston.iceamericano.smartcampus;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CourseList extends AppCompatActivity {

    RecyclerView mRecyclerView;
    LinearLayoutManager mLayoutManager;
    List<Course> mList;
    CourseListAdapter mAdapter;
    FirebaseDatabase database;
    String TAG = getClass().getSimpleName();
    String IDtype;
    ArrayList takes;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference uReference = FirebaseDatabase.getInstance().getReference();
    DatabaseReference userdata = uReference.child("takingCourseList");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_class);

        Intent in = getIntent();
        IDtype = in.getExtras().getString("IDtype");
        if (IDtype.equals("professor"))
        {
            takes = in.getStringArrayListExtra("takes");
        }

        database = FirebaseDatabase.getInstance();

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(CourseList.this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mList = new ArrayList<>();
        mAdapter = new CourseListAdapter(mList,this);
        mRecyclerView.setAdapter(mAdapter);


        if(IDtype.equals("professor"))
        {
            String value="";
            userdata = uReference.child("section");
            userdata.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    String value1 = dataSnapshot.getValue().toString();
                    for (DataSnapshot dataSnapshot2: dataSnapshot.getChildren()){
                        int i;
                        for(i =0;i<takes.size(); i++)
                        {
                            if(dataSnapshot2.getKey().equals(takes.get(i)))
                            {
                                String title = dataSnapshot2.child("title").getValue().toString();
                                String professor = dataSnapshot2.child("professor_name").getValue().toString();
                                String classroom = dataSnapshot2.child("location").child("classroom").getValue().toString();
                                String credit = dataSnapshot2.child("credit").getValue().toString();
                                String lectureID = dataSnapshot2.getKey();

                                Course course =  new Course(title,professor,classroom,credit+"학점",lectureID,IDtype);

                                // [START_EXCLUDE]
                                // Update RecyclerView
                                mList.add(course);
                                mAdapter.notifyItemInserted(mList.size() - 1);
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w(TAG, "Failed to read value.", error.toException());
                }
            });

        }
        else
        {
            int cut_index;
            String cut_char = user.getEmail();
            cut_index = cut_char.indexOf("@");
            String value= user.getEmail().substring(0, cut_index);

            DatabaseReference userLecture = userdata.child(value);
            userLecture.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    String value1 = dataSnapshot.getValue().toString();
                    for (DataSnapshot dataSnapshot2: dataSnapshot.getChildren()){

                        String title = dataSnapshot2.child("title").getValue().toString();
                        String professor = dataSnapshot2.child("professor_name").getValue().toString();
                        String classroom = dataSnapshot2.child("location").child("classroom").getValue().toString();
                        String credit = dataSnapshot2.child("credit").getValue().toString();
                        String lectureID = dataSnapshot2.getKey();

                        String value2 = dataSnapshot2.getValue().toString();

                        Log.d(TAG, "Value is: " + value2);
                        Course course =  new Course(title,professor,classroom,credit+"학점",lectureID,IDtype);


                        // [START_EXCLUDE]
                        // Update RecyclerView

                        mList.add(course);
                        mAdapter.notifyItemInserted(mList.size() - 1);
                    }

                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w(TAG, "Failed to read value.", error.toException());
                }
            });
        }



    }



}
