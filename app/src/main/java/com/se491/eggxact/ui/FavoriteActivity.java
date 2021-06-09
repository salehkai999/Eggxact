package com.se491.eggxact.ui;

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
import com.se491.eggxact.structure.Recipe;
import com.se491.eggxact.structure.RecipeAdapter;
import com.se491.eggxact.structure.RecipeInfo;
import com.se491.eggxact.ui.landingpage.FavAdapter;

import java.util.ArrayList;

public class FavoriteActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "FavoriteActivity";
    private RecyclerView recyclerView;
    public static ArrayList<Recipe> favRecipeList = new ArrayList<>();
    private FavAdapter favoriteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.fragment_favorites);
        recyclerView = findViewById(R.id.recRecycler);
        Intent intent = getIntent();
        if(intent.hasExtra("favorites")){
            Log.d(TAG, "onCreate: YES");
            ArrayList<Recipe> list = (ArrayList<Recipe>)intent.getSerializableExtra("favorites");
            this.setTitle("Favorites (" +list.size() + ")");
            favRecipeList.addAll(list);
            //favoriteAdapter = new FavAdapter(favRecipeList);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(favoriteAdapter);
        }

    }

    @Override
    public void onClick(View v) {
        int pos = recyclerView.getChildAdapterPosition(v);
        Recipe r = favRecipeList.get(pos);
        Log.d(TAG, "onClick: "+r.toString());
        try {
            //new Thread(new RecipeIdSearchRunnable(r.recipeId,this)).start();
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
