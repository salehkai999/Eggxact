package com.se491.eggxact.ui.filtering;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.se491.eggxact.R;

public class FilterViewHolder extends RecyclerView.ViewHolder {

    ImageView image;
    TextView titleTxt;

    public FilterViewHolder(@NonNull  View itemView) {
        super(itemView);
        image = itemView.findViewById(R.id.filterResImg);
        titleTxt = itemView.findViewById(R.id.filter_result);
    }
}
