package com.se491.eggxact.ui.landingpage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.se491.eggxact.R;

import java.util.ArrayList;

public class CatAdapter extends RecyclerView.Adapter<CatViewHolder> {


    ArrayList<String> catList = new ArrayList<>();

    public CatAdapter(ArrayList<String> catList) {
        this.catList = catList;
    }

    @NonNull
    @Override
    public CatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.categories_item_layout,parent,false);
        return  new CatViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull  CatViewHolder holder, int position) {
        holder.cardTxt.setText(catList.get(position));
    }

    @Override
    public int getItemCount() {
        return catList.size();
    }
}

