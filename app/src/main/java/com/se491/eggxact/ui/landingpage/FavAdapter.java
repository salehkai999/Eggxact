package com.se491.eggxact.ui.landingpage;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.se491.eggxact.R;
import com.se491.eggxact.structure.RecipeInfo;
import com.se491.eggxact.structure.Recipe;


import java.util.ArrayList;

public class FavAdapter extends RecyclerView.Adapter<FavViewHolder> {

    private static final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Favorites").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
    private ArrayList<RecipeInfo> favList;
    private Context context;
    private FavoritesFragment favoritesFragment;
    private RecipeInfo favData;
    private  int deletePos;


    public FavAdapter(ArrayList<RecipeInfo> favList, Context context, FavoritesFragment favoritesFragment) {
        this.favList = favList;
        this.context = context;
        this.favoritesFragment = favoritesFragment;
    }

    @NonNull
    @Override
    public FavViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fav_recycler_item_layout,parent,false);
        itemView.setOnClickListener(favoritesFragment);
        return new FavViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull  FavViewHolder holder, int position) {

        if(favList.get(position).getName().length() > 31) {
            holder.title.setText(favList.get(position).getName().substring(0,31)+"...");
        }
        else
            holder.title.setText(favList.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return favList.size();
    }

    public Context getContext() {
        return context;
    }

    public void deleteFave(int pos){
        favData = favList.get(pos);
        deletePos = pos;
        RecipeInfo str = favList.remove(pos);
        removeFromDB(favData.getRecipeId());
        notifyItemRemoved(pos);
        showSnackbar();
    }

    private void removeFromDB(String pos) {
        databaseReference.child(pos).removeValue();
    }

    private void showSnackbar() {
        View view = favoritesFragment.getView().findViewById(R.id.coordinator);
        Snackbar snackbar = Snackbar.make(view, "You deleted " + favData, Snackbar.LENGTH_LONG);
        snackbar.setActionTextColor(Color.BLUE);
        snackbar.setAction("Undo", v -> {
            favList.add(deletePos,favData);
            readdToDB(favData);
            notifyItemInserted(deletePos);
        });
        snackbar.show();
    }

    private void readdToDB( RecipeInfo favData) {
        databaseReference.child(favData.getRecipeId()).setValue(favData);
    }


}
