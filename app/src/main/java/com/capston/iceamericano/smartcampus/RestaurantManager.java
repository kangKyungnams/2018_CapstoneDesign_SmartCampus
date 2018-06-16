package com.capston.iceamericano.smartcampus;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

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

public class RestaurantManager extends AppCompatActivity {
    long mNow;
    Date mDate;
    SimpleDateFormat mFormat2 = new SimpleDateFormat("hh:mm:ss");

    RecyclerView mRecyclerView1,mRecyclerView2,mRecyclerView3;
    LinearLayoutManager mLayoutManager1,mLayoutManager2,mLayoutManager3;
    List<Restaurant_order> mList1,mList2,mList3;
    Restaurant_order_Adapter mAdapter1,mAdapter2,mAdapter3;
    FirebaseDatabase database;
    String TAG = getClass().getSimpleName();
    String high_level, nowDate;
    DatabaseReference uReference = FirebaseDatabase.getInstance().getReference();
    DatabaseReference userdata = uReference.child("order");
    LinearLayout lay1,lay2,lay3;
    TextView mTextView, tv_restaurant_manager_corner1,tv_restaurant_manager_corner2,tv_restaurant_manager_corner3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_manager);

        Intent in = getIntent();
        high_level = in.getExtras().getString("high_level");

        //bind view
        mTextView = (TextView) findViewById(R.id.textView);
        tv_restaurant_manager_corner1 = (TextView) findViewById(R.id.tv_restaurant_manager_corner1);
        tv_restaurant_manager_corner2 = (TextView) findViewById(R.id.tv_restaurant_manager_corner2);
        tv_restaurant_manager_corner3 = (TextView) findViewById(R.id.tv_restaurant_manager_corner3);
        lay1 = (LinearLayout) findViewById(R.id.restaurant_manager_lay1);
        lay2 = (LinearLayout) findViewById(R.id.restaurant_manager_lay2);
        lay3 = (LinearLayout) findViewById(R.id.restaurant_manager_lay3);


        //리사이클러 뷰

        database = FirebaseDatabase.getInstance();

        mRecyclerView1 = (RecyclerView) findViewById(R.id.restaurant_manager_recycle1);
        mRecyclerView2 = (RecyclerView) findViewById(R.id.restaurant_manager_recycle2);
        mRecyclerView3 = (RecyclerView) findViewById(R.id.restaurant_manager_recycle3);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView1.setHasFixedSize(true);
        mRecyclerView2.setHasFixedSize(true);
        mRecyclerView3.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager1 = new LinearLayoutManager(RestaurantManager.this);
        mLayoutManager2 = new LinearLayoutManager(RestaurantManager.this);
        mLayoutManager3 = new LinearLayoutManager(RestaurantManager.this);
        mRecyclerView1.setLayoutManager(mLayoutManager1);
        mRecyclerView2.setLayoutManager(mLayoutManager2);
        mRecyclerView3.setLayoutManager(mLayoutManager3);

        // specify an adapter (see also next example)
        mList1 = new ArrayList<>();
        mAdapter1 = new Restaurant_order_Adapter(mList1,this);
        mRecyclerView1.setAdapter(mAdapter1);

        mList2 = new ArrayList<>();
        mAdapter2 = new Restaurant_order_Adapter(mList2,this);
        mRecyclerView2.setAdapter(mAdapter2);

        mList3 = new ArrayList<>();
        mAdapter3 = new Restaurant_order_Adapter(mList3,this);
        mRecyclerView3.setAdapter(mAdapter3);


        DatabaseReference userorder = userdata.child(high_level);
        userorder.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value1 = dataSnapshot.getValue().toString();
                long now = System.currentTimeMillis();
                Date date = new Date(now);
                SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy-MM-dd");
                nowDate= sdfNow.format(date);
                int sequence=0;

                try {
                    for (DataSnapshot dataSnapshot2: dataSnapshot.getChildren()){
                        sequence++;
                        if(sequence == 1)
                        {
                            String restaurant_name = dataSnapshot2.getKey().toString();
                            tv_restaurant_manager_corner1.setText(dataSnapshot2.getKey().toString());
                            for(DataSnapshot dataSnapshot3: dataSnapshot2.getChildren())
                            {
                                if(nowDate.equals(dataSnapshot3.getKey().toString()))
                                {
                                    for(DataSnapshot dataSnapshot4: dataSnapshot3.getChildren())
                                    {
                                        int num =1;
                                        String order_menu_name = dataSnapshot4.child("메뉴").getValue().toString();
                                        String bt_order_status = dataSnapshot4.child("status").getValue().toString();
                                        int tv_order_count = Integer.parseInt(dataSnapshot4.child("수량").getValue().toString());
                                        String email = dataSnapshot4.getKey().toString();

                                        Restaurant_order restaurant_order =  new Restaurant_order(order_menu_name,bt_order_status,num,high_level,restaurant_name,nowDate,email,tv_order_count);
                                        // [START_EXCLUDE]
                                        // Update RecyclerView

                                        mList1.add(restaurant_order);
                                        mAdapter1.notifyItemInserted(mList1.size() - 1);
                                        lay1.setVisibility(View.VISIBLE);
                                    }
                                }
                            }
                        }
                        if(sequence == 2)
                        {
                            String restaurant_name = dataSnapshot2.getKey().toString();
                            tv_restaurant_manager_corner2.setText(dataSnapshot2.getKey().toString());
                            for(DataSnapshot dataSnapshot3: dataSnapshot2.getChildren())
                            {
                                if(nowDate.equals(dataSnapshot3.getKey().toString()))
                                {
                                    for(DataSnapshot dataSnapshot4: dataSnapshot3.getChildren())
                                    {
                                        int num =1;
                                        String order_menu_name = dataSnapshot4.child("메뉴").getValue().toString();
                                        String bt_order_status = dataSnapshot4.child("status").getValue().toString();
                                        int tv_order_count = Integer.parseInt(dataSnapshot4.child("수량").getValue().toString());
                                        String email = dataSnapshot4.getKey().toString();

                                        Restaurant_order restaurant_order =  new Restaurant_order(order_menu_name,bt_order_status,num,high_level,restaurant_name,nowDate,email,tv_order_count);
                                        // [START_EXCLUDE]
                                        // Update RecyclerView

                                        mList2.add(restaurant_order);
                                        mAdapter2.notifyItemInserted(mList2.size() - 1);
                                        lay2.setVisibility(View.VISIBLE);
                                    }
                                }
                            }
                        }
                        if(sequence == 3)
                        {
                            String restaurant_name = dataSnapshot2.getKey().toString();
                            tv_restaurant_manager_corner3.setText(dataSnapshot2.getKey().toString());
                            for(DataSnapshot dataSnapshot3: dataSnapshot2.getChildren())
                            {
                                if(nowDate.equals(dataSnapshot3.getKey().toString()))
                                {
                                    for(DataSnapshot dataSnapshot4: dataSnapshot3.getChildren())
                                    {
                                        int num =1;
                                        String order_menu_name = dataSnapshot4.child("메뉴").getValue().toString();
                                        String bt_order_status = dataSnapshot4.child("status").getValue().toString();
                                        int tv_order_count = Integer.parseInt(dataSnapshot4.child("수량").getValue().toString());
                                        String email = dataSnapshot4.getKey().toString();

                                        Restaurant_order restaurant_order =  new Restaurant_order(order_menu_name,bt_order_status,num,high_level,restaurant_name,nowDate,email,tv_order_count);
                                        // [START_EXCLUDE]
                                        // Update RecyclerView

                                        mList3.add(restaurant_order);
                                        mAdapter3.notifyItemInserted(mList3.size() - 1);
                                        lay3.setVisibility(View.VISIBLE);
                                    }
                                }
                            }
                        }
                    }
                }
                catch (Exception e)
                {

                }


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });



    }

    private  String getTime2(){
        mNow = System.currentTimeMillis();
        mDate = new Date(mNow);
        return mFormat2.format(mDate);
    }
}