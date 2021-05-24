package com.se491.eggxact;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.se491.eggxact.Runnables.RandomRecipeRunnable;
import com.se491.eggxact.Runnables.RecipeIdSearchRunnable;
import com.se491.eggxact.Runnables.RecipeSearchRunnable;
import com.se491.eggxact.dbutil.CategoriesHelper;
import com.se491.eggxact.structure.Category;
import com.se491.eggxact.structure.Recipe;
import com.se491.eggxact.structure.RecipeAdapter;
import com.se491.eggxact.structure.RecipeInfo;

import java.util.ArrayList;

public class AdvSearchActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "AdvSearchActivity";
    private static final ArrayList<Recipe> recipeList = new ArrayList<>();
    private DatabaseReference databaseReference;
    RecyclerView recyclerView;
    RecipeAdapter recipeAdapter;
    EditText searchByName;
    String categoryStr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adv_search);


        searchByName = findViewById(R.id.searchNameTxt);
        recyclerView = findViewById(R.id.recycler);
        recipeAdapter = new RecipeAdapter(recipeList,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(recipeAdapter);


       // new Thread(new RandomRecipeRunnable()).start();

        databaseReference = FirebaseDatabase.getInstance().getReference().child("cuisines");

        if(getIntent().hasExtra("searchText")){
            Intent intent = getIntent();
            String searchText = intent.getExtras().getString("searchText");
            doSearch(searchText);
            searchByName.setText("");
        }

        searchByName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if ((actionId == EditorInfo.IME_ACTION_GO)) {
                    Log.i(TAG,"Enter pressed");
                    Log.d(TAG, "onEditorAction: "+getBaseContext().toString());
                    if(searchByName.getText().toString().trim().isEmpty()){
                        searchByName.setError("Can't be empty!!");
                    }else {
                        doSearch(searchByName.getText().toString().trim());
                        categoryStr = searchByName.getText().toString().toUpperCase().trim();
                        searchByName.setText("");
                    }

                }
                return false;
            }
        });


    }

    private void saveCategoriesToDB() {
        Category category = new Category();
        category.setName(categoryStr);
        category.addAllRecipe(recipeList);
        category.setImg("https://images-na.ssl-images-amazon.com/images/I/31unEqr67gL._AC_SY355_.jpg");
        databaseReference.push().setValue(category);
    }

    private void doSearch(String query) {
        new Thread(new RecipeSearchRunnable(this,query)).start();
    }

    public void showData(ArrayList<Recipe> recipeArrayList) {
        recipeList.clear();
        recipeList.addAll(recipeArrayList);
        recipeAdapter.notifyDataSetChanged();
        //saveDataToDB();
        //saveCategoriesToDB();
        Log.d(TAG, "showData: "+recipeList.size());
    }

    private void saveDataToDB() {
        Log.d(TAG, "saveDataToDB: ");
        for(Recipe r : recipeList){
            databaseReference.push().setValue(r);
        }
    }

    @Override
    public void onClick(View v) {
        int pos = recyclerView.getChildAdapterPosition(v);
        Recipe recipe = recipeList.get(pos);
        Toast.makeText(this, recipe.recipeName+" : "+recipe.recipeId, Toast.LENGTH_SHORT).show();
        new Thread(new RecipeIdSearchRunnable(recipe.recipeId,this)).start();
        // I will consider adding a progress bar to let the user know that the app is still working LOL.
    }

    public void passRecipeObject(RecipeInfo recipeInfo) {
        Log.d(TAG, "passRecipeObject: "+recipeInfo.toString());
        /* Moving to RecipeActivity here, pass the recipeInfo object .putExtra and in your activity it should check if there's an extra and use getSerializableExtra (You can comment out the logs)
            Logs are useful for debugging and getting the functionality right.
         */
        Intent i = new Intent(this,RecipeActivity.class);
        i.putExtra("RecipeInfo",recipeInfo);
        startActivity(i);
    }


}