package com.capston.iceamericano.smartcampus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {

    private ArrayAdapter adapter;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        spinner = (Spinner)findViewById(R.id.Major_Spinner);
        adapter = ArrayAdapter.createFromResource(this, R.array.major, android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        TextView registerButton2 = (TextView)findViewById(R.id.registerButton2);
        registerButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent2 = new Intent(RegisterActivity.this, MainActivity.class);
                RegisterActivity.this.startActivity(registerIntent2);
            }
        });
    }
}
