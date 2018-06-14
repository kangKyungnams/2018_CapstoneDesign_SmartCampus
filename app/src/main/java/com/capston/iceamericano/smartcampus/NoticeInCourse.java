package com.capston.iceamericano.smartcampus;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class NoticeInCourse extends AppCompatActivity {

    RecyclerView mRecyclerView;
    LinearLayoutManager mLayoutManager;
    List<Notice> mList;
    NoticeListAdapter mAdapter;
    FirebaseDatabase database;
    String lectureID,TAG = getClass().getSimpleName();
    TextView tv_att_check_lectureName;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference uReference = FirebaseDatabase.getInstance().getReference();
    DatabaseReference userdata = uReference.child("notice");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notice_in_course);

        Intent in = getIntent();
        lectureID = in.getExtras().getString("lectureNameKey");

        database = FirebaseDatabase.getInstance();

        mRecyclerView = (RecyclerView) findViewById(R.id.notice_in_course_recyclerView);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(NoticeInCourse.this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mList = new ArrayList<>();
        mAdapter = new NoticeListAdapter(mList,this);
        mRecyclerView.setAdapter(mAdapter);


        if(!userdata.child(lectureID).toString().isEmpty())
        {
            DatabaseReference userNotice = userdata.child(lectureID);
            userNotice.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.

                    for (DataSnapshot dataSnapshot2: dataSnapshot.getChildren()){
                        String notice_time = dataSnapshot2.child("time").getValue().toString();
                        String notice_title = dataSnapshot2.child("title").getValue().toString();
                        String notice_writer = dataSnapshot2.child("writer").getValue().toString();
                        Notice notice_list =  new Notice(notice_writer,notice_title,notice_time);
                        mList.add(notice_list);

                    }

                    mAdapter.notifyItemInserted(mList.size() - 1);

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
