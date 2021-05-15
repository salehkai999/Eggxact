package com.se491.eggxact.ui.landingpage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.se491.eggxact.R;

import java.util.ArrayList;

public class RandomAdapter extends RecyclerView.Adapter<RandomViewHolder> {

    ArrayList<String> ingredients;

    public RandomAdapter(ArrayList<String> ingredients) {
        this.ingredients = ingredients;
    }

    @NonNull
    @Override
    public RandomViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.random_ingredient_item_layout,parent,false);
        return new RandomViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull  RandomViewHolder holder, int position) {
            holder.ingredient.setText(ingredients.get(position));
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

}
