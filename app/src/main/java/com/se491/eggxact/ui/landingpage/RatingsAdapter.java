package com.se491.eggxact.ui.landingpage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.se491.eggxact.R;

import java.util.ArrayList;

public class RatingsAdapter extends RecyclerView.Adapter<RatingsViewHolder> {

    ArrayList<String> ratingsList;

    public RatingsAdapter(ArrayList<String> ratingsList) {
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
        holder.cardTxt.setText(ratingsList.get(position));
    }

    @Override
    public int getItemCount() {
        return ratingsList.size();
    }
}
