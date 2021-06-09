package com.se491.eggxact.ui.filtering;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.se491.eggxact.R;
import com.se491.eggxact.Runnables.IngredientsSearchRunnable;
import com.se491.eggxact.structure.Recipe;

import java.util.ArrayList;

public class FiltersActivity extends AppCompatActivity {

    private LinearLayout itemsLayout;
    private final ArrayList<String> filterItems = new ArrayList<>();
    private static final String TAG = "FiltersActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filters);
        itemsLayout = findViewById(R.id.filter_list);
        this.setTitle("Filter Search");
    }


    public void addItem(View v){
        final View filterItem = getLayoutInflater().inflate(R.layout.ingredient_add_layout,null,false);
        EditText ingredientTxt = filterItem.findViewById(R.id.filterItem);
        ImageView removeImg = filterItem.findViewById(R.id.filter_remove_img);
        removeImg.setOnClickListener(filterView -> {
            removeView(filterItem);
        });
        itemsLayout.addView(filterItem);
        this.setTitle("Ingredients ("+itemsLayout.getChildCount()+")");
    }

    private void removeView(View filterView) {
        itemsLayout.removeView(filterView);
        this.setTitle("Ingredients ("+itemsLayout.getChildCount()+")");
    }

    public void search(View v){

        //StringBuilder query= new StringBuilder();
        if(itemsLayout.getChildCount() == 0){
            Toast.makeText(this, "Please Add items!!", Toast.LENGTH_SHORT).show();
        }
        else {
            //Toast.makeText(this, String.valueOf(itemsLayout.getChildCount()), Toast.LENGTH_LONG).show();
            this.setTitle("Searching...");
            Log.d(TAG, "search: "+itemsLayout.getChildCount());
            filterItems.clear();
            for(int i=0;i<itemsLayout.getChildCount();i++)
            {
                View childItem = itemsLayout.getChildAt(i);
                EditText itemTxt = childItem.findViewById(R.id.filterItem);
                if(!itemTxt.getText().toString().trim().isEmpty()){
                    Log.d(TAG, "search: "+itemTxt.getText().toString()+" : Child At "+i);
                    filterItems.add(itemTxt.getText().toString().trim());
                }
                else {
                    itemTxt.setError("Cannot be empty!");
                    return;
                }
            }
            Log.d(TAG, "search: "+filterItems.toString());
            String query = filterItems.toString().replaceAll("\\[","")
                    .replaceAll("\\]","").replaceAll("\\s+","");
            Log.d(TAG, "search: "+query);
            new Thread(new IngredientsSearchRunnable(this,query)).start();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
        this.setTitle("Filter Search");
    }

    public void passData(ArrayList<Recipe> recipeArrayList) {
        Intent i = new Intent(this,FilterResults.class);
        i.putExtra(Recipe.class.getName(),recipeArrayList);
        startActivity(i);
    }
}