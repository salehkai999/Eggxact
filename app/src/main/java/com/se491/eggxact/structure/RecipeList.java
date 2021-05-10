package com.se491.eggxact.structure;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.se491.eggxact.R;

import java.util.List;

public class RecipeList extends ArrayAdapter<Recipe> {

    private Activity context;
    private List<Recipe> recipeList;

    public RecipeList(Activity context,List<Recipe> recipeList) {
        super(context, R.layout.list_of_recipes, recipeList);
        this.context = context;
        this.recipeList = recipeList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewRecipe = inflater.inflate(R.layout.list_of_recipes, null, true);

        TextView textViewRecipeId = listViewRecipe.findViewById(R.id.textViewRecipeId);
        TextView textViewRecipeName = listViewRecipe.findViewById(R.id.textViewRecipeName);

        Recipe recipe =  recipeList.get(position);
        textViewRecipeId.setText(recipe.getRecipeId());
        textViewRecipeName.setText(recipe.getRecipeName());

        return listViewRecipe;
    }
}
