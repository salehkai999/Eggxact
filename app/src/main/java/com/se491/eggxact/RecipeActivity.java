package com.se491.eggxact;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.se491.eggxact.Runnables.RecipeIdSearchRunnable;
import com.se491.eggxact.structure.IngredientsAdapter;
import com.se491.eggxact.structure.Recipe;
import com.se491.eggxact.structure.RecipeAdapter;
import com.se491.eggxact.structure.RecipeInfo;
import com.se491.eggxact.structure.User;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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

    private TextView likesText;
    private TextView dislikesText;
    private ImageView likeBtn;
    private ImageView dislikeBtn;

    //private Recipe recipe;

    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("recipeHolder");
    private String key;


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

        likesText = findViewById(R.id.likesText);
        dislikesText = findViewById(R.id.dislikeText);
        likeBtn = findViewById(R.id.likeButton);
        dislikeBtn = findViewById(R.id.dislikeButton);



        if(getIntent().hasExtra("RecipeInfo")) {
            recipeInfo = (RecipeInfo) getIntent().getSerializableExtra("RecipeInfo");

            reference.orderByChild("recipeId").equalTo(recipeInfo.getRecipeId()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){
//                        Log.d(TAG, "Data exists " + recipeInfo.getRecipeId());
                        //will get likes and dislikes values here
                    }
                    else{
                        // Add the recipe to recipeHolder with 0 likes and 0 dislikes
                        Log.d(TAG, "Data does not exist for recipeId " + recipeInfo.getRecipeId());
                        Recipe r = new Recipe();
                        r.setRecipeId(recipeInfo.getRecipeId());
                        r.setDislikes(0);
                        r.setLikes(0);
                        r.setRecipeName(recipeInfo.getName());
                        r.setSourceUrl("");
                        reference.push().setValue(r);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            showData();
        }
        else {
            progressBar.setVisibility(View.VISIBLE);
            hideViews();
            Log.d(TAG, "onCreate: FOUND");
            downloadDataThenShow();
        }

        likeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incrementLikes();
            }
        });

        dislikeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incrementDislikes();
            }
        });




    }

    private void incrementLikes(){
        long cnt = (long) Double.parseDouble(likesText.getText().toString());
        cnt++;
        likesText.setText((Long.toString(cnt)));
        long finalCnt = cnt;
        reference.child(key).child("likes").setValue(finalCnt);
    }

    private void incrementDislikes(){
        long cnt = (long) Double.parseDouble(dislikesText.getText().toString());
        cnt++;
        dislikesText.setText((Long.toString(cnt)));
        long finalCnt = cnt;
        reference.child(key).child("dislikes").setValue(finalCnt);
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
        likesText.setVisibility(View.INVISIBLE);
        dislikesText.setVisibility(View.INVISIBLE);
        likeBtn.setVisibility(View.INVISIBLE);
        dislikeBtn.setVisibility(View.INVISIBLE);
    }

    private void downloadDataThenShow() {
        Recipe recipe = (Recipe) getIntent().getSerializableExtra(Recipe.class.getName());
        Log.d(TAG, "downloadDataThenShow: "+recipe.toString());
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

        reference.orderByChild("recipeId").equalTo(recipeInfo.getRecipeId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    Log.d(TAG, "Data exists here" + snapshot.getValue());
                    Recipe r = new Recipe();
                    for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                        r = dataSnapshot.getValue(Recipe.class);
                        key = dataSnapshot.getKey();
                        Log.d(TAG,"Key exists here " + key);

                    }
                    Log.d(TAG,"Likes " + r.toString() );
                    likesText.setText(String.valueOf(r.getLikes()));
                    dislikesText.setText(String.valueOf(r.getDislikes()));
                }
                else{
//                    Log.d(TAG, "Data does not exist for recipeId " + recipe.getRecipeId());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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
        likesText.setVisibility(View.VISIBLE);
        dislikesText.setVisibility(View.VISIBLE);
        likeBtn.setVisibility(View.VISIBLE);
        dislikeBtn.setVisibility(View.VISIBLE);
    }
}