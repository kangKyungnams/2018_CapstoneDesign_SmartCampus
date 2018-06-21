package com.capston.iceamericano.smartcampus;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.ArrayList;

public class Mypage extends AppCompatActivity {



    String TAG = "Mypage";
    //    private ArrayAdapter adapter;
//    private Spinner spinner;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference uReference = FirebaseDatabase.getInstance().getReference();
    DatabaseReference userdata = uReference.child("user");
    private TextView mypage_user_name, mypage_major, mypage_email,mypage_balance;
    ImageView mypage_qr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        mypage_user_name = (TextView)findViewById(R.id.mypage_user_name);
        mypage_major = (TextView)findViewById(R.id.mypage_major);
        mypage_email = (TextView)findViewById(R.id.mypage_email);
        mypage_balance = (TextView)findViewById(R.id.mypage_balance);
        mypage_qr = (ImageView) findViewById(R.id.mypage_qr);



        String text2Qr = user.getEmail();
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(text2Qr, BarcodeFormat.QR_CODE,200,200);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            mypage_qr.setImageBitmap(bitmap);

        }catch (Exception e)
        {

        }


        String  value= user.getEmail().substring(0, 10);
        DatabaseReference userMypage = userdata.child(value);

        //접속 계정의 강의 목록 불러오기
        userMypage.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                mypage_user_name.setText(dataSnapshot.child("name").getValue().toString());
                mypage_major.setText(dataSnapshot.child("department_name").getValue().toString());
                mypage_email.setText(dataSnapshot.child("e_mail").getValue().toString());
                mypage_balance.setText("잔액 : " + dataSnapshot.child("balance").getValue().toString() + "원");
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }
}
