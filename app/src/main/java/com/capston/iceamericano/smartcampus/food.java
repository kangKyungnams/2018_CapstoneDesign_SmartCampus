package com.capston.iceamericano.smartcampus;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class food extends AppCompatActivity {

    RecyclerView mRecyclerView;
    LinearLayoutManager mLayoutManager;
    List<foodMenu> mList;
    foodMenuAdapter mAdapter;
    FirebaseDatabase database;
    String TAG = getClass().getSimpleName();
    Button bt_food_list1,bt_food_list2,bt_food_list3, Pur_bt1;
    int res_num1=0,res_num2=0,res_num3=0;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference uReference = FirebaseDatabase.getInstance().getReference();
    DatabaseReference userdata = uReference.child("menu");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);

        database = FirebaseDatabase.getInstance();

        bt_food_list1 = (Button) findViewById(R.id.bt_food_list1);
        bt_food_list2 = (Button) findViewById(R.id.bt_food_list2);
        bt_food_list3 = (Button) findViewById(R.id.bt_food_list3);
        Pur_bt1 = (Button) findViewById(R.id.Pur_bt1);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyle_food_list);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(food.this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mList = new ArrayList<>();
        mAdapter = new foodMenuAdapter(mList,this);
        mRecyclerView.setAdapter(mAdapter);

//        int cut_index;
//        String cut_char = user.getEmail();
//        cut_index = cut_char.indexOf("@");
//        String  value= user.getEmail().substring(0, cut_index);
//        DatabaseReference userLecture = userdata.child(value);

        String value= "coop0001";

        DatabaseReference userLecture = userdata.child(value);
        userLecture.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value1 = dataSnapshot.getValue().toString();
                long now = System.currentTimeMillis();
                Date date = new Date(now);
                SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy-MM-dd");
                String nowDate = sdfNow.format(date);

                for (DataSnapshot dataSnapshot2: dataSnapshot.getChildren()){

                    int cut_index;
                    String cut_char = dataSnapshot2.getKey().toString();
                    cut_index = cut_char.indexOf(":");
                    String foodMenu_date = cut_char.substring(0,cut_index);
                    if(foodMenu_date.equals(nowDate))
                    {
                        String foodMenu_time_section = cut_char.substring(cut_index+1,cut_char.length());

                        for(DataSnapshot dataSnapshot3: dataSnapshot2.getChildren())
                        {
                            String foodMenu_restaurant_name = dataSnapshot3.getKey().toString();
                            String foodMenu_menu_name = dataSnapshot3.child("food_name").getValue().toString();
                            String foodMenu_price = dataSnapshot3.child("price").getValue().toString();

                            foodMenu foodMenu1 =  new foodMenu(foodMenu_restaurant_name,foodMenu_menu_name,foodMenu_price,foodMenu_date,foodMenu_time_section);
                            // [START_EXCLUDE]
                            // Update RecyclerView

                            mList.add(foodMenu1);
                            mAdapter.notifyItemInserted(mList.size() - 1);
                        }

                    }
                    set_visiblity();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        bt_food_list1.setOnClickListener(button_action1);
        bt_food_list2.setOnClickListener(button_action2);
        bt_food_list3.setOnClickListener(button_action3);
        Pur_bt1.setOnClickListener(clear_action);

    }

    void set_visiblity()
    {
        res_num1 =0;
        res_num2 =0;
        res_num3 =0;
        if(mList.size() == 1)
        {
            bt_food_list1.setVisibility(View.VISIBLE);
            bt_food_list1.setText(mList.get(0).getRestaurant_name());
        }
        else if(mList.size() == 2)
        {
            bt_food_list1.setVisibility(View.VISIBLE);
            bt_food_list2.setVisibility(View.VISIBLE);
            bt_food_list1.setText(mList.get(0).getRestaurant_name());
            bt_food_list2.setText(mList.get(1).getRestaurant_name());
        }
        else if(mList.size() == 3)
        {
            bt_food_list1.setVisibility(View.VISIBLE);
            bt_food_list2.setVisibility(View.VISIBLE);
            bt_food_list3.setVisibility(View.VISIBLE);
            bt_food_list1.setText(mList.get(0).getRestaurant_name());
            bt_food_list2.setText(mList.get(1).getRestaurant_name());
            bt_food_list3.setText(mList.get(2).getRestaurant_name());
        }
        else
        {

        }
    }

    Button.OnClickListener button_action1 = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            res_num1++;
            bt_food_list1.setText(String.valueOf(res_num1));
        }
    };
    Button.OnClickListener button_action2 = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            res_num2++;
            bt_food_list2.setText(String.valueOf(res_num2));
        }
    };
    Button.OnClickListener button_action3 = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            res_num3++;
            bt_food_list3.setText(String.valueOf(res_num3));
        }
    };

    Button.OnClickListener clear_action = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            set_visiblity();
        }
    };

}
