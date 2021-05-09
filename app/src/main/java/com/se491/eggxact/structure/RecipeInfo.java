package com.se491.eggxact.structure;

import java.util.ArrayList;

public class RecipeInfo {

    private String name;
    private int prepTime;
    private int cookingTime;
    private int readyMinutes;
    private double healthScore;
    private String instructions;
    private String imgURL;
    private final ArrayList<String> ingredients = new ArrayList<>();

    public RecipeInfo() {
    }

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

    @Override
    public String toString() {
        return "RecipeInfo{" +
                "name='" + name + '\'' +
                ",\n prepTime=" + prepTime +
                ",\n cookingTime=" + cookingTime +
                ",\n readyMinutes=" + readyMinutes +
                ",\n healthScore=" + healthScore +
                ",\n instructions='" + instructions + '\'' +
                ",\n imgURL='" + imgURL + '\'' +
                ",\n ingredients=" + ingredients.toString() +
                '}';
    }
}
