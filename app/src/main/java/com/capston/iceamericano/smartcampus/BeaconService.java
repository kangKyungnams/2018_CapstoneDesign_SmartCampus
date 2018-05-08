package com.capston.iceamericano.smartcampus;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kongtech.plutocon.sdk.Plutocon;
import com.kongtech.plutocon.sdk.PlutoconManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 경남 on 2018-04-11.
 */

public class BeaconService extends Service {
    private final int OFFSET_RSSI = -60;
    private final int COUNT = 0;
    private final String CLASS_ROOM = "ED:6F:DE:A3:D5:56";
    private final String CAFETERIA = "EB:BA:54:08:89:BB";
    private PlutoconManager plutoconManager;

    private int targetRssi = OFFSET_RSSI;
    private boolean isDiscovered;

    private List<Plutocon> plutoconList;
    private int mCount = COUNT;
    private PlutoconAdpater plutoconAdapter;
    private final static int SPLASH_DELAY = 2000;
    private Handler handler;
    private NotificationCompat.Builder notification;
    private Plutocon targetPlutocon;
    private String lectureID, lectureDateKey;

    private FirebaseAuth mAuth;
    DatabaseReference uReference = FirebaseDatabase.getInstance().getReference();
    DatabaseReference attendance = uReference.child("attendance");

    public BeaconService(){

    }
    @Override
    public IBinder onBind(Intent intent){

        //throw new UnsupportedOperationException("Not yet implemented");
        return null;
    }

    @Override
    public void onCreate(){
        super.onCreate();
        handler = new Handler();
        plutoconList = new ArrayList<>();
        plutoconManager = new PlutoconManager(this);
        plutoconAdapter = new PlutoconAdpater();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        super.onStartCommand(intent,flags,startId);
        lectureID = intent.getStringExtra("lectureID");
        lectureDateKey = intent.getStringExtra("lectureDateKey");

        plutoconManager.connectService(new PlutoconManager.OnReadyServiceListener() {
            @Override
            public void onReady() {
                BeaconService.this.startMonitoring();
            }
        });



        Notification mNoti = new Notification(R.drawable.ic_launcher_foreground,"서비스 실행됨",System.currentTimeMillis());

//출석 중 노티알림
        Notification notiEx = new NotificationCompat.Builder(BeaconService.this)
                .setContentTitle("동국대학교 스마트캠퍼스(" + ")" )
                .setContentText(  " 과목 출석이 진행중입니다. ("+String.valueOf(mCount)+"%)")
                .setSmallIcon(R.mipmap.ic_launcher)
                .build();


//노티 실행
        startForeground(1,notiEx);
        return START_REDELIVER_INTENT;//super.onStartCommand(intent, flags, startId);
    }


    public Handler mHandler = new Handler(){ // 핸들러 처리부분
        public void handleMessage(Message msg){
            mHandler.sendEmptyMessageDelayed(0, 5*1000);    // 5초마다 반복함  cf) 1000=1초

        };
    };

    //비콘 모니터링 시작
    private void startMonitoring(){
        plutoconManager.startMonitoring(PlutoconManager.MONITORING_BACKGROUND, new PlutoconManager.OnMonitoringPlutoconListener() {
            @Override
            public void onPlutoconDiscovered(final Plutocon plutocon, final List<Plutocon> plutocons) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        plutoconList.clear();
                        plutoconList.addAll(plutocons);
                        long ms = System.currentTimeMillis();
                        plutoconAdapter.refresh();

                        if (plutocon.getMacAddress().equals(CLASS_ROOM) && plutocon.getRssi() > targetRssi && !isDiscovered) {
                            targetPlutocon = plutocon;
                            isDiscovered = true;
                            //Toast.makeText(BeaconService.this, String.valueOf(ms - plutocon.getLastSeenMillis()), Toast.LENGTH_SHORT).show();
                            (new Handler()).postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    if (isDiscovered) {

                                        mCount+=2;
                                        isDiscovered = false;
//                                        Toast msg = Toast.makeText(BeaconService.this, "됐다! "+mCount, Toast.LENGTH_SHORT);
//                                        msg.show();
                                        mHandler.sendMessage(Message.obtain(mHandler,1));

                                        //노티 업데이트
                                        Notification notiEx = new NotificationCompat.Builder(BeaconService.this)
                                                .setContentTitle("동국대학교 스마트캠퍼스("  + ")" )
                                                .setContentText( "과목 출석이 진행중입니다. ("+String.valueOf(mCount)+"%)")
                                                .setSmallIcon(R.mipmap.ic_launcher)
                                                .build();
                                        //background에서 foreground로 전환
                                        startForeground(1,notiEx);

                                        if(mCount>20 /*|| 교수가 출석 종료시*/  ){
                                            attendance.child(lectureID).child(lectureDateKey).child("status").setValue("출석완료");
                                            stopSelf();
                                            //출석완료시 자동종료
                                        }



                                    }

                                }
                            }, SPLASH_DELAY);

                        }//ms - plutocon.getLastSeenMillis()>10000
                        if(targetPlutocon!=null && ms - targetPlutocon.getLastSeenMillis()>100000) {
                            Toast.makeText(BeaconService.this, String.valueOf(ms - targetPlutocon.getLastSeenMillis()), Toast.LENGTH_SHORT).show();
                            attendance.child(lectureID).child(lectureDateKey).child("status").setValue("무단이탈");
                            stopSelf();
                        }



                    }
                });
            }
        });
    }

    private void runOnUiThread(Runnable runnable){
        handler.post(runnable);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        plutoconManager.close();
    }

    private class PlutoconAdpater extends BaseAdapter {

        @Override
        public int getCount() {
            return plutoconList == null ? 0 : plutoconList.size();
        }

        @Override
        public Object getItem(int position) {
            return plutoconList.get(position);
        }


        @Override
        public long getItemId(int position) {
            return 0;
        }
        public Plutocon getPlutocon(int position){

            Plutocon myPlutocon = plutoconList.get(position);
            return myPlutocon;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
//                LayoutInflater inflater = LayoutInflater.from(SignActivity.this);
//                convertView = inflater.inflate(R.layout.item_plutocon, parent, false);
            }

            Plutocon plutocon = plutoconList.get(position);

//            TextView tvName = (TextView) convertView.findViewById(R.id.deviceName);
//            TextView tvAddress = (TextView) convertView.findViewById(R.id.deviceAddress);
//            TextView tvRSSI = (TextView) convertView.findViewById(R.id.deviceRSSI);
//            TextView tvInterval = (TextView) convertView.findViewById(R.id.deviceInterval);
//
//            tvName.setText(plutocon.getName());
//            tvAddress.setText(plutocon.getMacAddress());
//            tvRSSI.setText(plutocon.getRssi() + "dBm");
//            tvInterval.setText(plutocon.getInterval() + "ms");
            return convertView;
        }
        private void refresh(){
            notifyDataSetChanged();
        }
    }





//    private void drawUI()
}
