package com.se491.eggxact.dbutil;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.se491.eggxact.structure.RecipeInfo;

import java.util.ArrayList;

public class FavHelper {
    private static DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Favorites").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
    private static final ArrayList<RecipeInfo> FAV_LIST = new ArrayList<>();
    private static final String TAG = "FavHelper";

    public static void getDataFromDB() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d(TAG, "onDataChange: "+snapshot.toString());
                FAV_LIST.clear();
                Iterable<DataSnapshot> iterable = snapshot.getChildren();
                for(DataSnapshot dataSnapshot : iterable) {
                    RecipeInfo recipeInfo = dataSnapshot.getValue(RecipeInfo.class);
                    Log.d(TAG, "onDataChange: "+recipeInfo.toString());
                    FAV_LIST.add(recipeInfo);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public static ArrayList<RecipeInfo> getFavList() {
        return FAV_LIST;
    }



}
