package com.capston.iceamericano.smartcampus;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by 경남 on 2018-06-17.
 */

public class NotificationListener {
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private Context mContext;
    private String statuss;
    private Notification mNotification;

    public NotificationListener(Context context){
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference("order").child("coop0001").child("뚝배기").child("2018-06-17").child("2013112140").child("status");
        mContext = context;
        mNotification = new Notification(mContext);
        statuss = "미완성";

    }

    public void foodListener(){
        mDatabaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(final DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(final DataSnapshot dataSnapshot, String s) {
                final String mStatus = dataSnapshot.getValue().toString();
                if(!mStatus.equals(statuss)) {


                    Intent chatIntent = new Intent(mContext, food.class); //context로 이동
//                chatIntent.putExtra("chat_id", updatedChat.getChatId());
                    mNotification
                            .setData(chatIntent)
                            .setTitle("동국대학교 SmartCampus")
                            .setText("주문하신 음식이 완료되었습니다.")
                            .notification();
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
