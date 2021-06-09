package com.se491.eggxact.ui.landingpage;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

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
import com.se491.eggxact.R;
import com.se491.eggxact.structure.RecipeInfo;
import com.se491.eggxact.structure.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    private static final String TAG = "ProfileFragment";

    ConstraintLayout profileLayout;
    ConstraintLayout editProfileLayout;
    ScrollView editScroll;
    Button editProfileBtn;
    Button saveChanges;

    private TextView userName;
    private TextView userEmail;

    private TextView fullNameInput;
    private TextView emailInput;
    private TextView phoneInput;
    private TextView passInput;

    User r;



    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users");
    private String id = user.getUid();

    private FirebaseUser userP;


    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fragmentView = inflater.inflate(R.layout.fragment_profile, container, false);

        userName = fragmentView.findViewById(R.id.user_name);
        userEmail = fragmentView.findViewById(R.id.email);
        fullNameInput = fragmentView.findViewById(R.id.fnameInput);
        emailInput = fragmentView.findViewById(R.id.emailInput);
        phoneInput = fragmentView.findViewById(R.id.phoneInput);
        passInput = fragmentView.findViewById(R.id.passInput);
        profileLayout = fragmentView.findViewById(R.id.profileLayout);
        editProfileLayout = fragmentView.findViewById(R.id.editProfileLayout);
        editScroll = fragmentView.findViewById(R.id.editScroll);
        editScroll.setVisibility(View.INVISIBLE);
        editProfileBtn = fragmentView.findViewById(R.id.edit_profile);
        saveChanges = fragmentView.findViewById(R.id.save_editProfile);


        if (user != null) {
            reference.child(id).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    r = snapshot.getValue(User.class);
                    userName.setText(r.fullname);
                    userEmail.setText(r.email);

                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        } else {
            // No user is signed in
            Log.d("ProfileFragment", "no user");

        }




        editProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profileLayout.setVisibility(View.INVISIBLE);
                editScroll.setVisibility(View.VISIBLE);
                fullNameInput.setText(r.fullname);
                emailInput.setText(r.email);
                phoneInput.setText(r.phone);
            }
        });

        saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                String fullname = fullNameInput.getText().toString().trim();
//                String phone = phoneInput.getText().toString().trim();
//                String email = emailInput.getText().toString().trim();
//                String pass = passInput.getText().toString().trim();
                updateUser();
            }
        });
        return fragmentView;
    }

    private void updateUser(){
        String fullname = fullNameInput.getText().toString().trim();
        String phone = phoneInput.getText().toString().trim();
        String email = emailInput.getText().toString().trim();
        String password = passInput.getText().toString().trim();

        if (fullname.isEmpty()) {
            fullNameInput.setError("Fullname is required!");
            fullNameInput.requestFocus();
            return;
        }

        if (phone.isEmpty()) {
            phoneInput.setError("Phone number is required!");
            phoneInput.requestFocus();
            return;
        }

        if (phone.length() < 10 || phone.length() > 10) {
            phoneInput.setError("Phone number must be 10 digits!");
            phoneInput.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            emailInput.setError("Email is required!");
            emailInput.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailInput.setError("Please provide valid email address!");
            emailInput.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            passInput.setError("Password cannot be empty!");
            passInput.requestFocus();
            return;
        }

        if (password.length() < 6) {
            passInput.setError("Min password length is 6 characters!");
            passInput.requestFocus();
            return;
        }
        Map<String, Object> userUpdates = new HashMap<>();
        userUpdates.put("fullname",fullname);
        userUpdates.put("phone",phone);
        userUpdates.put("email",email);
        Log.d(TAG, "display name: " + user.getEmail());

        user.updateEmail(email);
        user.updatePassword(password);
        reference.child(id).updateChildren(userUpdates);
        profileLayout.setVisibility(View.VISIBLE);
        editScroll.setVisibility(View.INVISIBLE);
    }
}