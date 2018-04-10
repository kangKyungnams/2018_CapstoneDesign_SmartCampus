package com.capston.iceamericano.smartcampus;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
public class Restaurant extends AppCompatActivity {

    private ArrayAdapter RestaurantAdapter;
    private Spinner RestaurantSpinner;
    View v;


        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
            v = inflater.inflate(R.layout.activity_restaurant, container, false);
            return v;
        }

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            RestaurantSpinner = (Spinner) findViewById(R.id.course_Spinner);
        }

        public void onDataChange(DataSnapshot dataSnapshot) {
            ArrayList<String> arGeneral = new ArrayList<String>();
            RestaurantAdapter = new ArrayAdapter<String>(Restaurant.this, android.R.layout.simple_spinner_dropdown_item, arGeneral);
            RestaurantSpinner.setAdapter(RestaurantAdapter);
        }


}
