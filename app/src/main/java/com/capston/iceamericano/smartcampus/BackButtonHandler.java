package com.capston.iceamericano.smartcampus;

import android.app.Activity;
import android.widget.Toast;

/**
 * Created by 경남 on 2018-06-05.
 */



public class BackButtonHandler {

    private long backKeyPressedTime = 0;
    private Toast toast;
    private Activity activity;

    public BackButtonHandler(Activity activity) {
        this.activity = activity;
        toast = Toast.makeText(activity, "뒤로가기 버튼을 한 번 더 누르면 종료됩니다.", Toast.LENGTH_LONG);
    }

    public void onBackPressed(){
        if(System.currentTimeMillis() > backKeyPressedTime + 2000){
            backKeyPressedTime = System.currentTimeMillis();
            toast.show();
            return;
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000){
            activity.finishAffinity();
            toast.cancel();
        }
    }

}