package com.se491.eggxact.ui.landingpage;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.se491.eggxact.R;


public class RandomFragment extends Fragment {



    public RandomFragment() {
        // Required empty public constructor
    }


    public static RandomFragment newInstance() {
        RandomFragment fragment = new RandomFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_random, container, false);
    }
}