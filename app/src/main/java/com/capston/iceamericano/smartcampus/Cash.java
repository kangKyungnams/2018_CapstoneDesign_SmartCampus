package com.capston.iceamericano.smartcampus;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Cash extends AppCompatActivity {

    private Button bt_account, bt_cash_clear, bt_cash_charge;
    private EditText ed_cash_charge;
    int after_balance=0;
    String value;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference uReference = FirebaseDatabase.getInstance().getReference();
    DatabaseReference userdata = uReference.child("user");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash);

        bt_account = (Button)findViewById(R.id.bt_account);
        bt_cash_clear = (Button)findViewById(R.id.bt_cash_clear);
        bt_cash_charge = (Button)findViewById(R.id.bt_cash_charge);
        ed_cash_charge = (EditText)findViewById(R.id.ed_cash_charge);

        bt_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final LinearLayout layout = new LinearLayout(Cash.this);
                layout.setOrientation(LinearLayout.VERTICAL);

                final CharSequence[] items = {"신한", "국민", "농협"};


                AlertDialog.Builder builder = new AlertDialog.Builder(Cash.this);
                final AlertDialog.Builder builder1 = new AlertDialog.Builder(Cash.this);

                builder.setTitle("입금 계좌 선택 ")
                        .setView(layout)
                        .setPositiveButton("확인", new DialogInterface.OnClickListener(){
                            // 확인 버튼 클릭시 설정
                            public void onClick(DialogInterface dialog, int whichButton){
                                dialog.cancel();
                            }
                        })
                        .setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener(){
                            // 목록 클릭시 설정
                            public void onClick(DialogInterface dialog, int index){
                                layout.removeAllViews();

                                if(items[index] == "신한"){
                                    items[index] = "110-393-897870 (신한:생활협동조합)";
                                }
                                else if(items[index]== "국민"){
                                    items[index] = "402402-04-171357 (국민:생활협동조합)";
                                }
                                else if(items[index] == "농협"){
                                    items[index] = "302-94947808-21 (농협:생활협동조합)";
                                }


                                final TextView description2 = new TextView(Cash.this);
                                description2.setText("       해당 계좌로 신청한 금액만큼 입금해주세요 ");
                                final TextView description = new TextView(Cash.this);
                                description.setText("       (입금을 먼저 하시고 충전버튼을 눌러주세요) ");
                                final TextView description3 = new TextView(Cash.this);
                                description3.setText("      "+items[index]);

                                description3.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                                description2.setTextColor(getResources().getColor(R.color.colorrealGray));
                                description.setTextColor(getResources().getColor(R.color.colorrealGray));
                                description3.setTextSize(15);

                                layout.addView(description3);
                                layout.addView(description2);
                                layout.addView(description);



                            }
                        });
                AlertDialog dialog = builder.create();    // 알림창 객체 생성
                dialog.show();

            }
        });
        bt_cash_clear.setOnClickListener(bt_clear_action);
        bt_cash_charge.setOnClickListener(bt_charge_action);

    }

    Button.OnClickListener bt_clear_action = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            ed_cash_charge.setText("");
            ed_cash_charge.requestFocus();
        }
    };

    Button.OnClickListener bt_charge_action = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            int cut_index;
            String cut_char = user.getEmail();
            cut_index = cut_char.indexOf("@");
            value= user.getEmail().substring(0, cut_index);

            userdata.child(value).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    int pre_balance;
                    try {
                        String balance = dataSnapshot.child("balance").getValue().toString();
                        pre_balance = Integer.parseInt(balance);
                        after_balance = pre_balance + Integer.parseInt(ed_cash_charge.getText().toString());
                        userdata.child(value).child("balance").setValue(after_balance);
                        Toast.makeText(Cash.this, "충전이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    catch (Exception e){
                        Toast.makeText(Cash.this, "올바르지 않습니다.", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                }
            });

        }
    };
}