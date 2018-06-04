package com.capston.iceamericano.smartcampus;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.capston.iceamericano.smartcampus.Beacon.BeaconService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kongtech.plutocon.sdk.Plutocon;
import com.kongtech.plutocon.sdk.PlutoconManager;

import java.util.ArrayList;
import java.util.List;

public class AttCheckActivity extends AppCompatActivity {

    ComponentName mServiceName;
    PlutoconManager plutoconManager;
    Button attStart;
    private List<Plutocon> plutoconList;

    String att_adapter_name, att_adapter_date, att_adapter_state, compare1;

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
        attStart = findViewById(R.id.attStart);


        plutoconList = new ArrayList<>();
        plutoconManager = new PlutoconManager(this);

        attStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(교수가 출석 버튼을 눌렀다면){
                    startSrv();
//                }
//                else{
//                    Toast msg = Toast.makeText(AttCheckActivity.this, "현재 진행중인 수업이 아닙니다.", Toast.LENGTH_SHORT);
//                    msg.show();
//                }
            }
        });



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

                    compare1 = dataSnapshot2.getKey().toString().substring(0,10);
                    String lectureDateKey = dataSnapshot2.getKey().toString();

                    if( compare2.equals(compare1))
                    {
                        att_adapter_num++;
                        att_adapter_name = dataSnapshot2.child("name").getValue().toString();
                        att_adapter_date = dataSnapshot2.child("date").getValue().toString();
                        att_adapter_state = dataSnapshot2.child("status").getValue().toString();

                        AttState stateAtt =  new AttState(att_adapter_num,att_adapter_name,att_adapter_date,att_adapter_state,lectureDateKey,lectureID);
                        mList.add(stateAtt);

                    }

                }

                mAdapter.notifyItemInserted(mList.size() - 1);
                mAdapter.notifyItemChanged(mList.size() - 1);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

    }


    private void startSrv(){
//        mServiceName = startService(new Intent(this, BeaconService.class));

        Intent mIntent = new Intent(this, BeaconService.class);
        mIntent.putExtra("studentNo","2013112071");
        mIntent.putExtra("className","캡스톤디자인1");

        mServiceName = startService(mIntent);


    }

    private void stopSrv(){

        startSrv();
        Intent intent = new Intent();
        intent.setComponent(mServiceName);
        stopService(intent);

    }
    @Override
    public void onResume(){
        //실행중인 서비스를 찾아서 비콘서비스가 실행중이면 서비스액티비티에서 plutoconManager를 실행하지 않게하여 비콘모니터링의 충돌을 제거한다.
        if(!getServiceTaskName()) {

            super.onResume();
            Toast msg = Toast.makeText(AttCheckActivity.this, "못빠져나옴", Toast.LENGTH_SHORT);
            msg.show();
            plutoconManager.connectService(new PlutoconManager.OnReadyServiceListener() {
                @Override
                public void onReady() {
                    AttCheckActivity.this.startMonitoring();
                }
            });
        }
        else{
            super.onResume();
            Toast msg = Toast.makeText(AttCheckActivity.this, "빠져나옴", Toast.LENGTH_SHORT);
            msg.show();
        }

    }
    //서비스를 활성화 시키기 위해 껍데기만 존재하는 모니터링 함수
    private void startMonitoring() {
        plutoconManager.startMonitoring(PlutoconManager.MONITORING_FOREGROUND, new PlutoconManager.OnMonitoringPlutoconListener() {
            @Override
            public void onPlutoconDiscovered(final Plutocon plutocon, final List<Plutocon> plutocons) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }
        });
    }

    @Override
    public void onStart(){
        plutoconManager = new PlutoconManager(this);
        plutoconManager.connectService(null);
        super.onStart();
    }

    //실행중인 서비스에 비콘서비스가 있는지 확인
    private boolean getServiceTaskName() {

        boolean checked = false;
        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> info;
        info = am.getRunningServices(100);

        for(int i=0; i<info.size(); i++){
            ActivityManager.RunningServiceInfo rsi = info.get(i);
            Log.d("run service","Package Name : " + rsi.service.getPackageName());
            Log.d("run service","Class Name : " + rsi.service.getClassName());
            if(rsi.service.getClassName().equals("com.capston.iceamericano.smartcampus.Beacon.BeaconService")){
                checked = true;
            }

        }

        return checked;
    }



}
