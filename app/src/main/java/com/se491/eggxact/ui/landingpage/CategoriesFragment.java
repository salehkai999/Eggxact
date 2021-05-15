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

public class CategoriesFragment extends Fragment {

    RecyclerView recyclerView;
    RecyclerView recyclerViewTwo;
    CatAdapter catAdapterR1;
    CatAdapter catAdapterR2;
    ArrayList<String> catList = new ArrayList<>(Arrays.asList("Chicken","Salad","Beef","American","Italian","Korean","Asian"));


    public CategoriesFragment() {
        // Required empty public constructor
    }


    public static CategoriesFragment newInstance() {
        CategoriesFragment fragment = new CategoriesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_categories, container, false);
        recyclerView = fragmentView.findViewById(R.id.catFragRecycler);
        recyclerViewTwo = fragmentView.findViewById(R.id.catFragRecycler2);
        catAdapterR1 = new CatAdapter(catList);
        catAdapterR2 = new CatAdapter(catList);
        recyclerView.setLayoutManager(new LinearLayoutManager(fragmentView.getContext()));
        recyclerView.setAdapter(catAdapterR1);
        recyclerViewTwo.setLayoutManager(new LinearLayoutManager(fragmentView.getContext()));
        recyclerViewTwo.setAdapter(catAdapterR2);

        return fragmentView;
    }
}