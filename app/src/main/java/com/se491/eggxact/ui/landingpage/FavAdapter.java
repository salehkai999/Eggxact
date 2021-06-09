package com.se491.eggxact.ui.landingpage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.se491.eggxact.R;
import com.se491.eggxact.structure.Recipe;

import java.util.ArrayList;

public class FavAdapter extends RecyclerView.Adapter<FavViewHolder> {

    private ArrayList<Recipe> favList;

    public FavAdapter(ArrayList<Recipe> favList) {
        this.favList = favList;
    }

    @NonNull
    @Override
    public FavViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fav_recycler_item_layout,parent,false);
        return new FavViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull  FavViewHolder holder, int position) {
        holder.title.setText(favList.get(position).getRecipeName());

    }

    @Override
    public int getItemCount() {
        return favList.size();
    }
}
