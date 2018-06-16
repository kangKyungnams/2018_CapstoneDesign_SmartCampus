package com.capston.iceamericano.smartcampus;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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
    String user_name, high_level, nowDate ;
    Button bt_food_list1,bt_food_list2,bt_food_list3, Pur_bt1,Pur_bt2;
    int res_num1=0,res_num2=0,res_num3=0 , total_price1 =0 ,total_price2 =0, total_price3 =0;
    List<Integer> price_Get;
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
        Pur_bt2 = (Button) findViewById(R.id.Pur_bt2);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyle_food_list);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(food.this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mList = new ArrayList<>();
        price_Get = new ArrayList<>();
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
                nowDate= sdfNow.format(date);

                for (DataSnapshot dataSnapshot2: dataSnapshot.getChildren()){

                    int cut_index;
                    String cut_char = dataSnapshot2.getKey().toString();
                    cut_index = cut_char.indexOf(":");
                    String foodMenu_date = cut_char.substring(0,cut_index);

                    high_level = dataSnapshot.getKey().toString();
                    if(foodMenu_date.equals(nowDate))
                    {
                        String foodMenu_time_section = cut_char.substring(cut_index+1,cut_char.length());

                        for(DataSnapshot dataSnapshot3: dataSnapshot2.getChildren())
                        {
                            String foodMenu_restaurant_name = dataSnapshot3.getKey().toString();
                            String foodMenu_menu_name = dataSnapshot3.child("food_name").getValue().toString();
                            String foodMenu_price = dataSnapshot3.child("price").getValue().toString();
                            price_Get.add(Integer.parseInt(foodMenu_price));

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
        Pur_bt2.setOnClickListener(food_order_action);

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
            total_price1 = price_Get.get(0)*res_num1;
            bt_food_list1.setText(mList.get(0).getRestaurant_name() + "\n" +String.valueOf(total_price1)+"원");
        }
    };
    Button.OnClickListener button_action2 = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            res_num2++;
            total_price2 = price_Get.get(1)*res_num2;
            bt_food_list2.setText(mList.get(1).getRestaurant_name() + "\n" +String.valueOf(total_price2)+"원");
        }
    };
    Button.OnClickListener button_action3 = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            res_num3++;
            total_price3 = price_Get.get(2)*res_num3;
            bt_food_list3.setText(mList.get(2).getRestaurant_name() + "\n" +String.valueOf(total_price3)+"원");
        }
    };

    Button.OnClickListener clear_action = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            set_visiblity();
        }
    };

    Button.OnClickListener food_order_action = new Button.OnClickListener(){
        @Override
        public void onClick(View v) {

            int cut_index;
            String cut_char = user.getEmail();
            cut_index = cut_char.indexOf("@");
            user_name = user.getEmail().substring(0, cut_index);

            final DatabaseReference find_user = uReference.child("user").child(user_name);
            find_user.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.

                    try {
                        if( (total_price1 + total_price2 + total_price3) > Integer.parseInt(dataSnapshot.child("balance").getValue().toString()))
                        {
                            Toast.makeText(food.this, "잔액이 부족합니다.", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            int after_balance = Integer.parseInt(dataSnapshot.child("balance").getValue().toString()) - (total_price1 + total_price2 + total_price3);
                            find_user.child("balance").setValue(after_balance);
                            if(res_num1 != 0)
                            {
                                uReference.child("order").child(high_level).child(mList.get(0).getRestaurant_name()).child(nowDate).child(user_name).child("수량").setValue(res_num1);
                                uReference.child("order").child(high_level).child(mList.get(0).getRestaurant_name()).child(nowDate).child(user_name).child("메뉴").setValue(mList.get(0).menu_name);
                                uReference.child("order").child(high_level).child(mList.get(0).getRestaurant_name()).child(nowDate).child(user_name).child("status").setValue("미완성");
                            }
                            if(res_num2 != 0)
                            {
                                uReference.child("order").child(high_level).child(mList.get(1).getRestaurant_name()).child(nowDate).child(user_name).child("수량").setValue(res_num2);
                                uReference.child("order").child(high_level).child(mList.get(1).getRestaurant_name()).child(nowDate).child(user_name).child("메뉴").setValue(mList.get(1).menu_name);
                                uReference.child("order").child(high_level).child(mList.get(1).getRestaurant_name()).child(nowDate).child(user_name).child("status").setValue("미완성");
                            }
                            if(res_num3 != 0)
                            {
                                uReference.child("order").child(high_level).child(mList.get(2).getRestaurant_name()).child(nowDate).child(user_name).child("수량").setValue(res_num3);
                                uReference.child("order").child(high_level).child(mList.get(2).getRestaurant_name()).child(nowDate).child(user_name).child("메뉴").setValue(mList.get(2).menu_name);
                                uReference.child("order").child(high_level).child(mList.get(2).getRestaurant_name()).child(nowDate).child(user_name).child("status").setValue("미완성");
                            }
                        }
                        set_visiblity();
                        Toast.makeText(food.this, "주문이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                    }
                    catch (Exception e)
                    {
                        Toast.makeText(food.this, "DB 접근 오류", Toast.LENGTH_SHORT).show();
                    }
                }


                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w(TAG, "Failed to read value.", error.toException());
                }
            });

        }
    };

}
