package com.se491.eggxact.ui.landingpage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.se491.eggxact.R;
import com.se491.eggxact.structure.RecipeInfo;

import java.util.List;
import java.util.Map;

public class RecommendationAdapter extends RecyclerView.Adapter<RandomViewHolder> {

    //map of recommendations by category
    List<RecipeInfo> recommendations;

    public RecommendationAdapter(List<RecipeInfo> recommendations) {
        this.recommendations = recommendations;
    }

    @NonNull
    @Override
    public RandomViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recommendation_item_layout,parent,false);
        return new RandomViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull  RandomViewHolder holder, int position) {
            //holder.ingredient.setText(ingredients.get(position));
    }

    @Override
    public int getItemCount() {
        //return ingredients.size();
        return 1;
    }

}
