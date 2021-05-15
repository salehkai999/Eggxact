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
    CatAdapter catAdapter;
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
        catAdapter = new CatAdapter(catList);
        recyclerView.setLayoutManager(new LinearLayoutManager(fragmentView.getContext()));
        recyclerView.setAdapter(catAdapter);

        return fragmentView;
    }
}