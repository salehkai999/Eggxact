package com.se491.eggxact.structure;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class RecipeInfo implements Serializable {

    /*
        RecipeInfo to hold a single recipe's data.
     */
    private String recipeId;
    private String commentID;
    private String name;
    private int prepTime;
    private int cookingTime;
    private int readyMinutes;
    private double healthScore;
    private String instructions;
    private String imgURL;
    private final ArrayList<String> ingredients = new ArrayList<>();
    private final ArrayList<String> cuisines = new ArrayList<>();
    private static final String TAG = "RecipeInfo";

    public RecipeInfo() {
    }


    public String getRecipeId(){ return recipeId; }
    public void setRecipeId(String id){this.recipeId = id;}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrepTime() {
        return prepTime;
    }

    public void setPrepTime(int prepTime) {
        this.prepTime = prepTime;
    }

    public int getCookingTime() {
        return cookingTime;
    }

    public void setCookingTime(int cookingTime) {
        this.cookingTime = cookingTime;
    }

    public int getReadyMinutes() {
        return readyMinutes;
    }

    public void setReadyMinutes(int readyMinutes) {
        this.readyMinutes = readyMinutes;
    }

    public double getHealthScore() {
        return healthScore;
    }

    public void setHealthScore(double healthScore) {
        this.healthScore = healthScore;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public void addIngredient(String ingredient){
        ingredients.add(ingredient);
    }

    public ArrayList<String> getIngredients() {
        return ingredients;
    }

    public void addCuisine(String cuisine){
        cuisines.add(cuisine);
    }

    public ArrayList<String> getCuisines() {
        return cuisines;
    }

    public String getCommentID() {
        return commentID;
    }

    public void setCommentID(String commentID) {
        this.commentID = commentID;
    }

    @Override
    public String toString() {
        return "RecipeInfo{" +
                "name='" + name + '\'' +
                ", prepTime=" + prepTime +
                ", cookingTime=" + cookingTime +
                ", readyMinutes=" + readyMinutes +
                ", healthScore=" + healthScore +
                ", instructions='" + instructions + '\'' +
                ", imgURL='" + imgURL + '\'' +
                ", ingredients=" + ingredients +
                ", cuisines=" + cuisines +
                '}';
    }
}
