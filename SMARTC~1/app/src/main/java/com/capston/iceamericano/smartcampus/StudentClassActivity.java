package com.capston.iceamericano.smartcampus;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class StudentClassActivity extends AppCompatActivity {

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

    }
}
