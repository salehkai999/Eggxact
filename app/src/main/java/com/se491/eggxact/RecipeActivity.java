package com.se491.eggxact;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.se491.eggxact.structure.IngredientsAdapter;
import com.se491.eggxact.structure.Recipe;
import com.se491.eggxact.structure.RecipeAdapter;
import com.se491.eggxact.structure.RecipeInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RecipeActivity extends AppCompatActivity {

    TextView titleView;
    TextView prepTimeView;
    TextView cookTimeView;
    TextView totalTimeView;
    TextView instructionsView;

    RecyclerView ingredientsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_u_i);

        RecipeInfo recipeInfo = (RecipeInfo) getIntent().getSerializableExtra("RecipeInfo");
        Log.d("RecipeActivity", "passedRecipeObject: "+recipeInfo.toString());

        titleView = findViewById(R.id.Title);
        prepTimeView = findViewById(R.id.prepTime);
        cookTimeView = findViewById(R.id.cookTime);
        totalTimeView = findViewById(R.id.totalTime);
        instructionsView = findViewById(R.id.instructions);

        ingredientsView = findViewById(R.id.ingredientsList);



        titleView.setText(recipeInfo.getName());
        prepTimeView.setText("Prep Time: " + recipeInfo.getPrepTime() + " mins");
        cookTimeView.setText("Cook Time: " + recipeInfo.getCookingTime() + " mins");
        totalTimeView.setText("Total Time: "+ recipeInfo.getReadyMinutes() + " mins");

        IngredientsAdapter ingredientsAdapter = new IngredientsAdapter(this, recipeInfo.getIngredients());

        ingredientsView.setAdapter(ingredientsAdapter);
        ingredientsView.setLayoutManager(new LinearLayoutManager(this));

        instructionsView.setText(recipeInfo.getInstructions());
        instructionsView.setMovementMethod(new ScrollingMovementMethod());


//        List<String> ingred = new ArrayList<String>();
//        ingred.add("Lasagna Sheet");
//        ingred.add("Tomatoe Sauce");
//        ingred.add("Ground Beef");
//        ingred.add("Shredded Cheese");
//        ingred.add("Salt");
//        ingred.add("Black Pepper");
//        ingred.add("Cottage Cheese");
//
//        String[] test = {"Lasagna","Tomatoe","Beef","Shredded Cheese"};
//
//





//        btn_getRecipe.setOnClickListener(new View.OnClickListener() {
//
//
//            @Override
//            public void onClick(View v) {
//
//                titleView.setText(recipeInfo.getName());
//                authorView.setText(recipe.author);
//                summaryView.setText(recipe.summary);
////                ingredientsView.setAdapter(adapter);
//
//
//
//
//            }
//        }) ;

    }


}