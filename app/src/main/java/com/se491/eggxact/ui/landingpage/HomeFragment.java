package com.se491.eggxact.ui.landingpage;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.se491.eggxact.R;

import java.util.ArrayList;
import java.util.Arrays;


public class HomeFragment extends Fragment {


    RecyclerView recyclerView;
    RecyclerView ratedRecyclerView;
    CatAdapter catAdapter;
    RatingsAdapter ratingsAdapter;
    ArrayList<String> catList = new ArrayList<>(Arrays.asList("Chicken","Salad","Beef","American","Italian"));
    ArrayList<String> ratingsList = new ArrayList<>(Arrays.asList("Thai Turkey Stir-Fry","Smash Burgers","Berry Almond Breakfast Parfait"));

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = fragmentView.findViewById(R.id.catRecycler);
        catAdapter = new CatAdapter(catList);
        RecyclerView.LayoutManager horizontalLayout
                = new LinearLayoutManager(fragmentView.getContext(),
                LinearLayoutManager.HORIZONTAL,
                false);
        recyclerView.setLayoutManager(horizontalLayout);
        recyclerView.setAdapter(catAdapter);

        ratedRecyclerView = fragmentView.findViewById(R.id.ratingsRecycler);
        ratingsAdapter = new RatingsAdapter(ratingsList);
        RecyclerView.LayoutManager ratingsLayout = new LinearLayoutManager(fragmentView.getContext(),
                LinearLayoutManager.HORIZONTAL,
                false);
        ratedRecyclerView.setLayoutManager(ratingsLayout);
        ratedRecyclerView.setAdapter(ratingsAdapter);


        return fragmentView;

    }
}