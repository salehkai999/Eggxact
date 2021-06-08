package com.se491.eggxact.ui.landingpage;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.se491.eggxact.R;
import com.se491.eggxact.dbutil.RandomGenerator;
import com.se491.eggxact.structure.Recipe;
import com.se491.eggxact.structure.RecipeInfo;

import java.util.ArrayList;
import java.util.Arrays;

public class FavoritesFragment extends Fragment {

    static final ArrayList<String> favList = new ArrayList<>(Arrays.asList("Chicken","Salad","Beef","American","Italian","Korean","Asian"));
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
        ArrayList<RecipeInfo> recipeInfoArrayList = RandomGenerator.getAllData();
        if(!recipeInfoArrayList.isEmpty()){
            favList.clear();
            for(RecipeInfo recipeInfo : recipeInfoArrayList.subList(0,6)){
                favList.add(recipeInfo.getName());
            }
            //DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Test").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
            //databaseReference.setValue(recipeInfoArrayList.subList(0,6));
            /*
            for(RecipeInfo recipeInfo : recipeInfoArrayList.subList(0,6)) {
                databaseReference.push().setValue(recipeInfo);
            }*/
        }
        recyclerView = fragmentView.findViewById(R.id.favRecycler);
        favAdapter = new FavAdapter(favList,getContext(),this);
        recyclerView.setLayoutManager(new LinearLayoutManager(fragmentView.getContext()));
        recyclerView.setAdapter(favAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeDeleteCallback(favAdapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);
        return fragmentView;
    }
}