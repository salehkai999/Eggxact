package com.se491.eggxact;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.se491.eggxact.Runnables.RecipeSearchRunnable;
import com.se491.eggxact.structure.Recipe;
import com.se491.eggxact.structure.RecipeAdapter;

import java.util.ArrayList;

public class AdvSearchActivity extends AppCompatActivity {

    private static final String TAG = "AdvSearchActivity";
    private static final ArrayList<Recipe> recipeList = new ArrayList<>();
    RecyclerView recyclerView;
    RecipeAdapter recipeAdapter;
    EditText searchByName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adv_search);
        searchByName = findViewById(R.id.searchNameTxt);
        recyclerView = findViewById(R.id.recycler);
        recipeAdapter = new RecipeAdapter(recipeList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(recipeAdapter);

        searchByName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    Log.i(TAG,"Enter pressed");
                    Log.d(TAG, "onEditorAction: "+getBaseContext().toString());
                    if(searchByName.getText().toString().trim().isEmpty()){
                        searchByName.setError("Can't be empty!!");
                    }else {
                        doSearch(searchByName.getText().toString().trim());
                        searchByName.setText("");
                    }
                }
                return false;
            }
        });

    }

    private void doSearch(String query) {
        new Thread(new RecipeSearchRunnable(this,query)).start();
    }

    public void showData(ArrayList<Recipe> recipeArrayList) {
        recipeList.clear();
        recipeList.addAll(recipeArrayList);
        recipeAdapter.notifyDataSetChanged();
        Log.d(TAG, "showData: "+recipeList.size());
    }
}