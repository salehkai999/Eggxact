package com.se491.eggxact.ui.landingpage;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.se491.eggxact.R;

public class RatingsViewHolder extends RecyclerView.ViewHolder {
    TextView cardTxt;
    public RatingsViewHolder(@NonNull View itemView) {
        super(itemView);
        cardTxt = itemView.findViewById(R.id.titleRatingRecycler);
    }
}
