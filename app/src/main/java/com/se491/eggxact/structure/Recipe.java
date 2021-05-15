package com.se491.eggxact.structure;

import com.google.firebase.database.DatabaseReference;

public class Recipe implements Comparable<Recipe> {


    /*
        RecipeHolder to link with Firebase.
     */

    public String recipeId;
    public String recipeName;
    public String sourceUrl;
    public long likes;
    public long dislikes;

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

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public long getLikes() {
        return likes;
    }

    public void setLikes(long likes) {
        this.likes = likes;
    }

    public long getDislikes() {
        return dislikes;
    }

    public void setDislikes(long dislikes) {
        this.dislikes = dislikes;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "recipeId='" + recipeId + '\'' +
                ", recipeName='" + recipeName + '\'' +
                ", sourceUrl='" + sourceUrl + '\'' +
                ", likes=" + likes +
                ", dislikes=" + dislikes +
                '}';
    }

    @Override
    public int compareTo(Recipe o) {
        if(this.getLikes() > o.getLikes()){
            return -1;
        }
        else if(this.getLikes()<o.getLikes()){
            return 1;
        }
        else
            return 0;
    }
}
