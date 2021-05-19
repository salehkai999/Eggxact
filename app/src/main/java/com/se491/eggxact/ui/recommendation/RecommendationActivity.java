package com.se491.eggxact.ui.recommendation;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.se491.eggxact.R;
import com.se491.eggxact.structure.Recipe;
import com.se491.eggxact.ui.ratingsact.RatingsActAdapter;

import java.util.ArrayList;


public class RecommendationActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "RecommendationActivity";
    RecyclerView recyclerView;
    RatingsActAdapter recommendationAdapter;
    ArrayList<Recipe> recipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recommendation_item_layout);
        //recyclerView = findViewById(R.id.ratingsRecycler);
    }

    @Override
    public void onClick(View v) {

    }
}
