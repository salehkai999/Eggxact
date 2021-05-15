package com.se491.eggxact.ui.landingpage;

import android.icu.text.Edits;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.se491.eggxact.R;
import com.se491.eggxact.structure.Recipe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


public class HomeFragment extends Fragment {

    private static final String TAG = "HomeFragment";
    private static final ArrayList<Recipe> DB_RECIPE_LIST  = new ArrayList<>();
    private static final ArrayList<Recipe> HIGHEST_RATED_LIST = new ArrayList<>();

    RecyclerView recyclerView;
    RecyclerView ratedRecyclerView;
    CatAdapter catAdapter;
    RatingsAdapter ratingsAdapter;
    ArrayList<String> catList = new ArrayList<>(Arrays.asList("Chicken","Salad","Beef","American","Italian"));
    //ArrayList<String> ratingsList = new ArrayList<>(Arrays.asList("Thai Turkey Stir-Fry","Smash Burgers","Berry Almond Breakfast Parfait"));
    DatabaseReference databaseReference;

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
        databaseReference = FirebaseDatabase.getInstance().getReference().child("recipeHolder");
        getRecipesDB();
        recyclerView = fragmentView.findViewById(R.id.catRecycler);
        catAdapter = new CatAdapter(catList);
        RecyclerView.LayoutManager horizontalLayout
                = new LinearLayoutManager(fragmentView.getContext(),
                LinearLayoutManager.HORIZONTAL,
                false);
        recyclerView.setLayoutManager(horizontalLayout);
        recyclerView.setAdapter(catAdapter);

        ratedRecyclerView = fragmentView.findViewById(R.id.ratingsRecycler);
        ratingsAdapter = new RatingsAdapter(HIGHEST_RATED_LIST);
        RecyclerView.LayoutManager ratingsLayout = new LinearLayoutManager(fragmentView.getContext(),
                LinearLayoutManager.HORIZONTAL,
                false);
        ratedRecyclerView.setLayoutManager(ratingsLayout);
        ratedRecyclerView.setAdapter(ratingsAdapter);


        return fragmentView;

    }

    private void getRecipesDB() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Iterable<DataSnapshot> iterable = snapshot.getChildren();
                for(DataSnapshot dataSnapshot : iterable){
                    Recipe r = dataSnapshot.getValue(Recipe.class);
                    DB_RECIPE_LIST.add(r);
                    //Log.d(TAG, "onDataChange: "+r.toString());
                }
                Collections.sort(DB_RECIPE_LIST);
                Log.d(TAG, "onDataChange: "+DB_RECIPE_LIST.get(0).toString());
                Log.d(TAG, "onDataChange: "+DB_RECIPE_LIST.get(DB_RECIPE_LIST.size()-1).toString());
                HIGHEST_RATED_LIST.addAll(DB_RECIPE_LIST.subList(0,7));
                ratingsAdapter.notifyDataSetChanged();
              //  Log.d(TAG, "onDataChange: "+snapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {

            }
        });
    }
}