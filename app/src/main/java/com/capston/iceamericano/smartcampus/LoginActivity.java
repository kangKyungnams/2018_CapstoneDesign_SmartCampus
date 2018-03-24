package com.capston.iceamericano.smartcampus;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    String TAG = "LoginActivity";
    private FirebaseAuth mAuth;
    private EditText ed_ID;
    private EditText ed_PW;
    private Button registerButton;
    private Button loginButton;

    BluetoothAdapter mBluetoothAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ed_ID = (EditText) findViewById(R.id.ed_ID);
        ed_PW = (EditText) findViewById(R.id.ed_PW);
        registerButton = (Button) findViewById(R.id.registerButton);
        loginButton = (Button) findViewById(R.id.loginButton);

        mAuth = FirebaseAuth.getInstance();
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        blueTooth();

        registerButton.setOnClickListener(register);
        loginButton.setOnClickListener(login);



    }


    void blueTooth ()
    {
        if(mBluetoothAdapter == null)
        {
            Log.d(TAG, "There is no bluetooth");
        }
        else
        {
            if(mBluetoothAdapter.isEnabled())
            {
                Log.d(TAG, "Bluetooth is already on");
            }
            else
            {
                mBluetoothAdapter.enable();
            }
        }
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
            loginAuth(ed_ID.getText().toString(),ed_PW.getText().toString());
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
