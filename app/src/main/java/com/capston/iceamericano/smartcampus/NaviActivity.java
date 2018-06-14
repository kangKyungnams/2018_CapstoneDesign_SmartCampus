package com.capston.iceamericano.smartcampus;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.capston.iceamericano.smartcampus.Beacon.BeaconLocation;
import com.capston.iceamericano.smartcampus.Beacon.BeaconService;
import com.capston.iceamericano.smartcampus.Beacon.KalmanFilter;
import com.capston.iceamericano.smartcampus.Trilaterlation.TrilaterationTest;
import com.kongtech.plutocon.sdk.Plutocon;
import com.kongtech.plutocon.sdk.PlutoconManager;

import org.apache.commons.math3.fitting.leastsquares.LeastSquaresOptimizer;
import org.apache.commons.math3.linear.RealVector;

import java.util.ArrayList;
import java.util.List;

public class NaviActivity extends AppCompatActivity implements SensorEventListener {

    Button btn_sign;
    Button btn_stop;
    TextView one;
    TextView two;
    TextView three;
    LinearLayout mArea;
    TextView hie;


    private final int OFFSET_RSSI = -94;
    private final int QUEUESIZE = 4;
    private final int COUNT = 0;
    private final int BEACON_COUNT = 21;
    private PlutoconManager plutoconManager;
    private Plutocon targetPlutocon;
    private int targetRssi = OFFSET_RSSI;
    private boolean isDiscovered;
    private boolean flag = false;
    private Snackbar snackbar;
    private List<Plutocon> plutoconList;
    private int mCount = COUNT;
    private PlutoconAdpater plutoconAdapter;
    private Plutocon[] mPlutocon = new Plutocon[3];
    private boolean[] isDiscover = new boolean[3];
    private boolean isRemoved = true;
    private final static int SPLASH_DELAY = 2000;
    private ComponentName mServiceName;


    //거리측정
    private final double CONSTANT_NUMBER = 2.82;
    private double[] mQueue1 = new double[QUEUESIZE];
    private double[] mQueue2 = new double[QUEUESIZE];
    private double[] mQueue3 = new double[QUEUESIZE];
    private int mRear1 = COUNT;
    private int mRear2 = COUNT;
    private int mRear3 = COUNT;
    private Boolean isFull1 = false;
    private Boolean isFull2 = false;
    private Boolean isFull3 = false;
    private Boolean startQ1 = false;
    private Boolean startQ2 = false;
    private Boolean startQ3 = false;


    private KalmanFilter kalmanFilter1;
    private KalmanFilter kalmanFilter2;
    private KalmanFilter kalmanFilter3;
    private Boolean kalman1 = false;
    private Boolean kalman2 = false;
    private Boolean kalman3 = false;







    //삼각측량
    private double[][] positions;
    private double[] distances;
    private StringBuilder output;

    private RealVector linearCalculatedPosition;
    private LeastSquaresOptimizer.Optimum nonLinearOptimum;

    TrilaterationTest trilaterationTest;

    private double[] mXY;
    AppCompatImageView image;
    AppCompatImageView beacon1;
    AppCompatImageView beacon2;
    AppCompatImageView beacon3;
    AppCompatImageView beacon4;
    AppCompatImageView beacon5;
    AppCompatImageView beacon6, beacon7, beacon8, beacon9, beacon10,beacon11,beacon12,beacon13,beacon14,beacon15,beacon16,beacon17,beacon18,beacon19,beacon20,beacon21;
    AppCompatImageView target;

    // 센서 관련 객체
    SensorManager m_sensor_manager;
    Sensor m_acc_sensor, m_mag_sensor;

    int m_check_count = 0;
    // 출력용 텍스트뷰
    TextView m_check_view;
    TextView m_result_view[] = new TextView[4];

    // 데이터를 저장할 변수들
    float[] m_acc_data = null, m_mag_data = null;
    float[] m_rotation = new float[9];
    float[] m_result_data = new float[3];
    private int mAzimuth = COUNT;
    private int targetX, targetY;


    private BeaconLocation[] beaconLocations;
    private Boolean isTarget;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navi);
        Intent in = getIntent();
        isTarget = true;
        distances = new double[3];
        positions = new double[3][2];
        //{ { 300, 1250 }, { 500, 1100 }, { 330, 1100 } };
        beaconLocations = new BeaconLocation[BEACON_COUNT];


//        beaconLocations[0] = new BeaconLocation("CLASSROOM",300,1250);
//        beaconLocations[1] = new BeaconLocation("CAFETERIA",500,1100);
//        beaconLocations[2] = new BeaconLocation("OFFICE",330,1100);
//        beaconLocations[3] = new BeaconLocation("LAB",500,1250);
//        beaconLocations[4] = new BeaconLocation("COMPUTERROOM",700,1250);
//        beaconLocations[5] = new BeaconLocation("toilet",700,1250);
        beaconLocations[0] = new BeaconLocation("CLASSROOM",490,1100);
        beaconLocations[1] = new BeaconLocation("CAFETERIA",490,950);
        beaconLocations[2] = new BeaconLocation("OFFICE",490,790);
        beaconLocations[3] = new BeaconLocation("LAB",490,615);
        beaconLocations[4] = new BeaconLocation("COMPUTERROOM",490,460);
        beaconLocations[5] = new BeaconLocation("LAB2",490,300);
        beaconLocations[6] = new BeaconLocation("LAB3",900,145);
        beaconLocations[7] = new BeaconLocation("LAB4",690,145);
        beaconLocations[8] = new BeaconLocation("LAB5",490,145);
        beaconLocations[9] = new BeaconLocation("LAB6",295,145);
        beaconLocations[10] = new BeaconLocation("toilet",120,300);
        beaconLocations[11] = new BeaconLocation("LAB7",120,460);
        beaconLocations[12] = new BeaconLocation("LAB8",120,615);
        beaconLocations[13] = new BeaconLocation("LAB9",120,790);
        beaconLocations[14] = new BeaconLocation("LAB10",120,950);
        beaconLocations[15] = new BeaconLocation("LAB11",120,1100);
        beaconLocations[16] = new BeaconLocation("LAB12",295,1250);
        beaconLocations[17] = new BeaconLocation("LAB13",490,1250);
        beaconLocations[18] = new BeaconLocation("LAB14",690,1250);
        beaconLocations[19] = new BeaconLocation("LAB15",900,1250);
        beaconLocations[20] = new BeaconLocation("LAB16",320,535);





        for(int x = 0; x<3 ; x++){
            isDiscover[x] = false;
        }



        btn_sign = findViewById(R.id.btn_sign);
        btn_stop = findViewById(R.id.btn_stop);
        hie = findViewById(R.id.hi);
        mArea = findViewById(R.id.area);
        one = findViewById(R.id.one);
        two = findViewById(R.id.two);
        three  = findViewById(R.id.three);
        image= findViewById(R.id.image2);
        beacon1 = findViewById(R.id.beacon1);
        beacon2 = findViewById(R.id.beacon2);
        beacon3 = findViewById(R.id.beacon3);
        beacon4 = findViewById(R.id.beacon4);
        beacon5 = findViewById(R.id.beacon5);
        beacon6 = findViewById(R.id.beacon6);
        beacon7 = findViewById(R.id.beacon7);
        beacon8 = findViewById(R.id.beacon8);
        beacon9 = findViewById(R.id.beacon9);
        beacon10 = findViewById(R.id.beacon10);
        beacon11 = findViewById(R.id.beacon11);
        beacon12 = findViewById(R.id.beacon12);
        beacon13 = findViewById(R.id.beacon13);
        beacon14 = findViewById(R.id.beacon14);
        beacon15 = findViewById(R.id.beacon15);
        beacon16 = findViewById(R.id.beacon16);
        beacon17 = findViewById(R.id.beacon17);
        beacon18 = findViewById(R.id.beacon18);
        beacon19 = findViewById(R.id.beacon19);
        beacon20 = findViewById(R.id.beacon20);
        beacon21 = findViewById(R.id.beacon21);


        target = findViewById(R.id.targetLocation);
        plutoconAdapter = new PlutoconAdpater();
        ListView listView = findViewById(R.id.list);

        listView.setAdapter(plutoconAdapter);

        plutoconList = new ArrayList<>();
        plutoconManager = new PlutoconManager(this);
        this.setResult(0,null);


//        beacon1.setX(300);
//        beacon1.setY(1250);
//        beacon2.setX(500);
//        beacon2.setY(1100);
//        beacon3.setX(330);
//        beacon3.setY(1100);
//        beacon4.setX(500);
//        beacon4.setY(1250);
//        beacon5.setX(700);
//        beacon5.setY(1250);
//        beacon6.setX(700);
//        beacon6.setY(1250);
        beacon1.setX(490);
        beacon1.setY(1100);
        beacon2.setX(490);
        beacon2.setY(950);
        beacon3.setX(490);
        beacon3.setY(790);
        beacon4.setX(490);
        beacon4.setY(615);
        beacon5.setX(490);
        beacon5.setY(460);
        beacon6.setX(490);
        beacon6.setY(300);
        beacon7.setX(900);
        beacon7.setY(145);
        beacon8.setX(690);
        beacon8.setY(145);
        beacon9.setX(490);
        beacon9.setY(145);
        beacon10.setX(295);
        beacon10.setY(145);
        beacon11.setX(120);
        beacon11.setY(300);

        beacon12.setX(120);
        beacon12.setY(460);
        beacon13.setX(120);
        beacon13.setY(615);
        beacon14.setX(120);
        beacon14.setY(790);
        beacon15.setX(120);
        beacon15.setY(950);
        beacon16.setX(120);
        beacon16.setY(1100);
        beacon17.setX(295);
        beacon17.setY(1250);
        beacon18.setX(490);
        beacon18.setY(1250);
        beacon19.setX(690);
        beacon19.setY(1250);
        beacon20.setX(900);
        beacon20.setY(1250);
        beacon21.setX(320);
        beacon21.setY(535);


        String className = in.getExtras().getString("className");

        if(className.equals("데이터베이스체제")){
            target.setX(540);
            target.setY(1080);
            targetX = 540;
            targetY = 1080;
        }
        else if(className.equals("캡스톤디자인1")){
            target.setX(540);
            target.setY(585);
            targetX = 540;
            targetY = 585;
        }
        else if(className.equals("미적분학및연습1")){
            target.setX(35);
            target.setY(835);
            targetX = 35;
            targetY = 835;
        }
        else if(className.equals("대학영어2(Reading)")){
            target.setX(40);
            target.setY(520);
            targetX = 40;
            targetY = 520;
        }
        else if(className.equals("대학영어2(Conversation)")){
            target.setX(513);
            target.setY(100);
            targetX = 513;
            targetY = 100;
        }




        // 시스템서비스로부터 SensorManager 객체를 얻는다.
        m_sensor_manager = (SensorManager)getSystemService(SENSOR_SERVICE);

        // SensorManager 를 이용해서 가속센서와 자기장 센서 객체를 얻는다.
        m_acc_sensor = m_sensor_manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        m_mag_sensor = m_sensor_manager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        btn_stop.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                stopSrv();
            }
        });
        btn_sign.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startSrv();
            }
        });
    }


    private void enQueue1(double insert){
        if(mRear1==0 && !startQ1){
            startQ1 = true;
        }
        else if (mRear1 == 0 && startQ1) {
            mQueue1[mRear1 % QUEUESIZE] = insert;
            mRear1++;
        }
        else if (mRear1 != 0){
            mQueue1[mRear1 % QUEUESIZE] = insert;
            mRear1++;
        }

        if(mRear1==QUEUESIZE){
            isFull1 = true;
        }
    }

    private void enQueue2(double insert){
        if(mRear2==0 && !startQ2){
            startQ2 = true;
        }
        else if (mRear2 == 0 && startQ2) {
            mQueue2[mRear2 % QUEUESIZE] = insert;
            mRear2++;
        }
        else if (mRear2 != 0){
            mQueue2[mRear2 % QUEUESIZE] = insert;
            mRear2++;
        }
        if(mRear2==QUEUESIZE){
            isFull2 = true;
        }
    }

    private void enQueue3(double insert){
        if(mRear3==0 && !startQ3){
            startQ3 = true;
        }
        else if (mRear3 == 0 && startQ3) {
            mQueue3[mRear3 % QUEUESIZE] = insert;
            mRear3++;
        }
        else if (mRear3 != 0){
            mQueue3[mRear3 % QUEUESIZE] = insert;
            mRear3++;
        }
        if(mRear3==QUEUESIZE){
            isFull3 = true;
        }
    }

    private double avgQueue(double[] queue){
        double sum = 0;
        double max = 0;
        double min = 0;
        int i;
        int j;
        //double[] myQueue = new double[10];
        for(i = 0 ; i < QUEUESIZE ; i++){
            if(i == 0) {
                max = queue[i];
            }
            else{
                if(queue[i] > max){
                    max = queue[i];
                }
            }
        }
//        for(j = 0 ; j < QUEUESIZE ; j++){
//            if(j == 0) {
//                min = queue[j];
//            }
//            else{
//                if(queue[j] < min){
//                    min = queue[j];
//                }
//            }
//        }

        for(i = 0; i < QUEUESIZE; i++){
            sum+=queue[i];
        }
        sum = sum - max;
        return (sum / (QUEUESIZE - 1));
    }

    private void startSrv(){
        mServiceName = startService(new Intent(this, BeaconService.class));
    }

    private void stopSrv(){
        if (null == mServiceName) {
            startSrv();
        }
        Intent intent = new Intent();
        intent.setComponent(mServiceName);
        stopService(intent);

    }

    @Override
    protected void onResume(){
        super.onResume();
        m_check_count = 0;

        // 센서 값을 이 컨텍스트에서 받아볼 수 있도록 리스너를 등록한다.
        m_sensor_manager.registerListener(this, m_acc_sensor, SensorManager.SENSOR_DELAY_UI);
        m_sensor_manager.registerListener(this, m_mag_sensor, SensorManager.SENSOR_DELAY_UI);
    }


    private void startMonitoring() {
        plutoconManager.startMonitoring(PlutoconManager.MONITORING_FOREGROUND, new PlutoconManager.OnMonitoringPlutoconListener() {
            @Override
            public void onPlutoconDiscovered(final Plutocon plutocon, final List<Plutocon> plutocons) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        plutoconList.clear();
                        long ms = System.currentTimeMillis();
                        plutoconList.addAll(plutocons);
                        plutoconAdapter.refresh();

                        if(plutoconAdapter.getCount()>2){
                            for(int i = 0; i < 3; i++) {
//                            while(i < 3) {
//                                if (plutoconAdapter.getPlutocon(i).getMajor() != 1)
                                mPlutocon[i] = plutoconAdapter.getPlutocon(i);
                                isDiscover[i] = true;
                                if (i == 0 && isDiscover[0]) {
                                    double mRssi1 = mPlutocon[i].getRssi();
                                    double mDistance1 = Math.pow(10, (4 - mRssi1) / (10 * CONSTANT_NUMBER));
                                    if (!kalman1) {
                                        kalmanFilter1 = new KalmanFilter(mDistance1);
                                        enQueue1(kalmanFilter1.getX());
                                    } else {
                                        enQueue1(kalmanFilter1.update(mDistance1));
                                        if (isFull1) {
                                            String temp1 = mPlutocon[i].getName();
                                            for (int a = 0; a < BEACON_COUNT; a++) {
                                                if (beaconLocations[a].getName().equals(temp1)) {
                                                    positions[i][0] = beaconLocations[a].getmX();
                                                    positions[i][1] = beaconLocations[a].getmY();
                                                    distances[i] = 0.01 * avgQueue(mQueue1);//kalmanFilter1.update(mDistance1);//
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                    kalman1 = true;
                                }

                                if (i == 1 && isDiscover[1]) {
                                    double mRssi2 = mPlutocon[i].getRssi();
                                    double mDistance2 = Math.pow(10, (4 - mRssi2) / (10 * CONSTANT_NUMBER));
                                    if (!kalman2) {
                                        kalmanFilter2 = new KalmanFilter(mDistance2);
                                        enQueue2(kalmanFilter2.getX());
                                    } else {
                                        enQueue2(kalmanFilter2.update(mDistance2));
                                        if (isFull2) {
                                            String temp2 = mPlutocon[i].getName();
                                            for (int b = 0; b < BEACON_COUNT; b++) {
                                                if (beaconLocations[b].getName().equals(temp2)) {
                                                    positions[i][0] = beaconLocations[b].getmX();
                                                    positions[i][1] = beaconLocations[b].getmY();
                                                    distances[i] = 0.01 * avgQueue(mQueue2);//kalmanFilter2.update(mDistance2);//
                                                }
                                            }
                                        }
                                    }
                                    kalman2 = true;
                                }

                                if (i == 2 && isDiscover[2]) {
                                    double mRssi3 = mPlutocon[i].getRssi();
                                    double mDistance3 = Math.pow(10, (4 - mRssi3) / (10 * CONSTANT_NUMBER));
                                    if (!kalman3) {
                                        kalmanFilter3 = new KalmanFilter(mDistance3);
                                        enQueue3(kalmanFilter3.getX());
                                    } else {
                                        enQueue3(kalmanFilter3.update(mDistance3));
                                        if (isFull3) {
                                            String temp3 = mPlutocon[i].getName();
                                            for (int c = 0; c < BEACON_COUNT; c++) {
                                                if (beaconLocations[c].getName().equals(temp3)) {
                                                    positions[i][0] = beaconLocations[c].getmX();
                                                    positions[i][1] = beaconLocations[c].getmY();
                                                    distances[i] = 0.01 * avgQueue(mQueue3);//kalmanFilter3.update(mDistance3);//
                                                }
                                            }
                                        }
                                    }
                                    kalman3 = true;
                                }

                            }

                            if(kalman1 && kalman3 && kalman2){

                                mXY = new double[2];
                                trilaterationTest = new TrilaterationTest(positions, distances);
                                mXY = trilaterationTest.returnOutput();

                                float realX;
                                float realY;
                                //hie.setText("내 위치 :   " + String.format("%.1f", mXY[0]) + "   " + String.format("%.0f", mXY[1]));

//                                if(190 <= mAzimuth && mAzimuth <= 250){
//                                    mXY[1] = mXY[1] * 0.9;
//                                }
//                                if(250 <= mAzimuth && mAzimuth <= 310){
//                                    mXY[0] = mXY[0] * 1.1;
//                                }
//                                if(90 <= mAzimuth && mAzimuth <= 160){
//                                    mXY[0] = mXY[0] * 0.9;
//                                }
//                                if(0 <= mAzimuth && mAzimuth <= 80){
//                                    mXY[1] = mXY[1] * 1.1;
//                                }




                                if(mXY[1]< 280) {
                                    realY = 230;
                                    image.setX(Float.valueOf(String.format("%.1f", mXY[0]*0.98)));
                                    image.setY(realY);
                                }
                                else if(mXY[1]>1150){
                                    realY = 1200;
                                    image.setX(Float.valueOf(String.format("%.1f", mXY[0]*0.98)));
                                    image.setY(realY);
                                }
                                else if(mXY[1]>280 && mXY[1]<1150){
                                    if(mXY[0]>320){
                                        realX = 470;
                                        image.setX(realX);
                                        image.setY(Float.valueOf(String.format("%.1f", mXY[1] * 1.02)));
                                    }
                                    else if(mXY[0]<320){
                                        realX = 180;
                                        image.setX(realX);
                                        image.setY(Float.valueOf(String.format("%.1f", mXY[1] * 1.02)));
                                    }
                                }
                                else{
                                    image.setX(Float.valueOf(String.format("%.1f", mXY[0] * 0.98)));
                                    image.setY(Float.valueOf(String.format("%.1f", mXY[1] * 1.02)));

                                }


                                if((targetX - 100 < mXY[0] && targetX + 100 > mXY[0]) && (targetY - 100 < mXY[1] && targetY + 100 > mXY[1])){
                                    if(isTarget) {
                                        Toast.makeText(NaviActivity.this, "목적지에 도착하였습니다.", Toast.LENGTH_LONG).show();
                                        isTarget = false;
                                    }

                                }




                            }

                            for(int x = 0 ; x < 3 ; x++){

                                if((mPlutocon[x].getMajor()==1) || ((mPlutocon[x] != null) && ((ms - mPlutocon[x].getLastSeenMillis() >= 2100) || (mPlutocon[x].getRssi() < OFFSET_RSSI)))){
                                    //Toast.makeText(NaviActivity.this,String.valueOf(mPlutocon[x].getRssi()),Toast.LENGTH_SHORT).show();
                                    //((ms - mPlutocon[x].getLastSeenMillis() >= 2100) || (mPlutocon[x].getRssi() < OFFSET_RSSI))
//ms - mPlutocon[x].getLastSeenMillis() >= 2100
                                    isRemoved = false;
                                }
                                if(mPlutocon[x] != null
                                        && isDiscover[x]
                                        && !isRemoved){

                                    for(int j = 0; j<3 ; j++){
                                        isDiscover[j] = false;
                                        mPlutocon[j] = null;
                                    }
                                    kalmanFilter1.resetFilter();
                                    kalmanFilter2.resetFilter();
                                    kalmanFilter3.resetFilter();
                                    kalman1 = false;
                                    kalman2 = false;
                                    kalman3 = false;
                                    isFull1 = false;
                                    isFull2 = false;
                                    isFull3 = false;

                                    mQueue1 = new double[QUEUESIZE];
                                    mQueue2 = new double[QUEUESIZE];
                                    mQueue3 = new double[QUEUESIZE];

                                    isRemoved = true;
                                    plutocons.clear();
                                }
                            }
                        }
                    }
                });
            }
        });
    }


    @Override
    protected void onStart() {
        //온스타트 순서 바꿨음
        super.onStart();
        plutoconManager = new PlutoconManager(this);
        //plutoconManager.connectService(null);
        plutoconManager.connectService(new PlutoconManager.OnReadyServiceListener() {
            @Override
            public void onReady() {
                NaviActivity.this.startMonitoring();
            }
        });

    }

//    @Override
//    public void onBackPressed(){
//        Intent intent = new Intent(this,LoginActivity.class);
//
//        moveTaskToBack(true);
//        startActivity(intent);
//    }

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
                LayoutInflater inflater = LayoutInflater.from(NaviActivity.this);
                convertView = inflater.inflate(R.layout.item_plutocon, parent, false);
            }

            Plutocon plutocon = plutoconList.get(position);

            TextView tvName = (TextView) convertView.findViewById(R.id.deviceName);
            TextView tvAddress = (TextView) convertView.findViewById(R.id.deviceAddress);
            TextView tvRSSI = (TextView) convertView.findViewById(R.id.deviceRSSI);
            TextView tvInterval = (TextView) convertView.findViewById(R.id.deviceInterval);

            tvName.setText(plutocon.getName());
            tvAddress.setText(plutocon.getMacAddress());
            tvRSSI.setText(plutocon.getRssi() + "dBm");
            tvInterval.setText(plutocon.getInterval() + "ms");
            return convertView;
        }
        private void refresh(){
            notifyDataSetChanged();
        }
    }
    public void onSensorChanged(SensorEvent event)
    {
        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            // 가속 센서가 전달한 데이터인 경우
            // 수치 데이터를 복사한다.
            m_acc_data = event.values.clone();
        } else if(event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            // 자기장 센서가 전달한 데이터인 경우
            // 수치 데이터를 복사한다.
            m_mag_data = event.values.clone();
        }

        // 데이터가 존재하는 경우
        if(m_acc_data != null && m_mag_data != null) {
            // 가속 데이터와 자기장 데이터로 회전 매트릭스를 얻는다.
            SensorManager.getRotationMatrix(m_rotation, null, m_acc_data, m_mag_data);
            // 회전 매트릭스로 방향 데이터를 얻는다.
            SensorManager.getOrientation(m_rotation, m_result_data);


            // Radian 값을 Degree 값으로 변환한다.
            m_result_data[0] = (float)Math.toDegrees(m_result_data[0]);

            // 0 이하의 값인 경우 360을 더한다.
            if(m_result_data[0] < 0) m_result_data[0] += 360;

            // 첫번째 데이터인 방위값으로 문자열을 구성하여 텍스트뷰에 출력한다.
            mAzimuth = (int)m_result_data[0];
//            m_result_view[0].setText(str);

//            // 두번째 데이터인 경사도를 Degree 로 변환한 후 문자열을 구성하여 출력한다.
//            str = "pitch(x) : " + (int)Math.toDegrees(m_result_data[1]);
//            m_result_view[1].setText(str);
//
//            // 세번째 데이터인 좌우 회전 값을 Degree 로 변환한 후 문자열을 구성하여 출력한다.
//            str = "roll(y) : " + (int)Math.toDegrees(m_result_data[2]);
//            m_result_view[2].setText(str);

            // 함수의 출력횟수를 텍스트뷰에 출력한다.
//            m_check_count++;
//            str = "호출 횟수 : " + m_check_count + " 회";
//            m_check_view.setText(str);

        }
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy){

    }
}
