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

public class AttCheckActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    LinearLayoutManager mLayoutManager;
    List<AttState> mList;
    AttAdapter mAdapter;
    FirebaseDatabase database;
    String lectureID,TAG = getClass().getSimpleName(), lectureName;
    TextView tv_att_check_lectureName;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference uReference = FirebaseDatabase.getInstance().getReference();
    DatabaseReference userdata = uReference.child("attendance");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_att_check);

        Intent in = getIntent();
        lectureID = in.getExtras().getString("lectureNameKey");
        lectureName = in.getExtras().getString("lectureName");
        tv_att_check_lectureName = (TextView) findViewById(R.id.tv_att_check_lectureName);

        tv_att_check_lectureName.setText(lectureName);

        database = FirebaseDatabase.getInstance();

        mRecyclerView = (RecyclerView) findViewById(R.id.att_recyclerView);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(AttCheckActivity.this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mList = new ArrayList<>();
        mAdapter = new AttAdapter(mList,this);
        mRecyclerView.setAdapter(mAdapter);


        DatabaseReference userLecture = userdata.child(lectureID);
        userLecture.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value1 = dataSnapshot.getValue().toString();
                String compare2 = user.getEmail().substring(0,10);
                int att_adapter_num = 0;

                for (DataSnapshot dataSnapshot2: dataSnapshot.getChildren()){



                    String compare1 = dataSnapshot2.getKey().toString().substring(0,10);

                    if( compare2.equals(compare1))
                    {
                        att_adapter_num++;
                        String att_adapter_name = dataSnapshot2.child("name").getValue().toString();
                        String att_adapter_date = dataSnapshot2.child("date").getValue().toString();
                        String att_adapter_state = dataSnapshot2.child("status").getValue().toString();

                        AttState stateAtt =  new AttState(att_adapter_num,att_adapter_name,att_adapter_date,att_adapter_state);
                        mList.add(stateAtt);

                    }

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
