package com.se491.eggxact.ui.ratingsact;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.se491.eggxact.AdvSearchActivity;
import com.se491.eggxact.R;
import com.se491.eggxact.RecipeActivity;
import com.se491.eggxact.Runnables.RecipeIdSearchRunnable;
import com.se491.eggxact.dbutil.CategoriesHelper;
import com.se491.eggxact.structure.Recipe;
import com.se491.eggxact.structure.RecipeInfo;
import com.se491.eggxact.ui.landingpage.CatAdapter;
import com.se491.eggxact.ui.landingpage.RatingsAdapter;

import java.util.ArrayList;

public class RatingsActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "RatingsActivity";
    RecyclerView recyclerView;
    RatingsActAdapter ratingsAdapter;
    ArrayList<Recipe> recipes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ratings);
        recyclerView = findViewById(R.id.ratingsRecycler);
        if(getIntent().hasExtra("ratings")) {
            recipes = (ArrayList<Recipe>) getIntent().getSerializableExtra("ratings");
            ratingsAdapter = new RatingsActAdapter(this,recipes);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(ratingsAdapter);
            this.setTitle("Currently Rated : ("+recipes.size()+")");
        }
        Log.d(TAG, "onCreate: "+recipes.size());

    }

    @Override
    public void onClick(View v) {
            if(!recipes.isEmpty()){
                int pos = recyclerView.getChildAdapterPosition(v);
                Toast.makeText(this, recipes.get(pos).getRecipeName(), Toast.LENGTH_SHORT).show();
                new Thread(new RecipeIdSearchRunnable(recipes.get(pos).recipeId,this)).start();
            }
    }

    public void passRecipeObject(RecipeInfo recipeInfo) {
        Log.d(TAG, "passRecipeObject: "+recipeInfo.toString());
        Intent i = new Intent(this, RecipeActivity.class);
        i.putExtra("RecipeInfo",recipeInfo);
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.ratings_act_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.advSearchItem:
                //Toast.makeText(this, "EDIT ADD", Toast.LENGTH_SHORT).show();
                openAdvSearch();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openAdvSearch() {
        Intent intent = new Intent(this, AdvSearchActivity.class);
        startActivity(intent);
    }
}