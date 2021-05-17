package com.se491.eggxact.ui.landingpage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.se491.eggxact.R;
import com.se491.eggxact.structure.Recipe;

import java.util.ArrayList;

public class RatingsAdapter extends RecyclerView.Adapter<RatingsViewHolder> {

    ArrayList<Recipe> ratingsList;

    public RatingsAdapter(ArrayList<Recipe> ratingsList) {
        this.ratingsList = ratingsList;
    }


    @NonNull
    @Override
    public RatingsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rating_item_layout,parent,false);
        return  new RatingsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull  RatingsViewHolder holder, int position) {
        holder.title.setText(ratingsList.get(position).getRecipeName());
        holder.likes.setText(String.valueOf(ratingsList.get(position).getLikes()));
        holder.disLikes.setText(String.valueOf(ratingsList.get(position).getDislikes()));
    }

    @Override
    public int getItemCount() {
        return ratingsList.size();
    }
}
