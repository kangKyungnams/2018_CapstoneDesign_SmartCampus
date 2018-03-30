package com.capston.iceamericano.smartcampus;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.app.AlertDialog;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class LoginActivity extends AppCompatActivity {

    String TAG = "LoginActivity";
    private FirebaseAuth mAuth;
    private EditText ed_ID;
    private EditText ed_PW;
    private Button registerButton;
    private Button loginButton;
    private Button PWmod_Button;

    DatabaseReference uReference = FirebaseDatabase.getInstance().getReference();
    DatabaseReference userdata = uReference.child("user");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ed_ID = (EditText) findViewById(R.id.ed_ID);
        ed_PW = (EditText) findViewById(R.id.ed_PW);
        registerButton = (Button) findViewById(R.id.registerButton);
        loginButton = (Button) findViewById(R.id.loginButton);
        PWmod_Button = (Button) findViewById(R.id.PWmod_Button);

        mAuth = FirebaseAuth.getInstance();

        registerButton.setOnClickListener(register);
        loginButton.setOnClickListener(login);



        //비밀번호 찾기 버튼 레이아웃
        PWmod_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayout layout = new LinearLayout(LoginActivity.this);
                layout.setOrientation(LinearLayout.VERTICAL);
                final TextView description3 = new TextView(LoginActivity.this);
                description3.setText(" ");
                final TextView description2 = new TextView(LoginActivity.this);
                description2.setText("    가입시 이용한 학번과 E-mail을 입력하세요. ");
                description2.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                final EditText etId = new EditText(LoginActivity.this);
                etId.setHint("   학 번 ");
                final EditText etMail = new EditText(LoginActivity.this);
                etMail.setHint("   E-mail ");
                final TextView description = new TextView(LoginActivity.this);
                description.setText("    입력하신 E-mail로 비밀번호가 전송됩니다. ");
                description.setTextColor(getResources().getColor(R.color.colorrealGray));
                description2.setTextColor(getResources().getColor(R.color.colorrealGray));

                layout.addView(description3);
                layout.addView(description2);
                layout.addView(etId);
                layout.addView(etMail);
                layout.addView(description);


                AlertDialog.Builder dialog = new AlertDialog.Builder(LoginActivity.this);

                dialog.setTitle(" 비밀번호 찾기 ")
                        .setView(layout)
                        .setPositiveButton("확인  ", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String email_address = etMail.getText().toString();
                                if (etMail.getText().toString().isEmpty() == true) {
                                    etMail.setError("이메일을 입력해 주세요.");
                                    return;
                                }
                                FirebaseAuth auth = FirebaseAuth.getInstance();
                                auth.sendPasswordResetEmail(email_address).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                    }
                                });

                            }
                        })
                        .setNeutralButton("  취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .create()
                        .show();
            }
        });


    }


    Button.OnClickListener register = new Button.OnClickListener() {
        @Override
        public void onClick(View v) { // 버튼에 로그인 기능 입히기
            Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
            LoginActivity.this.startActivity(registerIntent);
        }
    };

    Button.OnClickListener login = new Button.OnClickListener() {
        @Override
        public void onClick(View v) { // 빈칸일시 에러메시지 띄우기

            if (ed_ID.getText().toString().isEmpty() == true) {
                    ed_ID.setError("Please confirm the Email");
                return;
            }
            if (ed_PW.getText().toString().isEmpty() == true) {
                    ed_PW.setError("PW를 입력해 주세요");
                return;
            }


            userdata.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.

                    String e_mail = dataSnapshot.child(ed_ID.getText().toString()).child("e_mail").getValue().toString();
                    String value = dataSnapshot.getValue().toString();
                    loginAuth(e_mail,ed_PW.getText().toString());
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w(TAG, "Failed to read value.", error.toException());
                }
            });



        }
    };


    void loginAuth(String email, String password)//로그인 기능
    {

        final Intent loginIntent = new Intent(LoginActivity.this, MainActivity.class);

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            LoginActivity.this.startActivity(loginIntent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "아이디와 비밀번호를 다시 확인해주세요.", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

}
