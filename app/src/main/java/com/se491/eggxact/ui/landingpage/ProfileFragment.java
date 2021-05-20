package com.se491.eggxact.ui.landingpage;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    TextView userName;
    TextView userEmail;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users");


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

        if (user != null) {
            String id = user.getUid();
            reference.child(id).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    User r = snapshot.getValue(User.class);
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
        // Inflate the layout for this fragment
        profileLayout = fragmentView.findViewById(R.id.profileLayout);
        editProfileLayout = fragmentView.findViewById(R.id.editProfileLayout);
        editScroll = fragmentView.findViewById(R.id.editScroll);
        editScroll.setVisibility(View.INVISIBLE);
        editProfileBtn = fragmentView.findViewById(R.id.edit_profile);
        editProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profileLayout.setVisibility(View.INVISIBLE);
                editScroll.setVisibility(View.VISIBLE);
            }
        });

        saveChanges = fragmentView.findViewById(R.id.save_editProfile);
        saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profileLayout.setVisibility(View.VISIBLE);
                editScroll.setVisibility(View.INVISIBLE);
            }
        });
        return fragmentView;
    }
}