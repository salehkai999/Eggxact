package com.se491.eggxact.dbutil;

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
import java.util.Random;

public class RandomGenerator {
    private static final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Random");
    private static final ArrayList<RecipeInfo> RECIPE_INFO_ARRAY_LIST = new ArrayList<>();
    private static final String TAG = "RandomGenerator";
    private static  Random random = new Random();

    public static void getAll() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                Iterable<DataSnapshot> iterable = snapshot.getChildren();
                //Log.d(TAG, "onDataChange: "+iterable.toString());
                //Log.d(TAG, "onDataChange: "+snapshot.getChildrenCount());
                for(DataSnapshot dataSnapshot : iterable){
                //    Log.d(TAG, "onDataChange: "+dataSnapshot.toString());
                    //Log.d(TAG, "onDataChange: "+dataSnapshot.getChildrenCount());
                    RecipeInfo r = dataSnapshot.getValue(RecipeInfo.class);
                    RECIPE_INFO_ARRAY_LIST.add(r);
                    //Log.d(TAG, "onDataChange: "+r.toString());
                }
                Log.d(TAG, "onDataChange: "+RECIPE_INFO_ARRAY_LIST.size());
            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {

            }
        });


    }

    public static RecipeInfo getRandomRecipe() {
       // int index = random.ints(0,RECIPE_INFO_ARRAY_LIST.size()).findFirst().getAsInt(); ** Required API level 24!!
      //  RECIPE_INFO_ARRAY_LIST.clear();
      //  getAll();
        if(!RECIPE_INFO_ARRAY_LIST.isEmpty()) {
            int index = random.nextInt(RECIPE_INFO_ARRAY_LIST.size());
            return RECIPE_INFO_ARRAY_LIST.get(index);
        }
        else
            return null;
    }

    public static ArrayList<RecipeInfo> getAllData() {
        if(!RECIPE_INFO_ARRAY_LIST.isEmpty()){
            return RECIPE_INFO_ARRAY_LIST;
        }
        return null;
    }


}
