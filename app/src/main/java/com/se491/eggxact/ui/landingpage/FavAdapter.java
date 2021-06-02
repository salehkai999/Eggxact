package com.se491.eggxact.ui.landingpage;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.se491.eggxact.R;

import java.util.ArrayList;

public class FavAdapter extends RecyclerView.Adapter<FavViewHolder> {

    private ArrayList<String> favList;
    private Context context;
    private FavoritesFragment favoritesFragment;
    private String favData;
    private  int deletePos;


    public FavAdapter(ArrayList<String> favList, Context context, FavoritesFragment favoritesFragment) {
        this.favList = favList;
        this.context = context;
        this.favoritesFragment = favoritesFragment;
    }

    @NonNull
    @Override
    public FavViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fav_recycler_item_layout,parent,false);
        return new FavViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull  FavViewHolder holder, int position) {
        holder.title.setText(favList.get(position));
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
        String str = favList.remove(pos);
        notifyItemRemoved(pos);
        showSnackbar();
    }

    private void showSnackbar() {
        View view = favoritesFragment.getView().findViewById(R.id.coordinator);
        Snackbar snackbar = Snackbar.make(view, "You deleted " + favData, Snackbar.LENGTH_LONG);
        snackbar.setActionTextColor(Color.BLUE);
        snackbar.setAction("Undo", v -> {
            favList.add(deletePos,favData);
            notifyItemInserted(deletePos);
        });
        snackbar.show();
    }


}
