package com.se491.eggxact.ui.landingpage;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.se491.eggxact.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    ConstraintLayout profileLayout;
    ConstraintLayout editProfileLayout;
    ScrollView editScroll;
    Button editProfileBtn;
    Button saveChanges;



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