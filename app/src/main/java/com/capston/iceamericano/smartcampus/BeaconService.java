package com.capston.iceamericano.smartcampus;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.kongtech.plutocon.sdk.Plutocon;
import com.kongtech.plutocon.sdk.PlutoconManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 경남 on 2018-04-11.
 */

public class BeaconService extends Service {
    private final int OFFSET_RSSI = -100;
    private final int COUNT = 0;
    private final String CLASS_ROOM = "ED:6F:DE:A3:D5:56";
    private final String CAFETERIA = "EB:BA:54:08:89:BB";
    private PlutoconManager plutoconManager;

    private int targetRssi = OFFSET_RSSI;
    private boolean isDiscovered;

    private List<Plutocon> plutoconList;
    private int mCount = COUNT;

    private final static int SPLASH_DELAY = 2000;
    private Handler handler;
    private NotificationCompat.Builder notification;

    private String className;
    private String userCode;

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
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        super.onStartCommand(intent,flags,startId);
        className = intent.getStringExtra("className");
        userCode = intent.getStringExtra("studentNo");
        plutoconManager.connectService(new PlutoconManager.OnReadyServiceListener() {
            @Override
            public void onReady() {
                BeaconService.this.startMonitoring();
            }
        });



        Notification mNoti = new Notification(R.drawable.ic_launcher_foreground,"서비스 실행됨",System.currentTimeMillis());

//출석 중 노티알림
        Notification notiEx = new NotificationCompat.Builder(BeaconService.this)
                .setContentTitle("동국대학교 스마트캠퍼스(" + userCode + ")" )
                .setContentText(className + "과목 출석이 진행중입니다. ("+String.valueOf(mCount)+"%)")
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

                        if (plutocon.getMacAddress().equals(CLASS_ROOM) && plutocon.getRssi() > targetRssi && !isDiscovered) {

                            isDiscovered = true;
                            (new Handler()).postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    if (isDiscovered) {
                                        mCount++;
                                        isDiscovered = false;
                                        Toast msg = Toast.makeText(BeaconService.this, "됐다! "+mCount, Toast.LENGTH_SHORT);
                                        msg.show();
                                        mHandler.sendMessage(Message.obtain(mHandler,1));

                                        //노티 업데이트
                                        Notification notiEx = new NotificationCompat.Builder(BeaconService.this)
                                                .setContentTitle("동국대학교 스마트캠퍼스(" + userCode + ")" )
                                                .setContentText(className + "과목 출석이 진행중입니다. ("+String.valueOf(mCount)+"%)")
                                                .setSmallIcon(R.mipmap.ic_launcher)
                                                .build();
                                        //background에서 foreground로 전환
                                        startForeground(1,notiEx);

                                        if(mCount>10){
                                            stopSelf();     //출석완료시 자동종료
                                        }

                                    }

                                }
                            }, SPLASH_DELAY);
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
}
