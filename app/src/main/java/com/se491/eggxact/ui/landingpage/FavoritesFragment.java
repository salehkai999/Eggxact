package com.se491.eggxact.ui.landingpage;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.se491.eggxact.R;
import com.se491.eggxact.structure.Recipe;

import java.util.ArrayList;
import java.util.Arrays;

public class FavoritesFragment extends Fragment {

    //static final ArrayList<String> favList = new ArrayList<>(Arrays.asList("Chicken","Salad","Beef","American","Italian","Korean","Asian"));
    public static ArrayList<Recipe> favList = new ArrayList();
    RecyclerView recyclerView;
    FavAdapter favAdapter;
    public FavoritesFragment() {
        // Required empty public constructor
    }


    public static FavoritesFragment newInstance() {
        FavoritesFragment fragment = new FavoritesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fragmentView = inflater.inflate(R.layout.fragment_favorites, container, false);
        recyclerView = fragmentView.findViewById(R.id.favRecycler);
        favAdapter = new FavAdapter(favList);
        recyclerView.setLayoutManager(new LinearLayoutManager(fragmentView.getContext()));
        recyclerView.setAdapter(favAdapter);
        return fragmentView;
    }
}