package com.se491.eggxact.ui.filtering;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.se491.eggxact.R;
import com.se491.eggxact.structure.Recipe;

import java.util.ArrayList;

public class FilterResults extends AppCompatActivity {

    private ArrayList<Recipe> recipes;
    private RecyclerView recyclerView;
    private FilterAdapter filterAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_results);
        recyclerView = findViewById(R.id.filters_recycler);
        if(getIntent().hasExtra(Recipe.class.getName())){
            recipes = (ArrayList<Recipe>)getIntent().getSerializableExtra(Recipe.class.getName());
            filterAdapter = new FilterAdapter(recipes,this);
            recyclerView.setAdapter(filterAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            this.setTitle("Found ("+recipes.size()+")");
        }
        else {
            this.setTitle("Oops!! Something went wrong.");
        }
    }
}