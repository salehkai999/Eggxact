package com.se491.eggxact.ui.landingpage;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.se491.eggxact.R;

public class RandomViewHolder extends RecyclerView.ViewHolder {

    TextView ingredient;

    public RandomViewHolder( View itemView) {
        super(itemView);
        ingredient = itemView.findViewById(R.id.randomIngredientItem);
    }
}
