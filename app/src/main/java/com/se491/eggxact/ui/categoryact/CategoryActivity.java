package com.se491.eggxact.ui.categoryact;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.se491.eggxact.R;
import com.se491.eggxact.RecipeActivity;
import com.se491.eggxact.Runnables.RecipeIdSearchRunnable;
import com.se491.eggxact.structure.Category;
import com.se491.eggxact.structure.Recipe;
import com.se491.eggxact.structure.RecipeAdapter;
import com.se491.eggxact.structure.RecipeInfo;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "CategoryActivity";
    private static final ArrayList<Recipe> recipeList = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecipeAdapter recipeAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        recyclerView = findViewById(R.id.catActRecycler);
        Intent intent = getIntent();
        if(intent.hasExtra(Category.class.getName())){
            Log.d(TAG, "onCreate: YES");
            Category c = (Category)intent.getSerializableExtra(Category.class.getName());
            this.setTitle(c.getName()+" ("+c.getRecipes().size()+")");
            recipeList.addAll(c.getRecipes());
            recipeAdapter = new RecipeAdapter(c.getRecipes(),this);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(recipeAdapter);
        }
        else if(intent.hasExtra(Recipe.class.getName())) {
            ArrayList<Recipe> r = (ArrayList<Recipe>) intent.getSerializableExtra(Recipe.class.getName());
            recipeList.addAll(r);
            recipeAdapter = new RecipeAdapter(r,this);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(recipeAdapter);
        }

    }

    @Override
    public void onClick(View v) {
        int pos = recyclerView.getChildAdapterPosition(v);
        Recipe recipe = recipeList.get(pos);
        Log.d(TAG, "onClick: "+recipe.toString());
        try {
            new Thread(new RecipeIdSearchRunnable(recipe.recipeId,this)).start();
        }
        catch (Exception e){
            e.printStackTrace();
            Log.d(TAG, "onClick: "+e.getMessage());
        }
    }

    public void passRecipeObject(RecipeInfo recipeInfo) {
        Log.d(TAG, "passRecipeObject: "+recipeInfo.toString());
        Intent i = new Intent(this, RecipeActivity.class);
        i.putExtra("RecipeInfo",recipeInfo);
        startActivity(i);
    }
}