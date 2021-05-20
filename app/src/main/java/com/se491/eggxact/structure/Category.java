package com.se491.eggxact.structure;

import java.io.Serializable;
import java.util.ArrayList;

public class Category implements Serializable {
    private String name;
    private ArrayList<Recipe> recipes = new ArrayList<>();

    public Category() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Recipe> getRecipes() {
        return recipes;
    }

    public void addRecipe(Recipe recipe){
        this.recipes.add(recipe);
    }

    public void addAllRecipe(ArrayList<Recipe> recipes){
        this.recipes.addAll(recipes);
    }

    @Override
    public String toString() {
        return "Category{" +
                "name='" + name + '\'' +
                ", recipes=" + recipes +
                '}';
    }
}
