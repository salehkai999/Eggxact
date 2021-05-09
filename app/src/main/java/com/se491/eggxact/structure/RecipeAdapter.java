package com.se491.eggxact.structure;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.se491.eggxact.AdvSearchActivity;
import com.se491.eggxact.R;

import java.util.ArrayList;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeViewHolder> {

    private static final String TAG = "RecipeAdapter";
    private ArrayList<Recipe> recipes;
    private AdvSearchActivity advSearchActivity;

    public RecipeAdapter(ArrayList<Recipe> recipes, AdvSearchActivity advSearchActivity) {
        this.recipes = recipes;
        this.advSearchActivity = advSearchActivity;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_item_layout,parent,false);
        itemView.setOnClickListener(advSearchActivity);
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
