package com.se491.eggxact.ui.recommendation;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.se491.eggxact.R;
import com.se491.eggxact.RecipeActivity;
import com.se491.eggxact.Runnables.RecipeIdSearchRunnable;
import com.se491.eggxact.structure.Category;
import com.se491.eggxact.structure.Recipe;
import com.se491.eggxact.structure.RecipeAdapter;
import com.se491.eggxact.structure.RecipeInfo;
import com.se491.eggxact.ui.categoryact.CategoryActivity;
import com.se491.eggxact.ui.landingpage.CatAdapter;
import com.se491.eggxact.ui.ratingsact.RatingsActAdapter;

import java.util.ArrayList;


public class RecommendationActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "RecommendationActivity";
    private RecyclerView recyclerView;
    private static final ArrayList<Recipe> recipeList = new ArrayList<>();
    private RecipeAdapter recipeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendation);
        recyclerView = findViewById(R.id.recRecycler);
        Intent intent = getIntent();
        if(intent.hasExtra("recommendations")){
            Log.d(TAG, "onCreate: YES");
            ArrayList<Recipe> list = (ArrayList<Recipe>)intent.getSerializableExtra("recommendations");
            this.setTitle("Recommendations (" +list.size() + ")");
            recipeList.addAll(list);
            recipeAdapter = new RecipeAdapter(recipeList, this);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(recipeAdapter);
        }

    }

    @Override
    public void onClick(View v) {
        int pos = recyclerView.getChildAdapterPosition(v);
        Recipe r = recipeList.get(pos);
        Log.d(TAG, "onClick: "+r.toString());
        try {
            new Thread(new RecipeIdSearchRunnable(r.recipeId,this)).start();
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
