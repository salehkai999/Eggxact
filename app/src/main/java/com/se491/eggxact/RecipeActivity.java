package com.se491.eggxact;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.se491.eggxact.Runnables.RecipeIdSearchRunnable;
import com.se491.eggxact.structure.IngredientsAdapter;
import com.se491.eggxact.structure.Recipe;
import com.se491.eggxact.structure.RecipeInfo;
import com.se491.eggxact.ui.comment.CommentRecyclerActivity;
import com.se491.eggxact.ui.comment.CommentRecyclerAdapter;
import com.se491.eggxact.ui.ratingsact.RatingsActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecipeActivity extends AppCompatActivity {

    private static final String TAG = "RecipeActivity";
    private TextView titleView;
    private TextView prepTimeView;
    private TextView cookTimeView;
    private TextView totalTimeView;
    private TextView instructionsView;
    private TextView ingredientsTxt;
    private TextView instructionsTxt;
    private ImageView recipeImg;
    private RecyclerView ingredientsView;
    private RecipeInfo recipeInfo;
    private ProgressBar progressBar;
    private Button viewCommentsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_u_i);

        titleView = findViewById(R.id.Title);
        prepTimeView = findViewById(R.id.prepTime);
        cookTimeView = findViewById(R.id.cookTime);
        totalTimeView = findViewById(R.id.totalTime);
        instructionsView = findViewById(R.id.instructions);
        instructionsTxt = findViewById(R.id.instructionTitle);
        ingredientsTxt = findViewById(R.id.Ingredients);
        recipeImg = findViewById(R.id.recipeImg);
        ingredientsView = findViewById(R.id.ingredientsList);
        progressBar = findViewById(R.id.progressBarRecipe);
        viewCommentsButton = findViewById(R.id.commentButton);

        if(getIntent().hasExtra("RecipeInfo")) {
            recipeInfo = (RecipeInfo) getIntent().getSerializableExtra("RecipeInfo");
            showData();
            Log.d("RecipeActivity", "passedRecipeObject: " + recipeInfo.toString());
        }
        else {
            progressBar.setVisibility(View.VISIBLE);
            hideViews();
            Log.d(TAG, "onCreate: FOUND");
            downloadDataThenShow();
        }



        viewCommentsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecipeActivity.this, CommentRecyclerActivity.class);
                startActivity(intent);
            }
        });


    }

    private void hideViews() {
        titleView.setVisibility(View.INVISIBLE);
        prepTimeView.setVisibility(View.INVISIBLE);
        cookTimeView.setVisibility(View.INVISIBLE);
        totalTimeView.setVisibility(View.INVISIBLE);
        instructionsView.setVisibility(View.INVISIBLE);
        recipeImg.setVisibility(View.INVISIBLE);
        ingredientsView.setVisibility(View.INVISIBLE);
        instructionsTxt.setVisibility(View.INVISIBLE);
        ingredientsTxt.setVisibility(View.INVISIBLE);
    }

    private void downloadDataThenShow() {
        Recipe recipe = (Recipe) getIntent().getSerializableExtra(Recipe.class.getName());
        Log.d(TAG, "downloadDataThenShow: "+recipe.getRecipeId());
        new Thread(new RecipeIdSearchRunnable(recipe.getRecipeId(),this)).start();
    }


    public void passRecipeObject(RecipeInfo recipeInfo) {
        this.recipeInfo = recipeInfo;
        showData();
    }

    private void showData() {
        Log.d(TAG, "showData: ");
        showViews();
        titleView.setText(recipeInfo.getName());
        prepTimeView.setText("Prep Time: " + recipeInfo.getPrepTime() + " mins");
        cookTimeView.setText("Cook Time: " + recipeInfo.getCookingTime() + " mins");
        totalTimeView.setText("Total Time: " + recipeInfo.getReadyMinutes() + " mins");

        IngredientsAdapter ingredientsAdapter = new IngredientsAdapter(this, recipeInfo.getIngredients());

        ingredientsView.setAdapter(ingredientsAdapter);
        ingredientsView.setLayoutManager(new LinearLayoutManager(this));

        instructionsView.setText(recipeInfo.getInstructions().replaceAll("\\<.*?>", ""));
        instructionsView.setMovementMethod(new ScrollingMovementMethod());

        Picasso.get().load(recipeInfo.getImgURL()).error(R.drawable.brokenimage)
                .placeholder(R.drawable.placeholder).into(recipeImg);

        progressBar.setVisibility(View.GONE);
    }

    private void showViews() {
        titleView.setVisibility(View.VISIBLE);
        prepTimeView.setVisibility(View.VISIBLE);
        cookTimeView.setVisibility(View.VISIBLE);
        totalTimeView.setVisibility(View.VISIBLE);
        instructionsView.setVisibility(View.VISIBLE);
        recipeImg.setVisibility(View.VISIBLE);
        ingredientsView.setVisibility(View.VISIBLE);
        instructionsTxt.setVisibility(View.VISIBLE);
        ingredientsTxt.setVisibility(View.VISIBLE);
    }
}