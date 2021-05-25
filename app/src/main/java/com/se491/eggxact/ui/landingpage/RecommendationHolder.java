package com.se491.eggxact.ui.landingpage;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.se491.eggxact.R;

public class RecommendationHolder extends RecyclerView.ViewHolder {
    TextView recommendationRecipe;

    public RecommendationHolder(@NonNull View itemView) {
        super(itemView);
        recommendationRecipe = itemView.findViewById(R.id.recipeTitle);
    }
}
