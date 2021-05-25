package com.se491.eggxact.ui.landingpage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.se491.eggxact.R;
import com.se491.eggxact.structure.Category;

import java.util.ArrayList;

public class RecommendationAdapter extends RecyclerView.Adapter<RecommendationHolder> {


    ArrayList<Category> recommendations = new ArrayList<>();
    RecomendationsFragment recommendationsFragment;
    HomeFragment homeFragment;

    public RecommendationAdapter(ArrayList<Category> recList, RecomendationsFragment recommendationsFragment) {
        this.recommendations = recList;
        this.recommendationsFragment = recommendationsFragment;
    }

    public RecommendationAdapter(ArrayList<Category> recList, HomeFragment homeFragment){
        this.recommendations = recList;
        this.homeFragment = homeFragment;
    }


    public RecommendationAdapter(ArrayList<Category> recList) {
        this.recommendations = recList;
    }

    @NonNull
    @Override
    public RecommendationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_item_layout,parent,false);
        if(recommendationsFragment != null)
            itemView.setOnClickListener(recommendationsFragment);
        else if(homeFragment != null)
            itemView.setOnClickListener(homeFragment);
        return  new RecommendationHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull  RecommendationHolder holder, int position) {
        holder.recommendationRecipe.setText(recommendations.get(position).getName());

//        Picasso.get().load(recommendations.get(position).getImg()).error(R.drawable.brokenimage)
//                .placeholder(R.drawable.placeholder).into(holder.image, new Callback() {
//            @Override
//            public void onSuccess() {
//                holder.cardTxt.setVisibility(View.INVISIBLE);
//            }
//
//            @Override
//            public void onError(Exception e) {
//                holder.cardTxt.setVisibility(View.VISIBLE);
//                holder.image.setVisibility(View.INVISIBLE);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return recommendations.size();
    }



}
