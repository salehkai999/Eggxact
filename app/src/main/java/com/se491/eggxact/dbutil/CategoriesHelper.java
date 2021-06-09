package com.se491.eggxact.dbutil;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.se491.eggxact.structure.Category;

import java.util.ArrayList;

public class CategoriesHelper {
    private static final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("categories");
    private static final DatabaseReference databaseReferenceChild = FirebaseDatabase.getInstance().getReference().child("categories");
    private static final String TAG = "CategoriesHelper";
    private static final ArrayList<Category> CATEGORIES = new ArrayList<>();

    public static void getAll() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                Iterable<DataSnapshot> iterable = snapshot.getChildren();
                CATEGORIES.clear();
                for(DataSnapshot dataSnapshot : iterable) {
                    databaseReferenceChild.child(dataSnapshot.getKey()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull  DataSnapshot snapshot) {
                            Category cat = snapshot.getValue(Category.class);
                            CATEGORIES.add(cat);
                            //Log.d(TAG, "onDataChange: "+cat.toString());
                        }

                        @Override
                        public void onCancelled(@NonNull  DatabaseError error) {
                            //Log.d(TAG, "onCancelled: "+error.toString());
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {
                //Log.d(TAG, "onCancelled: "+error.toString());
            }
        });

       // Log.d(TAG, "getAll: "+CATEGORIES.toString());
    }

    public static ArrayList<Category> getCategories() {
        Log.d(TAG, "getCategories: "+CATEGORIES.size());
        return CATEGORIES;
    }

}
