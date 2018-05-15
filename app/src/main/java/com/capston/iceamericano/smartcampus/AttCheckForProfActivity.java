package com.capston.iceamericano.smartcampus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;

public class AttCheckForProfActivity extends AppCompatActivity {    //  교수용 출결관리

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_att_check_for_prof);


    }

    Button.OnClickListener register = new Button.OnClickListener() {
        @Override
        public void onClick(View v) { // 출결 정보 버튼 눌러서 넘어가기

            Intent registerIntent = new Intent(AttCheckForProfActivity.this, DatePickerAtt.class);
            AttCheckForProfActivity.this.startActivity(registerIntent);

        }
    };

}
