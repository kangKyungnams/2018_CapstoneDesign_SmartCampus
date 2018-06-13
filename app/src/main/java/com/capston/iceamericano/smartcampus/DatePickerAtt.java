package com.capston.iceamericano.smartcampus;

import android.app.DatePickerDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kongtech.plutocon.sdk.Plutocon;
import com.kongtech.plutocon.sdk.PlutoconManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DatePickerAtt extends AppCompatActivity {     //교수용 출결에서 날짜별 출결보기

    Calendar dateTime = Calendar.getInstance();
    private TextView text;
    private Button btn_date;

    String att_adapter_name, att_adapter_date, att_adapter_state;

    RecyclerView mRecyclerView;
    LinearLayoutManager mLayoutManager;
    List<AttState> mList;
    AttAdapter mAdapter;
    FirebaseDatabase database;
    String lectureID,TAG = getClass().getSimpleName(), lectureName,attTime;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference uReference = FirebaseDatabase.getInstance().getReference();
    DatabaseReference userdata = uReference.child("attendance");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_picker_att);


        //선택한 날짜에 맞추어 날짜 표시 , DatePicker
        text = (TextView)findViewById(R.id.txt_TextDateTime);
        btn_date = (Button)findViewById(R.id.txt_ButtonDateTime);

        btn_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDate();
            }
        });

        updateTextLable();


        //Recycler View 데이터 연동
        Intent in = getIntent();
        lectureID = in.getExtras().getString("lectureNameKey");
        lectureName = in.getExtras().getString("lectureName");
        attTime = in.getExtras().getString("attTime");


        database = FirebaseDatabase.getInstance();

        mRecyclerView = (RecyclerView) findViewById(R.id.att_recyclerView_prof);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(DatePickerAtt.this);
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
                int att_adapter_num = 0;

                for (DataSnapshot dataSnapshot2: dataSnapshot.getChildren()){

                    String lectureDateKey = dataSnapshot2.getKey().toString();
                    if( text.getText().toString().equals(dataSnapshot2.child("date").getValue().toString()))
                    {
                        att_adapter_num++;
                        att_adapter_name = dataSnapshot2.child("name").getValue().toString();
                        att_adapter_date = dataSnapshot2.child("date").getValue().toString();
                        att_adapter_state = dataSnapshot2.child("status").getValue().toString();

                        AttState stateAtt =  new AttState(att_adapter_num,att_adapter_name,att_adapter_date,att_adapter_state,lectureDateKey,lectureID);
                        mList.add(stateAtt);

                    }
                }

                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                mAdapter.notifyItemRangeRemoved(0,mList.size());

                DatabaseReference userLecture = userdata.child(lectureID);
                userLecture.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.
                        String value1 = dataSnapshot.getValue().toString();
                        int att_adapter_num = 0;

                        for (DataSnapshot dataSnapshot2: dataSnapshot.getChildren()){

                            String lectureDateKey = dataSnapshot2.getKey().toString();
                            if( text.getText().toString().equals(dataSnapshot2.child("date").getValue().toString()))
                            {
                                att_adapter_num++;
                                att_adapter_name = dataSnapshot2.child("name").getValue().toString();
                                att_adapter_date = dataSnapshot2.child("date").getValue().toString();
                                att_adapter_state = dataSnapshot2.child("status").getValue().toString();

                                AttState stateAtt =  new AttState(att_adapter_num,att_adapter_name,att_adapter_date,att_adapter_state,lectureDateKey,lectureID);
                                mList.add(stateAtt);

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

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void updateDate(){
        new DatePickerDialog(this, d, dateTime.get(Calendar.YEAR), dateTime.get(Calendar.MONTH), dateTime.get(Calendar.DAY_OF_MONTH)).show();
    }

    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateTime.set(Calendar.YEAR, year);
            dateTime.set(Calendar.MONTH, monthOfYear);
            dateTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateTextLable();
        }
    };

    public void updateTextLable(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        text.setText(sdf.format(dateTime.getTime()));
    }

}
