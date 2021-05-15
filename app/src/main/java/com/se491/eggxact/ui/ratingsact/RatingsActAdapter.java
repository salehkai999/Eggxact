package com.se491.eggxact.ui.ratingsact;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.se491.eggxact.R;
import com.se491.eggxact.structure.Recipe;

import java.util.ArrayList;

public class RatingsActAdapter extends RecyclerView.Adapter<RatingsActViewHolder> {

    RatingsActivity ratingsActivity;
    ArrayList<Recipe> recipes;

    public RatingsActAdapter(RatingsActivity ratingsActivity, ArrayList<Recipe> recipes) {
        this.ratingsActivity = ratingsActivity;
        this.recipes = recipes;
    }

    @NonNull
    @Override
    public RatingsActViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.ratingsact_item_layout,parent,false);
        itemView.setOnClickListener(ratingsActivity);
        return  new RatingsActViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull  RatingsActViewHolder holder, int position) {
        holder.title.setText(recipes.get(position).getRecipeName());
        holder.likes.setText(String.valueOf(recipes.get(position).getLikes()));
        holder.dislikes.setText(String.valueOf(recipes.get(position).getDislikes()));
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }
}
