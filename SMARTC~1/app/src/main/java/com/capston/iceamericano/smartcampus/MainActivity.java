package com.capston.iceamericano.smartcampus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private ArrayAdapter adapter;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = (Spinner)findViewById(R.id.course_Spinner);
        adapter = ArrayAdapter.createFromResource(this, R.array.course, android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

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
