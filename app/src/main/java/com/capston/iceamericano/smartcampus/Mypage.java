package com.capston.iceamericano.smartcampus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Mypage extends AppCompatActivity {



    String TAG = "Mypage";
    //    private ArrayAdapter adapter;
//    private Spinner spinner;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference uReference = FirebaseDatabase.getInstance().getReference();
    DatabaseReference userdata = uReference.child("user");
    private TextView mypage_user_name, mypage_major, mypage_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        mypage_user_name = (TextView)findViewById(R.id.mypage_user_name);
        mypage_major = (TextView)findViewById(R.id.mypage_major);
        mypage_email = (TextView)findViewById(R.id.mypage_email);

        String  value= user.getEmail().substring(0, 10);
        DatabaseReference userMypage = userdata.child(value);

        //접속 계정의 강의 목록 불러오기
        userMypage.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                mypage_user_name.setText(dataSnapshot.child("name").getValue().toString());
                mypage_major.setText(dataSnapshot.child("department_name").getValue().toString());
                mypage_email.setText(dataSnapshot.child("e_mail").getValue().toString());

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }
}
