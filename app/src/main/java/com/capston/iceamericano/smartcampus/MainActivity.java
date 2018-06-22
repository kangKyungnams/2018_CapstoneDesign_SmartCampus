package com.capston.iceamericano.smartcampus;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

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

public class MainActivity extends AppCompatActivity{
//        implements NavigationView.OnNavigationItemSelectedListener{


    //비콘
    private final int OFFSET_RSSI = -80;
    private final int COUNT = 0;
    private final String CLASS_ROOM = "ED:6F:DE:A3:D5:56";
    private PlutoconManager plutoconManager;
    private Plutocon targetPlutocon;
    private int targetRssi = OFFSET_RSSI;
    private boolean isDiscovered;
    private boolean flag = false;
    private Snackbar snackbar;
    private List<Plutocon> plutoconList;
    private int mCount = COUNT;
    LinearLayout mArea;
    private final static int SPLASH_DELAY = 7000;

    private BackButtonHandler backButtonHandler;



    String TAG = "MainActivity";
    String IDtype;
    ArrayList<String> takes;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference uReference = FirebaseDatabase.getInstance().getReference();
    DatabaseReference userdata = uReference.child("user");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(!getServiceTaskName()) {
            mArea = findViewById(R.id.linearMain);
            plutoconList = new ArrayList<>();
            plutoconManager = new PlutoconManager(this);
            this.setResult(0, null);
        }


        takes = new ArrayList<>();

        backButtonHandler = new BackButtonHandler(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();


        int cut_index;
        String cut_char = user.getEmail();
        cut_index = cut_char.indexOf("@");
        String  value= user.getEmail().substring(0, cut_index);
        DatabaseReference userLecture = userdata.child(value);




        //접속 계정의 강의 목록 불러오기
        userLecture.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                IDtype = dataSnapshot.child("type").getValue().toString();
                if(IDtype.equals("manager"))
                {
                    String high_level = dataSnapshot.child("cafeteria_id").getValue().toString();
                    Intent manager_mode = new Intent(MainActivity.this, RestaurantManager.class);
                    manager_mode.putExtra("high_level",high_level);
                    MainActivity.this.startActivity(manager_mode);
                    finish();
                }
                if(IDtype.equals("professor"))
                {
                    for(DataSnapshot dataSnapshot2: dataSnapshot.child("takes").getChildren())
                    {
                        String value2 = dataSnapshot2.getValue().toString();
                        Log.d(TAG, "Value is: " + value2);
                        String notice = dataSnapshot2.getValue(String.class);
                        takes.add(notice);
                    }
                }
            }


            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });


        Button course_button = (Button) findViewById(R.id.course_button2);
        Button restaurant_button = (Button) findViewById(R.id.restaurant_button);
        Button my_button = (Button) findViewById(R.id.my_button);

        course_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Intent1 = new Intent(MainActivity.this, CourseList.class);
                Intent1.putExtra("IDtype",IDtype);
                if (IDtype.equals("professor"))
                {
                    Intent1.putStringArrayListExtra("takes", takes);
                }
                MainActivity.this.startActivity(Intent1);
            }
        });
        restaurant_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Intent2 = new Intent(MainActivity.this, Restaurant.class);
                MainActivity.this.startActivity(Intent2);
            }
        });
        my_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Intent3 = new Intent(MainActivity.this, Mypage.class);
                MainActivity.this.startActivity(Intent3);
            }
        });

    }
//    public boolean onNavigationItemSelected (MenuItem item){
//        int id = item.getItemId();
//
//
//
//        if (id == R.id.nav_myinfo) {
//            Intent myinfo1 = new Intent(MainActivity.this, Mypage.class);
//            MainActivity.this.startActivity(myinfo1);
//        } else if (id == R.id.nav_course) {
//            Intent course1 = new Intent(MainActivity.this, Course.class);
//            MainActivity.this.startActivity(course1);
//        } else if (id == R.id.nav_restaurant) {
//            Intent restaurant1 = new Intent(MainActivity.this, Restaurant.class);
//            MainActivity.this.startActivity(restaurant1);
//        } else if (id == R.id.nav_appinfo) {
//            Intent appinfo1 = new Intent(MainActivity.this, Appinfo.class);
//            MainActivity.this.startActivity(appinfo1);
//        }
//
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);
//        return true;
//    }
//    public boolean onCreateOptionsMenu (Menu menu){
//        // Inflate the menu; this adds items to the action bar if it is present.
//
//        MenuInflater inflater = getMenuInflater();
//
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//    @Override
//    public boolean onOptionsItemSelected (MenuItem item){
//
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_logout) {
//            Toast.makeText(MainActivity.this, "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();
//
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }


    @Override
    protected void onResume(){
        super.onResume();
        if(!getServiceTaskName()) {
            plutoconManager.connectService(new PlutoconManager.OnReadyServiceListener() {
                @Override
                public void onReady() {
                    MainActivity.this.startMonitoring();
                }
            });
        }

    }

    private void startMonitoring() {
        plutoconManager.startMonitoring(PlutoconManager.MONITORING_FOREGROUND, new PlutoconManager.OnMonitoringPlutoconListener() {
            @Override
            public void onPlutoconDiscovered(final Plutocon plutocon, final List<Plutocon> plutocons) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("beaconTEST","실행");
                        plutoconList.clear();
                        plutoconList.addAll(plutocons);

//targetPlutocon.getMacAddress())
                        if (plutocon.getMajor()==13 && plutocon.getRssi() > targetRssi && !isDiscovered) {

                            isDiscovered = true;
                            if(flag == false) {
                                snackbar = Snackbar.make(mArea, "강의실로 이동하시겠습니까?", Snackbar.LENGTH_INDEFINITE).setActionTextColor(Color.WHITE);

                                snackbar.setActionTextColor(Color.WHITE);

                                snackbar.setAction("OK", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        isDiscovered = false;
                                        flag = true;
                                    }
                                });
                                snackbar.show();
                            }
                            (new Handler()).postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    if (isDiscovered == true) {
                                        isDiscovered = false;
                                        snackbar.dismiss();
                                        flag = true;
                                    }
                                }
                            }, SPLASH_DELAY);
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

    @Override
    public void onBackPressed(){
        backButtonHandler.onBackPressed();
    }
}
