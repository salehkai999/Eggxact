package com.se491.eggxact.ui.landingpage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.se491.eggxact.R;
import com.se491.eggxact.structure.Category;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CuisinesAdapter  extends RecyclerView.Adapter<CatViewHolder> {

    ArrayList<Category> catList = new ArrayList<>();
    CategoriesFragment categoriesFragment;
    CuisinesOnClick cuisinesOnClick;

    public CuisinesAdapter(ArrayList<Category> catList, CategoriesFragment categoriesFragment) {
        this.catList = catList;
        this.categoriesFragment = categoriesFragment;
    }

    public void setCuisinesOnClick(CuisinesOnClick cuisinesOnClick) {
        this.cuisinesOnClick = cuisinesOnClick;
    }

    @NonNull
    @Override
    public CatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.categories_item_layout,parent,false);
        return  new CatViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull  CatViewHolder holder, int position) {
        holder.cardTxt.setText(catList.get(position).getName());
        Picasso.get().load(catList.get(position).getImg()).error(R.drawable.brokenimage)
                .placeholder(R.drawable.placeholder).into(holder.image, new Callback() {
            @Override
            public void onSuccess() {
                holder.cardTxt.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onError(Exception e) {
                holder.cardTxt.setVisibility(View.VISIBLE);
                holder.image.setVisibility(View.INVISIBLE);
            }
        });
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cuisinesOnClick != null){
                    cuisinesOnClick.onCuisineClick(catList.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return catList.size();
    }

    public interface CuisinesOnClick{
        public void onCuisineClick(Category category);
    }
}
