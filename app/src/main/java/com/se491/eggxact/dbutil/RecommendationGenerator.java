package com.se491.eggxact.dbutil;

import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.se491.eggxact.structure.Recipe;
import com.se491.eggxact.structure.RecipeInfo;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecommendationGenerator {
    private static final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Recommendations");
    private static final String TAG = "RandomGenerator";
    private static final Map<String, List<RecipeInfo>> recommendationMap = new HashMap<>();
    private static final ArrayList<String> categories = new ArrayList<>(Arrays.asList("Chicken","Salad","Beef","American","Italian"));



    //just used this as a dummy object to get some data in the table
    public static void addtoRecommendationTable() {
            //create a new string at that json location
            for(String category : categories) {
                String id = databaseReference.push().getKey();
                //need to get info for a recipe
                Map<String, List<RecipeInfo>> save = new HashMap<>();
                //TODO: how to load actual recipies here
                save.put(category, Arrays.asList(new RecipeInfo()));
                databaseReference.child(id).setValue(save);
            }
        }

}
