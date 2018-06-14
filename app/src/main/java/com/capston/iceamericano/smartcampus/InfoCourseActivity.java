package com.capston.iceamericano.smartcampus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.capston.iceamericano.smartcampus.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class InfoCourseActivity extends AppCompatActivity {

    String TAG = "InfoCourseActivity";
    String lectureID;
    TextView lecture_info_courseName, lecture_info_courseID, lecture_info_type, lecture_info_credit, lecture_info_professor, lecture_info_classroom;
    Button bt_info_course_attCheck, bt_info_course_noticeConfirm, btn_navi;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference uReference = FirebaseDatabase.getInstance().getReference();
    DatabaseReference userdata = uReference.child("takingCourseList");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_course);

        Intent in = getIntent();
        lectureID = in.getExtras().getString("lectureIDKey");

        lecture_info_courseName = (TextView)findViewById(R.id.lecture_info_courseName);
        lecture_info_courseID = (TextView)findViewById(R.id.lecture_info_courseID);
        lecture_info_type = (TextView)findViewById(R.id.lecture_info_type);
        lecture_info_credit = (TextView)findViewById(R.id.lecture_info_credit);
        lecture_info_professor = (TextView)findViewById(R.id.lecture_info_professor);
        lecture_info_classroom = (TextView)findViewById(R.id.lecture_info_classroom);
        lecture_info_courseID.setText(lectureID);

        bt_info_course_attCheck = (Button) findViewById(R.id.bt_info_course_attCheck);
        bt_info_course_noticeConfirm = (Button) findViewById(R.id.bt_info_course_noticeConfirm);
        btn_navi = (Button)findViewById(R.id.btn_navi2);

        String  value= user.getEmail().substring(0, 10);
        DatabaseReference userLecture = userdata.child(value);

        //접속 계정의 강의 목록 불러오기
        userLecture.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                lecture_info_courseName.setText(dataSnapshot.child(lectureID).child("title").getValue().toString());
//                 lecture_info_courseID.setText(dataSnapshot2.child("category").getValue().toString());
                lecture_info_type.setText(dataSnapshot.child(lectureID).child("category").getValue().toString());
                lecture_info_credit.setText(dataSnapshot.child(lectureID).child("credit").getValue().toString());
                lecture_info_professor.setText(dataSnapshot.child(lectureID).child("professor_name").getValue().toString());
                lecture_info_classroom.setText(dataSnapshot.child(lectureID).child("location").child("classroom").getValue().toString());

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        btn_navi.setOnClickListener(btn_navi2);
        bt_info_course_attCheck.setOnClickListener(bt_attCheck);
        bt_info_course_noticeConfirm.setOnClickListener(bt_noticeConfirm);
    }


    Button.OnClickListener bt_attCheck = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent attCheck = new Intent(InfoCourseActivity.this, AttCheckActivity.class);
            attCheck.putExtra("lectureNameKey",lecture_info_courseID.getText().toString());
            attCheck.putExtra("lectureName", lecture_info_courseName.getText().toString());
            InfoCourseActivity.this.startActivity(attCheck);
        }
    };

    Button.OnClickListener bt_noticeConfirm = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent noticeConfirm = new Intent(InfoCourseActivity.this, NoticeInCourse.class);
            noticeConfirm.putExtra("lectureNameKey",lecture_info_courseID.getText().toString());
            InfoCourseActivity.this.startActivity(noticeConfirm);
        }
    };
    Button.OnClickListener btn_navi2 = new Button.OnClickListener(){
        @Override
        public void onClick(View v){
            Intent navi = new Intent(InfoCourseActivity.this, NaviActivity.class);
            navi.putExtra("className",lecture_info_courseName.getText().toString());
            InfoCourseActivity.this.startActivity(navi);

        }
    };
}
