package com.capston.iceamericano.smartcampus;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class RegisterActivity extends AppCompatActivity {

    private ArrayAdapter adapter;
    private Spinner spinner;
    private EditText ed_StuNum;
    private EditText ed_name;
    private EditText ed_password;
    private EditText ed_email;
    private Button bt_signup;
    String TAG = "RegisterActivity";

    private FirebaseAuth mAuth;
    DatabaseReference uReference = FirebaseDatabase.getInstance().getReference();
    DatabaseReference userdata = uReference.child("user");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ed_StuNum = (EditText)findViewById(R.id.ed_StuNum);
        ed_name = (EditText)findViewById(R.id.ed_name);
        ed_password = (EditText)findViewById(R.id.ed_password);
        ed_email = (EditText)findViewById(R.id.ed_email);
        bt_signup = (Button)findViewById(R.id.bt_signup);

        spinner = (Spinner)findViewById(R.id.Major_Spinner);
        adapter = ArrayAdapter.createFromResource(this, R.array.major, android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        mAuth = FirebaseAuth.getInstance();
        bt_signup.setOnClickListener(on_click);
    }


    Button.OnClickListener on_click = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            createAccount(ed_email.getText().toString(), ed_password.getText().toString());
            finish();
        }
    };

    public void createAccount(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser User = task.getResult().getUser();
                            userdata.child(ed_StuNum.getText().toString()).child("ID").setValue(ed_email.getText().toString());
                            userdata.child(ed_StuNum.getText().toString()).child("PW").setValue(ed_password.getText().toString());
                            userdata.child(ed_StuNum.getText().toString()).child("Name").setValue(ed_name.getText().toString());
//                            userdata.child(User.getUid()).child("Photo").setValue("https://firebasestorage.googleapis.com/v0/b/anypeople-12.appspot.com/o/standard.png?alt=media&token=b2e19a52-39c8-44af-9989-d73e5c5c30d1");
                            userdata.child(ed_StuNum.getText().toString()).child("keyUID").setValue(User.getUid().toString());
                            Toast.makeText(RegisterActivity.this, "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "이미 있는 ID 입니다.", Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }


}
