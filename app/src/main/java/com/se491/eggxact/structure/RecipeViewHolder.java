package com.se491.eggxact.structure;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.se491.eggxact.R;

public class RecipeViewHolder extends RecyclerView.ViewHolder {

    TextView title;

    public RecipeViewHolder(@NonNull View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.recipeTitle);
    }
}
