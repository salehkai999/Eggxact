package com.se491.eggxact.structure;

import com.google.firebase.database.DatabaseReference;

public class Recipe {

    public String recipeId;
    public String recipeName;

    public Recipe() {
    }

    public Recipe(String id, String name) {
        this.recipeId = id;
        this.recipeName = name;
    }

    public String getRecipeId() {
        return recipeId;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeId(String id) {
        this.recipeId = id;
    }

}
