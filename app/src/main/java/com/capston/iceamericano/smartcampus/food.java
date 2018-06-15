package com.capston.iceamericano.smartcampus;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

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
    String IDtype;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference uReference = FirebaseDatabase.getInstance().getReference();
    DatabaseReference userdata = uReference.child("menu");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);

        database = FirebaseDatabase.getInstance();

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
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

    }
}
