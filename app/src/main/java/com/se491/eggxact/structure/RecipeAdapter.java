package com.se491.eggxact.structure;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.se491.eggxact.AdvSearchActivity;
import com.se491.eggxact.R;
import com.se491.eggxact.ui.categoryact.CategoryActivity;
import com.se491.eggxact.ui.landingpage.CategoriesFragment;
import com.se491.eggxact.ui.recommendation.RecommendationActivity;

import java.util.ArrayList;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeViewHolder> {

    private static final String TAG = "RecipeAdapter";
    private int flag = 0;
    private ArrayList<Recipe> recipes;
    private AdvSearchActivity advSearchActivity;
    private CategoryActivity categoryActivity;
    private RecommendationActivity recommendationActivity;

    public RecipeAdapter(ArrayList<Recipe> recipes, AdvSearchActivity advSearchActivity) {
        this.recipes = recipes;
        this.advSearchActivity = advSearchActivity;
        flag = 1;
    }

    public RecipeAdapter(ArrayList<Recipe> recipes, CategoryActivity categoryActivity) {
        this.recipes = recipes;
        this.categoryActivity = categoryActivity;
        flag = 2;
    }


    public RecipeAdapter(ArrayList<Recipe> recipes, RecommendationActivity recommendationActivity) {
        this.recipes = recipes;
        this.recommendationActivity = recommendationActivity;
        flag = 3;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_item_layout,parent,false);
        switch (flag) {
            case 1: itemView.setOnClickListener(advSearchActivity); break;
            case 2: itemView.setOnClickListener(categoryActivity); break;
            case 3: itemView.setOnClickListener(recommendationActivity); break;
            default:
        }

        return new RecipeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        holder.title.setText(recipes.get(position).getRecipeName());
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }
}
