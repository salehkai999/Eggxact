package com.se491.eggxact.ui.landingpage;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.se491.eggxact.R;
import com.se491.eggxact.dbutil.CategoriesHelper;
import com.se491.eggxact.structure.Category;
import com.se491.eggxact.ui.ratingsact.RatingsActivity;
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
    ArrayList<Category> catList = new ArrayList<>();
    //ArrayList<String> ratingsList = new ArrayList<>(Arrays.asList("Thai Turkey Stir-Fry","Smash Burgers","Berry Almond Breakfast Parfait"));
    DatabaseReference databaseReference;
    TextView catSeeAll;
    TextView ratedSeeAll;

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
        catList = CategoriesHelper.getCategories();
        catSeeAll = fragmentView.findViewById(R.id.catSeeAll);
        recyclerView = fragmentView.findViewById(R.id.catRecycler);
        ratedSeeAll = fragmentView.findViewById(R.id.ratedSeeAll);
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

        catSeeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    TabLayout tabLayout = getActivity().findViewById(R.id.tabLayout);
                    tabLayout.getTabAt(1).select();
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        ratedSeeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RatingsActivity.class);
                intent.putExtra("ratings",DB_RECIPE_LIST);
                startActivity(intent);
            }
        });


        return fragmentView;

    }

    private void getRecipesDB() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                DB_RECIPE_LIST.clear();
                HIGHEST_RATED_LIST.clear();
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