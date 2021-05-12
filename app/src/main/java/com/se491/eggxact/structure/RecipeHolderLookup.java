package com.se491.eggxact.structure;

import android.app.Activity;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.se491.eggxact.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class RecipeHolderLookup {

    DatabaseReference recipeHolderDatabase;
    List<Recipe> recipes;

    public RecipeHolderLookup(DatabaseReference reference) {
        recipeHolderDatabase = reference;
        recipes = new ArrayList<>();
    }

    public void addtoRecipeHolderTable(String recipeName, String recipeId) {
        if (!TextUtils.isEmpty(recipeName)) {
            //create a new string at that json location
            String id = recipeHolderDatabase.push().getKey();
            Recipe recipe = new Recipe(recipeId, recipeName);
            recipeHolderDatabase.child(id).setValue(recipe);
        }
    }


    public void selectFromRecipeHolder(String recipeId, Activity context, ListView listViewofRecipes) {
        recipeHolderDatabase.addValueEventListener(new ValueEventListener() {
            //executed every time db changes
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //want a fresh list each time starting the loop
                recipes.clear();
                for (DataSnapshot recipeSnapshot : snapshot.getChildren()) {
                    Recipe recipe = recipeSnapshot.getValue(Recipe.class);
                    if (recipe.getRecipeId().equals(recipeId)) {
                        recipes.add(recipe);
                        break;
                    }
                }
                if (recipes.size() == 0) {
                    Recipe samplerecipe = new Recipe("This is", "made up");
                    recipes.add(samplerecipe);
                }

                RecipeList adapter = new RecipeList(context, recipes);
                listViewofRecipes.setAdapter(adapter);
            }
                @Override
                public void onCancelled (@NonNull DatabaseError error){

                }

        });
    }
}


