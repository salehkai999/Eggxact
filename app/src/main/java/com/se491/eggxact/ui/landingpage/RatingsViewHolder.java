package com.se491.eggxact.ui.landingpage;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.se491.eggxact.R;

public class RatingsViewHolder extends RecyclerView.ViewHolder {
    TextView title;
    TextView likes;
    TextView disLikes;
    public RatingsViewHolder(@NonNull View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.titleRatingRecycler);
        likes = itemView.findViewById(R.id.likesTxt);
        disLikes = itemView.findViewById(R.id.dislikesTxt);
    }
}
