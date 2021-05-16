package com.se491.eggxact.ui.landingpage;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.se491.eggxact.R;

public class FavViewHolder extends RecyclerView.ViewHolder {

    TextView title;
    public FavViewHolder(@NonNull View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.favRecTitle);
    }
}
