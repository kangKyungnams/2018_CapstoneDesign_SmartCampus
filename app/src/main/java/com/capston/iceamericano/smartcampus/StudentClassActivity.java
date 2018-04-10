package com.capston.iceamericano.smartcampus;

import android.app.Fragment;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class StudentClassActivity extends AppCompatActivity{
//        implements NavigationView.OnNavigationItemSelectedListener

    private ListView noticeListView;
    private NoticeListAdapter Adapter;
    private List<Notice> noticeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_class);

        noticeListView = (ListView)findViewById(R.id.notice_listview);
        noticeList = new ArrayList<Notice>();
        noticeList.add(new Notice("캡스톤디자인 공지사항입니다","박은찬","2013-10-14"));
        noticeList.add(new Notice("자료구조 PPT","양기주","2013-10-14"));
        noticeList.add(new Notice("컴퓨터알고리즘 과제","김웅섭","2013-10-14"));
        Adapter = new NoticeListAdapter(getApplicationContext(), noticeList);
        noticeListView.setAdapter(Adapter);


        final Button course_button = (Button)findViewById(R.id.course_button);
        final Button schedule_button = (Button)findViewById(R.id.schdule_button);
        final LinearLayout notice = (LinearLayout)findViewById(R.id.notice);



        course_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notice.setVisibility(View.GONE);  //공지사항->강의실로 화면전환
                course_button.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                schedule_button.setBackgroundColor(getResources().getColor(R.color.colorlightBlue));
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, new CourseFragment());
                fragmentTransaction.commit();

            }
        });

        schedule_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notice.setVisibility(View.GONE);  //공지사항->시간표로 화면전환
                course_button.setBackgroundColor(getResources().getColor(R.color.colorlightBlue));
                schedule_button.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, new SchduleFragment());
                fragmentTransaction.commit();
            }
        });


//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();
//
//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);
    }

//    public boolean onNavigationItemSelected (MenuItem item){
//        // Handle navigation view item clicks here.
//        int id = item.getItemId();
//
//        android.app.FragmentManager manager = getFragmentManager();
//
//        if (id == R.id.nav_myinfo) {
//            manager.beginTransaction().replace(R.id.content_main, new Myinfo()).commit();
//        } else if (id == R.id.nav_course) {
//            manager.beginTransaction().replace(R.id.content_main, new Fragment()).commit();
//        } else if (id == R.id.nav_restaurant) {
//            manager.beginTransaction().replace(R.id.content_main, new Restaurant()).commit();
//        } else if (id == R.id.nav_appinfo) {
//            manager.beginTransaction().replace(R.id.content_main, new Appinfo()).commit();
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
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_logout) {
//            Toast.makeText(StudentClassActivity.this, "로그아웃!!", Toast.LENGTH_SHORT).show();
//            return true;
//        } else if (id == R.id.action_settings) {
//            Toast.makeText(StudentClassActivity.this, "설정!!", Toast.LENGTH_SHORT).show();
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
}
