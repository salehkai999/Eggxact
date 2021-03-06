package com.se491.eggxact.ui.landingpage;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.se491.eggxact.R;
import com.se491.eggxact.RecipeActivity;
import com.se491.eggxact.dbutil.FavHelper;
import com.se491.eggxact.dbutil.RandomGenerator;
import com.se491.eggxact.structure.Recipe;
import com.se491.eggxact.structure.RecipeInfo;
import com.se491.eggxact.structure.Recipe;


import java.util.ArrayList;
import java.util.Arrays;

public class FavoritesFragment extends Fragment implements View.OnClickListener {

    //static final ArrayList<String> favList = new ArrayList<>(Arrays.asList("Chicken","Salad","Beef","American","Italian","Korean","Asian"));

    public static  ArrayList<RecipeInfo> favList = new ArrayList<>();
    private static final String TAG = "FavoritesFragment";
    private static DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Favorites").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
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
        favList = FavHelper.getFavList();

        /*if(!recipeInfoArrayList.isEmpty()){
            favList.clear();
            for(RecipeInfo recipeInfo : recipeInfoArrayList.subList(0,6)){
                String key = databaseReference.push().getKey();
                recipeInfo.setRecipeId(key);
                favList.add(recipeInfo);
            }
            //DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Test").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
            //databaseReference.setValue(recipeInfoArrayList.subList(0,6));

            for(RecipeInfo recipeInfo : recipeInfoArrayList.subList(0,6)) {
                databaseReference.child(recipeInfo.getRecipeId()).setValue(recipeInfo);
            }
        }*/
        Log.d(TAG, "onCreateView: "+favList.size());
        recyclerView = fragmentView.findViewById(R.id.favRecycler);
        favAdapter = new FavAdapter(favList,getContext(),this);
        recyclerView.setLayoutManager(new LinearLayoutManager(fragmentView.getContext()));
        recyclerView.setAdapter(favAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeDeleteCallback(favAdapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);
        return fragmentView;
    }

    @Override
    public void onClick(View v) {
            if(!favList.isEmpty()){
                int pos = recyclerView.getChildAdapterPosition(v);
                Intent i = new Intent(getContext(), RecipeActivity.class);
                i.putExtra("favInfo",favList.get(pos));
                startActivity(i);
            }
    }
}