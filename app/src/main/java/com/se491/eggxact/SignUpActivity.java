package com.se491.eggxact;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.se491.eggxact.structure.User;

public class SignUpActivity extends AppCompatActivity {
    private EditText eFullname, ePhone, eEmail, ePassword;
    private Button register;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();

        eFullname = findViewById(R.id.et_fullname_register);
        eEmail = findViewById(R.id.et_email_register);
        ePassword = findViewById(R.id.et_password_register);
        ePhone = findViewById(R.id.et_phone_register);
        register = findViewById(R.id.btn_register);
        progressBar = findViewById(R.id.progressBar_register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

    }

    private void registerUser() {
        String fullname = eFullname.getText().toString().trim();
        String phone = ePhone.getText().toString().trim();
        String email = eEmail.getText().toString().trim();
        String password = ePassword.getText().toString().trim();

        if(fullname.isEmpty()) {
            eFullname.setError("Fullname is required!");
            eFullname.requestFocus();
            return;
        }

        if(phone.isEmpty()) {
            ePhone.setError("Phone number is required!");
            ePhone.requestFocus();
            return;
        }

        if(phone.length() < 10 || phone.length() > 10) {
            ePhone.setError("Phone number must be 10 digits!");
            ePhone.requestFocus();
            return;
        }

        if(email.isEmpty()) {
            eEmail.setError("Email is required!");
            eEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            eEmail.setError("Please provide valid email address!");
            eEmail.requestFocus();
            return;
        }

        if(password.isEmpty()) {
            ePassword.setError("Password cannot be empty!");
            ePassword.requestFocus();
            return;
        }

        if(password.length() < 6) {
            ePassword.setError("Min password length is 6 characters!");
            ePassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    User user = new User(fullname, phone, email, password);

                    FirebaseDatabase.getInstance().getReference("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()) {
                                Toast.makeText(SignUpActivity.this,"User has been registered successfully!",Toast.LENGTH_LONG).show();
                            }
                            else{
                                Toast.makeText(SignUpActivity.this,"Failed to register! Try again!",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
                else {
                    Toast.makeText(SignUpActivity.this,"This email id is already registered!",Toast.LENGTH_LONG).show();
                }
                progressBar.setVisibility(View.GONE);
            }
        });

    }
}