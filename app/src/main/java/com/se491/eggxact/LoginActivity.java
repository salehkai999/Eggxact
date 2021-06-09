package com.se491.eggxact;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView forgotPW, signUp;
    private EditText email, password;
    private Button login;
    private ImageButton twitter, fb, google;

    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        //let's initialize all private variables
        login = findViewById(R.id.btn_login);
        forgotPW = findViewById(R.id.tv_forgot);
        signUp = findViewById(R.id.tv_signup);
        email = findViewById(R.id.et_email);
        password = findViewById(R.id.et_password);
        twitter = findViewById(R.id.imgbtn_twitter);
        fb = findViewById(R.id.imgbtn_fb);
        google = findViewById(R.id.imgbtn_google);



        progressBar = findViewById(R.id.progressBar);
        mAuth = FirebaseAuth.getInstance();

        login.setOnClickListener(this);
        forgotPW.setOnClickListener(this);
        signUp.setOnClickListener(this);

        fb.setOnClickListener(this);
        twitter.setOnClickListener(this);
        google.setOnClickListener(this);
        //to assign click listener logic to login button

        Button crashButton = new Button(this);
        crashButton.setText("Crash!");
        crashButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
              Intent i = new Intent(LoginActivity.this,CrashActivity.class);
              startActivity(i);
            }
        });

        addContentView(crashButton, new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));


    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {
            case R.id.tv_signup:
                i = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(i);
                break;

            case R.id.tv_forgot:
                i = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(i);
                break;

            case R.id.imgbtn_twitter:
                Toast.makeText(LoginActivity.this,"Twitter login credentials are not implemented",Toast.LENGTH_SHORT).show();
                break;

            case R.id.imgbtn_fb:
                Toast.makeText(LoginActivity.this,"FB login credentials are not implemented",Toast.LENGTH_SHORT).show();
                break;

            case R.id.imgbtn_google:
                Toast.makeText(LoginActivity.this,"Google login credentials are not implemented",Toast.LENGTH_SHORT).show();
                break;

            case R.id.btn_login:
                userLogin();
                break;


        }

    }

    private void userLogin() {
        String inputEmail = email.getText().toString().trim();
        String inputPassword = password.getText().toString().trim();


        if(inputEmail.isEmpty()) {
            email.setError("Email is required!");
            email.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(inputEmail).matches()){
            email.setError("Please provide valid email address!");
            email.requestFocus();
            return;
        }

        if(inputPassword.isEmpty()) {
            password.setError("Password must be entered!");
            password.requestFocus();
            return;
        }

        if(inputPassword.length() < 6) {
            password.setError("Invalid password!!");
            password.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(inputEmail, inputPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Intent i = new Intent(LoginActivity.this, LandingPageActivity.class);
                    startActivity(i);
                    finish();

                }
                else {
                    Toast.makeText(LoginActivity.this,"Failed to login! Please check your credentials",Toast.LENGTH_LONG).show();
                }
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}