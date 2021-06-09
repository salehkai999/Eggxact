package com.se491.eggxact.ui.landingpage;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.se491.eggxact.AdvSearchActivity;
import com.se491.eggxact.R;

import com.se491.eggxact.RecipeActivity;
import com.se491.eggxact.dbutil.CategoriesHelper;
import com.se491.eggxact.structure.Category;
import com.se491.eggxact.ui.categoryact.CategoryActivity;

import com.se491.eggxact.structure.RecipeInfo;
import com.se491.eggxact.ui.recommendation.RecommendationActivity;

import com.se491.eggxact.ui.ratingsact.RatingsActivity;
import com.se491.eggxact.structure.Recipe;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;



public class HomeFragment extends Fragment implements  View.OnClickListener, RatingsAdapter.onItemClickListener {

    private static final String TAG = "HomeFragment";
    private static final ArrayList<Recipe> DB_RECIPE_LIST  = new ArrayList<>();
    private static final ArrayList<Recipe> HIGHEST_RATED_LIST = new ArrayList<>();
    private static final ArrayList<Recipe> CURRENT_RECOMMENDATION_LIST = new ArrayList<>();

    RecyclerView recyclerView;
    RecyclerView ratedRecyclerView;
    RecyclerView recommendationsRecyclerView;
    CatAdapter catAdapter;
    RatingsAdapter ratingsAdapter;

    ArrayList<Category> catList = new ArrayList<>();

    RecommendationAdapter recommendationAdapter;

    //ArrayList<String> ratingsList = new ArrayList<>(Arrays.asList("Thai Turkey Stir-Fry","Smash Burgers","Berry Almond Breakfast Parfait"));
    DatabaseReference databaseReference;
    DatabaseReference recommendationReference;
    TextView catSeeAll;
    TextView ratedSeeAll;
    TextView recommendationsSeeAll;
    EditText searchText;


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
        recommendationReference = FirebaseDatabase.getInstance().getReference().child("Recommendations");
        getRecipesDB();

        catList = CategoriesHelper.getCategories();
        catSeeAll = fragmentView.findViewById(R.id.catSeeAll);
        recyclerView = fragmentView.findViewById(R.id.catRecycler);
        ratedSeeAll = fragmentView.findViewById(R.id.ratedSeeAll);
        catAdapter = new CatAdapter(catList,this);

        RecyclerView.LayoutManager horizontalLayout
                = new LinearLayoutManager(fragmentView.getContext(),
                LinearLayoutManager.HORIZONTAL,
                false);
        recyclerView.setLayoutManager(horizontalLayout);
        recyclerView.setAdapter(catAdapter);
        ratedRecyclerView = fragmentView.findViewById(R.id.ratingsRecycler);
        ratingsAdapter = new RatingsAdapter(HIGHEST_RATED_LIST);
        ratingsAdapter.setOnItemClickListener(this);
        RecyclerView.LayoutManager ratingsLayout = new LinearLayoutManager(fragmentView.getContext(),
                LinearLayoutManager.HORIZONTAL,
                false);
        ratedRecyclerView.setLayoutManager(ratingsLayout);
        ratedRecyclerView.setAdapter(ratingsAdapter);

      recommendationsSeeAll = fragmentView.findViewById((R.id.recommendationsSeeAll));
        recommendationsRecyclerView = fragmentView.findViewById(R.id.RecommendationsRecycler);
        recommendationAdapter = new RecommendationAdapter(catList);
        RecyclerView.LayoutManager recRecyclerLayout = new LinearLayoutManager(fragmentView.getContext(),
                LinearLayoutManager.HORIZONTAL,
                false);
        recommendationsRecyclerView.setLayoutManager(recRecyclerLayout);
        recommendationsRecyclerView.setAdapter(recommendationAdapter);

        searchText = fragmentView.findViewById(R.id.search_recipe);
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


        recommendationsSeeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (int i = 0; i < catList.size(); i++) {
                    CURRENT_RECOMMENDATION_LIST.add(catList.get(i).getRecipes().get(0));
                }

                Intent intent = new Intent(getActivity(), RecommendationActivity.class);
                intent.putExtra("recommendations", CURRENT_RECOMMENDATION_LIST);
                startActivity(intent);
            }
          });

        searchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((actionId == EditorInfo.IME_ACTION_GO)) {
                    if (searchText.getText().toString().trim().isEmpty()) {
                        searchText.setError("Can't be empty!!");
                    } else {
                        Intent i = new Intent(HomeFragment.this.getActivity(), AdvSearchActivity.class);
                        i.putExtra("searchText", searchText.getText().toString().trim());
                        startActivity(i);
                    }
                }
                return false;
            }
        });

        searchText = fragmentView.findViewById(R.id.search_recipe);

        searchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((actionId == EditorInfo.IME_ACTION_GO)) {
                    if (searchText.getText().toString().trim().isEmpty()) {
                        searchText.setError("Can't be empty!!");
                    } else {
                        Intent i = new Intent(HomeFragment.this.getActivity(), AdvSearchActivity.class);
                        i.putExtra("searchText", searchText.getText().toString().trim());
                        startActivity(i);
                    }
                }
                return false;

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
                if(!DB_RECIPE_LIST.isEmpty()) {
                    Log.d(TAG, "onDataChange: " + DB_RECIPE_LIST.get(0).toString());
                    Log.d(TAG, "onDataChange: " + DB_RECIPE_LIST.get(DB_RECIPE_LIST.size() - 1).toString());
                    HIGHEST_RATED_LIST.addAll(DB_RECIPE_LIST.subList(0, 7));
                    ratingsAdapter.notifyDataSetChanged();
                }
              //  Log.d(TAG, "onDataChange: "+snapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        int pos = recyclerView.getChildAdapterPosition(v);
        Category c = catList.get(pos);
        Log.d(TAG, "onClick: "+c.toString());
        Toast.makeText(getContext(), c.getName(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(), CategoryActivity.class);
        intent.putExtra(Category.class.getName(),c);
        startActivity(intent);
    }

    @Override
    public void onItemClick(Recipe recipe) {
        Toast.makeText(getContext(), recipe.getRecipeName(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getContext(), RecipeActivity.class);
        intent.putExtra(Recipe.class.getName(),recipe);
        startActivity(intent);
    }
}