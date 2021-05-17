package com.se491.eggxact.ui.ratingsact;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.se491.eggxact.R;

public class RatingsActViewHolder extends RecyclerView.ViewHolder{

    TextView title;
    TextView likes;
    TextView dislikes;

    public RatingsActViewHolder(@NonNull  View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.ratingsActTitle);
        likes = itemView.findViewById(R.id.likesRatingsAct);
        dislikes = itemView.findViewById(R.id.dislikeRatingsAct);
    }
}
