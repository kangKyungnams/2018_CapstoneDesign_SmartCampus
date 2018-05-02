package com.capston.iceamericano.smartcampus;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
public class Restaurant extends AppCompatActivity {

    private Button bt_restaurant_order, bt_restaurant_charge, bt_restaurant_mypage;
//    View v;
//
//
//        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
//            v = inflater.inflate(R.layout.activity_restaurant, container, false);
//            return v;
//        }
//
        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_restaurant);
//            RestaurantSpinner = (Spinner) findViewById(R.id.course_Spinner);

            bt_restaurant_order = (Button) findViewById(R.id.bt_restaurant_order);
            bt_restaurant_charge = (Button) findViewById(R.id.bt_restaurant_charge);
            bt_restaurant_mypage = (Button) findViewById(R.id.bt_restaurant_mypage);


            bt_restaurant_order.setOnClickListener(order);
            bt_restaurant_charge.setOnClickListener(charge);
        }

    Button.OnClickListener order = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent orderIntent = new Intent(Restaurant.this, PurchaseRestaurant.class);
            Restaurant.this.startActivity(orderIntent);

        }
    };

    Button.OnClickListener charge = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent chargeIntent = new Intent(Restaurant.this, Cash.class);
            Restaurant.this.startActivity(chargeIntent);

        }
    };

//    Button.OnClickListener mypage = new Button.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//
//            Intent mypageIntent = new Intent(Restaurant.this, RegisterActivity.class);
//            LoginActivity.this.startActivity(registerIntent);
//
//        }
//    };

}
