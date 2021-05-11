package com.se491.eggxact;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.se491.eggxact.structure.Recipe;
import com.se491.eggxact.structure.RecipeAdapter;
import com.se491.eggxact.structure.RecipeInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RecipeUI extends AppCompatActivity {

    Button btn_getRecipe;
    TextView titleView;
    TextView authorView;
    TextView summaryView;
    RecyclerView ingredientsView;
//    RecipeAdapter ingredientsAdapter;

    String title;
    String author;
    String summary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_u_i);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        RecipeInfo recipeInfo = (RecipeInfo) getIntent().getSerializableExtra("RecipeInfo");
        Log.d("RecipeId", "passedRecipeObject: "+recipeInfo.toString());

        //assign values to each view
        btn_getRecipe = findViewById(R.id.showRecipe);
        titleView = findViewById(R.id.Title);
        authorView = findViewById(R.id.Author);
        summaryView = findViewById(R.id.Summary);
        ingredientsView = findViewById(R.id.ingredientsList);

        List<String> ingred = new ArrayList<String>();
        ingred.add("Lasagna Sheet");
        ingred.add("Tomatoe Sauce");
        ingred.add("Ground Beef");
        ingred.add("Shredded Cheese");
        ingred.add("Salt");
        ingred.add("Black Pepper");
        ingred.add("Cottage Cheese");

        String[] test = {"Lasagna","Tomatoe","Beef","Shredded Cheese"};

        Recipez recipe = new Recipez("Lasagna","Korey Lo", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. " +
                                                                        "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor " +
                                                                        "in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa " +
                                                                        "qui officia deserunt mollit anim id est laborum.", ingred);

        ;




        btn_getRecipe.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                titleView.setText(recipe.title);
                authorView.setText(recipe.author);
                summaryView.setText(recipe.summary);
//                ingredientsView.setAdapter(adapter);




            }
        }) ;

    }


}