package com.example.filmsandtv;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SigninActivity extends AppCompatActivity {

    EditText email, password;
    Button signin;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        email = findViewById(R.id.email);
        auth = FirebaseAuth.getInstance();
        password = findViewById(R.id.password);
        signin = findViewById(R.id.Sign);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailTxt = email.getText().toString();
                String passTxt = password.getText().toString();
                if(TextUtils.isEmpty(emailTxt) || TextUtils.isEmpty(passTxt)){
                    Toast.makeText(SigninActivity.this, "Empty credentials", Toast.LENGTH_SHORT).show();
                }else if(passTxt.length() < 6){
                    Toast.makeText(SigninActivity.this, "Password too short", Toast.LENGTH_SHORT).show();
                }else
                    registerUser(emailTxt, passTxt);
            }
        });
    }

    private void registerUser(String emailTxt, String passTxt) {
        auth.createUserWithEmailAndPassword(emailTxt, passTxt).addOnCompleteListener(SigninActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(SigninActivity.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SigninActivity.this, MainActivity.class));
                    finish();
                }else{
                    Toast.makeText(SigninActivity.this, "User registration failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}