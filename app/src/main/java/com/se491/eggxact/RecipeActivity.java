package com.se491.eggxact;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.se491.eggxact.Runnables.RecipeIdSearchRunnable;
import com.se491.eggxact.structure.IngredientsAdapter;
import com.se491.eggxact.structure.Recipe;
import com.se491.eggxact.structure.RecipeInfo;
import com.se491.eggxact.ui.FavoriteActivity;

import com.se491.eggxact.ui.comment.CommentRecyclerActivity;
import com.se491.eggxact.ui.comment.CommentRecyclerAdapter;
import com.se491.eggxact.ui.ratingsact.RatingsActivity;

import com.se491.eggxact.structure.User;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.se491.eggxact.ui.landingpage.FavoritesFragment.*;

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

    private TextView likesText;
    private TextView dislikesText;
    private ImageView likeBtn;
    private ImageView dislikeBtn;

    //private Recipe recipe;

    private DatabaseReference referenceRecipe = FirebaseDatabase.getInstance().getReference().child("recipeHolder");
    private String key;


    private static final ArrayList<RecipeInfo> RECIPE_INFO_LIST = new ArrayList<>();

    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Favorites").child(user.getUid());
    private String id = user.getUid();


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

        likesText = findViewById(R.id.likesText);
        dislikesText = findViewById(R.id.dislikeText);
        likeBtn = findViewById(R.id.likeButton);
        dislikeBtn = findViewById(R.id.dislikeButton);



        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Eggxact: Recipe View");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        }

        if(getIntent().hasExtra("RecipeInfo")) {
            recipeInfo = (RecipeInfo) getIntent().getSerializableExtra("RecipeInfo");

            referenceRecipe.orderByChild("recipeId").equalTo(recipeInfo.getRecipeId()).addValueEventListener(new ValueEventListener() {
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
                        referenceRecipe.push().setValue(r);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            showData();
        }
        else if(getIntent().hasExtra("favInfo")){
            recipeInfo = (RecipeInfo) getIntent().getSerializableExtra("favInfo");
            showData();
            likeBtn.setVisibility(View.INVISIBLE);
            dislikeBtn.setVisibility(View.INVISIBLE);
            dislikesText.setVisibility(View.INVISIBLE);
            likesText.setVisibility(View.INVISIBLE);
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



        viewCommentsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecipeActivity.this, CommentRecyclerActivity.class);
                startActivity(intent);
            }
        });





    }

    private void incrementLikes(){
        long cnt = (long) Double.parseDouble(likesText.getText().toString());
        cnt++;
        likesText.setText((Long.toString(cnt)));
        long finalCnt = cnt;
        referenceRecipe.child(key).child("likes").setValue(finalCnt);
    }

    private void incrementDislikes(){
        long cnt = (long) Double.parseDouble(dislikesText.getText().toString());
        cnt++;
        dislikesText.setText((Long.toString(cnt)));
        long finalCnt = cnt;
        referenceRecipe.child(key).child("dislikes").setValue(finalCnt);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.recipe_act_menu,menu);

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.notfav);

        if(checkIfFavExist()){
            item.setIcon(R.drawable.baseline_favorite_24);
            item.setTitle("Favorite");
        }

        return super.onPrepareOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        boolean fav = true;
        switch (item.getItemId()) {
            case R.id.notfav:
                updateMenuFavIcon(item);
                addFavorite();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private boolean checkIfFavExist() {
        boolean exist = false;
        for(int i=0; i<RECIPE_INFO_LIST.size(); i++){
            if(RECIPE_INFO_LIST.get(i).getName().equals(recipeInfo.getName())){
                exist = true;
            }
        }
        return exist;
    }


    private void addFavorite() {
        if(recipeInfo == null){

        }
        else{
            if(RECIPE_INFO_LIST.isEmpty()){
                String uniqueKey = reference.push().getKey();
                recipeInfo.setRecipeId(uniqueKey);
                RECIPE_INFO_LIST.add(recipeInfo);
                reference.child(uniqueKey).setValue(recipeInfo);
                favList.add(recipeInfo);
            }
            else {
                if(!checkIfFavExist()){
                    String uniqueKey = reference.push().getKey();
                    recipeInfo.setRecipeId(uniqueKey);
                    RECIPE_INFO_LIST.add(recipeInfo);
                    reference.child(uniqueKey).setValue(recipeInfo);
                    favList.add(recipeInfo);
                }
            }

        }
    }

    private void updateMenuFavIcon(MenuItem item) {
        if(item.getTitle().equals("Not Favorite")){
            Toast.makeText(this, "Added to Favorites", Toast.LENGTH_SHORT).show();
            item.setIcon(R.drawable.baseline_favorite_24);
            item.setTitle("Favorite");
        }
        else {
            Toast.makeText(this, "Removed from Favorites", Toast.LENGTH_SHORT).show();
            item.setIcon(R.drawable.baseline_not_favorite_24);
            item.setTitle("Not Favorite");
        }
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

        referenceRecipe.orderByChild("recipeId").equalTo(recipeInfo.getRecipeId()).addValueEventListener(new ValueEventListener() {
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