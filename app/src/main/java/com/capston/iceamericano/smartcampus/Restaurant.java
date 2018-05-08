package com.capston.iceamericano.smartcampus;

import android.app.ActivityManager;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.kongtech.plutocon.sdk.Plutocon;
import com.kongtech.plutocon.sdk.PlutoconManager;

import java.util.ArrayList;
import java.util.List;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class Restaurant extends AppCompatActivity {

    private final int OFFSET_RSSI = -50;
    private final int COUNT = 0;
    private PlutoconManager plutoconManager;
    private Plutocon targetPlutocon;
    private int targetRssi = OFFSET_RSSI;
    private boolean isDiscovered;
    private boolean flag = false;
    private List<Plutocon> plutoconList;
    private int mCount = COUNT;
    private final static int SPLASH_DELAY = 2000;



    private Button bt_restaurant_order, bt_restaurant_charge, bt_restaurant_mypage;
//    View v;
//
//
//        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
//            v = inflater.inflate(R.layout.activity_restaurant, container, false);
//            return v;
//        }
//
        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_restaurant);
//            RestaurantSpinner = (Spinner) findViewById(R.id.course_Spinner);
            if(!getServiceTaskName()) {
                plutoconList = new ArrayList<>();
                plutoconManager = new PlutoconManager(this);
                this.setResult(0, null);
            }
            isDiscovered = false;

            bt_restaurant_order = (Button) findViewById(R.id.bt_restaurant_order);
            bt_restaurant_charge = (Button) findViewById(R.id.bt_restaurant_charge);
            bt_restaurant_mypage = (Button) findViewById(R.id.bt_restaurant_mypage);


            bt_restaurant_order.setOnClickListener(order);
            bt_restaurant_charge.setOnClickListener(charge);
        }

    Button.OnClickListener order = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(isDiscovered ) {
                isDiscovered = false;
                Intent orderIntent = new Intent(Restaurant.this, PurchaseRestaurant.class);
                Restaurant.this.startActivity(orderIntent);
            }
            else{
                Toast msg = Toast.makeText(Restaurant.this, "주변에 식당이 존재하지않습니다", Toast.LENGTH_SHORT);
                msg.show();
            }

        }
    };

    Button.OnClickListener charge = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent chargeIntent = new Intent(Restaurant.this, Cash.class);
            Restaurant.this.startActivity(chargeIntent);

        }
    };


    private void startMonitoring() {
        plutoconManager.startMonitoring(PlutoconManager.MONITORING_FOREGROUND, new PlutoconManager.OnMonitoringPlutoconListener() {
            @Override
            public void onPlutoconDiscovered(final Plutocon plutocon, final List<Plutocon> plutocons) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        plutoconList.clear();
                        plutoconList.addAll(plutocons);
                        long ms = System.currentTimeMillis();



                        if (plutocon.getName().equals("CAFETERIA") && plutocon.getRssi() > targetRssi && !isDiscovered) {
                            isDiscovered = true;
                            targetPlutocon = plutocon;
                            plutocons.clear();
                        }
                        if(targetPlutocon!=null && targetPlutocon.getRssi() < targetRssi){
                            isDiscovered = false;
                            targetPlutocon = null;
                        }
                    }
                });
            }
        });
    }


    @Override
    protected void onStart() {
        if(!getServiceTaskName()) {
            plutoconManager = new PlutoconManager(this);
            plutoconManager.connectService(null);
        }
        super.onStart();

    }


    @Override
    protected void onResume(){
        super.onResume();
        if(!getServiceTaskName()) {
            plutoconManager.connectService(new PlutoconManager.OnReadyServiceListener() {
                @Override
                public void onReady() {
                    Restaurant.this.startMonitoring();
                }
            });
        }

    }


    private boolean getServiceTaskName() {

        boolean checked = false;
        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> info;
        info = am.getRunningServices(100);

        for(int i=0; i<info.size(); i++){
            ActivityManager.RunningServiceInfo rsi = info.get(i);
            Log.d("run service","Package Name : " + rsi.service.getPackageName());
            Log.d("run service","Class Name : " + rsi.service.getClassName());
            if(rsi.service.getClassName().equals("com.capston.iceamericano.smartcampus.BeaconService")){
                checked = true;
            }

        }

        return checked;
    }
//    Button.OnClickListener mypage = new Button.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//
//            Intent mypageIntent = new Intent(Restaurant.this, RegisterActivity.class);
//            LoginActivity.this.startActivity(registerIntent);
//
//        }
//    };

}
