package com.capston.iceamericano.smartcampus;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RestaurantManager extends AppCompatActivity implements View.OnClickListener{
    long mNow;
    Date mDate;
    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd   hh:mm:ss");
    SimpleDateFormat mFormat2 = new SimpleDateFormat("hh:mm:ss");

    TextView mTextView;
    Button Btn_complete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //bind view
        mTextView = (TextView) findViewById(R.id.textView);
        Btn_complete = (Button) findViewById(R.id.Btn_complete);

        //bind listener
        Btn_complete.setOnClickListener(this);
        handler.sendEmptyMessage(0);
    }

    private  String getTime2(){
        mNow = System.currentTimeMillis();
        mDate = new Date(mNow);
        return mFormat2.format(mDate);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.Btn_complete:
                handler.removeMessages(0);
//                mTextView.setText(getTime2());
                break;
            default:
                break;
        }
    }
    Handler handler = new Handler(){
        public void handleMessage(Message msg){
            handler.sendEmptyMessageDelayed(0, 1000);
            mTextView.setText(getTime2());
            Btn_complete.setText(getTime2());
        }
    };
}