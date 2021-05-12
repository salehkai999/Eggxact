package com.se491.eggxact.structure;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.common.util.Strings;
import com.se491.eggxact.AdvSearchActivity;
import com.se491.eggxact.R;
import com.se491.eggxact.RecipeActivity;

import java.util.ArrayList;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.ViewHolder> {

    Context context;
    private ArrayList<String> data = new ArrayList<String>();

    public IngredientsAdapter(Context ct, ArrayList<String> data){
        this.context = ct;
        this.data = data;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView ing;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ing = itemView.findViewById(R.id.ingItem);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.ingredients_item_layout, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.ing.setText(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


}
