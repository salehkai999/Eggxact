package com.se491.eggxact.ui.landingpage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.se491.eggxact.R;
import com.se491.eggxact.structure.Category;

import java.util.ArrayList;

public class CatAdapter extends RecyclerView.Adapter<CatViewHolder> {


    ArrayList<Category> catList = new ArrayList<>();
    CategoriesFragment categoriesFragment;

    public CatAdapter(ArrayList<Category> catList, CategoriesFragment categoriesFragment) {
        this.catList = catList;
        this.categoriesFragment = categoriesFragment;
    }

    public CatAdapter(ArrayList<Category> catList) {
        this.catList = catList;
    }

    @NonNull
    @Override
    public CatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.categories_item_layout,parent,false);
        itemView.setOnClickListener(categoriesFragment);
        return  new CatViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull  CatViewHolder holder, int position) {
        holder.cardTxt.setText(catList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return catList.size();
    }
}

