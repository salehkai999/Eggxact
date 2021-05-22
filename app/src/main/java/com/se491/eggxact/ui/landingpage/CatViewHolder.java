package com.se491.eggxact.ui.landingpage;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.se491.eggxact.R;

public class CatViewHolder extends RecyclerView.ViewHolder {
    TextView cardTxt;
    ImageView image;
    public CatViewHolder(@NonNull View itemView) {
        super(itemView);
        cardTxt = itemView.findViewById(R.id.cardTxt);
        image = itemView.findViewById(R.id.cardImg);
    }
}
