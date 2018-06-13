package com.capston.iceamericano.smartcampus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NoticeView extends AppCompatActivity {

    String TAG = "InfoCourseProf";
    String lectureID,professor;
    EditText ed_notice_enroll_title, ed_notice_enroll_content;
    Button bt_notice_enroll;

    DatabaseReference uReference = FirebaseDatabase.getInstance().getReference();
    DatabaseReference userdata = uReference.child("notice");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_view);

        Intent in = getIntent();
        lectureID = in.getExtras().getString("lectureNameKey");
        professor = in.getExtras().getString("lectureProf");

        ed_notice_enroll_title = (EditText) findViewById(R.id.ed_notice_enroll_title);
        ed_notice_enroll_content = (EditText) findViewById(R.id.ed_notice_enroll_content);
        bt_notice_enroll = (Button) findViewById(R.id.bt_notice_enroll);

        bt_notice_enroll.setOnClickListener(bt_noticeEnroll);


    }


    Button.OnClickListener bt_noticeEnroll = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {

            String ini = userdata.child(lectureID).getDatabase().toString();
            finish();
            
            long now = System.currentTimeMillis();
            Date date = new Date(now);
            SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy-MM-dd");
            String nowDate = sdfNow.format(date);

            userdata.child(lectureID).child(nowDate).child("content").setValue(ed_notice_enroll_content.getText().toString());
            userdata.child(lectureID).child(nowDate).child("title").setValue(ed_notice_enroll_title.getText().toString());
            userdata.child(lectureID).child(nowDate).child("writer").setValue(professor);
            userdata.child(lectureID).child(nowDate).child("time").setValue(nowDate);

        }
    };
}
