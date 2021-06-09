package com.se491.eggxact.ui.filtering;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.se491.eggxact.R;
import com.se491.eggxact.structure.Recipe;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FilterAdapter extends RecyclerView.Adapter<FilterViewHolder> {

    private static final String TAG = "FilterAdapter";
    private ArrayList<Recipe> recipes;
    private FilterResults filterResults;

    public FilterAdapter(ArrayList<Recipe> recipes, FilterResults filterResults) {
        this.recipes = recipes;
        this.filterResults = filterResults;
    }

    @NonNull
    @Override
    public FilterViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.filter_result_item,parent,false);
        itemView.setOnClickListener(filterResults);
        return new FilterViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull  FilterViewHolder holder, int position) {
        Recipe recipe = recipes.get(position);
        if(recipe.getRecipeName().length() > 31){
            holder.titleTxt.setText(recipe.getRecipeName().substring(0,27)+"\n"+recipe.getRecipeName().substring(27,recipe.getRecipeName().length()));
        }
        else {
            holder.titleTxt.setText(recipe.getRecipeName());
        }
        ImageView img = holder.image;
        Picasso.get().load(recipe.getSourceUrl())
                .into(img,
                        new Callback() {
                            @Override
                            public void onSuccess() {
                                Log.d(TAG, "onSuccess: ");
                            }

                            @Override
                            public void onError(Exception e) {
                                Log.d(TAG, "onError: " + e.getMessage());
                            }
                        });
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }
}
