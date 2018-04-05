package com.capston.iceamericano.smartcampus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

public class MainActivity extends AppCompatActivity {

    String TAG = "MainActivity";
    private ArrayAdapter adapter;
    private Spinner spinner;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference uReference = FirebaseDatabase.getInstance().getReference();
    DatabaseReference userdata = uReference.child("takingCourseList");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = (Spinner)findViewById(R.id.course_Spinner);

        //접속 계정의 강의 목록 불러오기
        userdata.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                String value = user.getEmail().substring(0,10);
                ArrayList<String> arGeneral = new ArrayList<String>();

                for (DataSnapshot dataSnapshot2: dataSnapshot.getChildren()){

                    String value2 = dataSnapshot2.getKey();
                    Boolean compare = value2.startsWith(value);
                    if(compare)
                    {
                        arGeneral.add(dataSnapshot2.child("title").getValue().toString());
                    }

                    Log.d(TAG, "Value is: " + value2);
                }
                adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, arGeneral);
                spinner.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });


        TextView course_button2 = (TextView)findViewById(R.id.course_button2);
        course_button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent2 = new Intent(MainActivity.this, StudentClassActivity.class);
                MainActivity.this.startActivity(registerIntent2);
            }
        });

    }
}
