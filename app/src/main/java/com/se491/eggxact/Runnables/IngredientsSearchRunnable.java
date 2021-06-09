package com.se491.eggxact.Runnables;

import android.net.Uri;
import android.util.Log;

import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.se491.eggxact.R;
import com.se491.eggxact.structure.Recipe;
import com.se491.eggxact.ui.filtering.FiltersActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class IngredientsSearchRunnable implements Runnable {

    private static final String TAG = "FilterIngSearchRunnable";
    private static final String URL = "https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com/recipes/findByIngredients";
    private static final String HOST = "spoonacular-recipe-food-nutrition-v1.p.rapidapi.com";
    private static final ArrayList<Recipe> RECIPE_ARRAY_LIST = new ArrayList<>();
    private String API_KEY;
    private FiltersActivity filtersActivity;
    private String query;

    public IngredientsSearchRunnable(FiltersActivity filtersActivity, String query) {
        this.filtersActivity = filtersActivity;
        this.query = query;
        this.API_KEY = this.filtersActivity.getString(R.string.API_KEY1);
    }

    @Override
    public void run() {
        Uri.Builder builder = Uri.parse(URL).buildUpon();
        builder.appendQueryParameter("ingredients",query);
        builder.appendQueryParameter("number","5");
        builder.appendQueryParameter("ignorePantry","true");
        builder.appendQueryParameter("ranking","1");
        String urlStr = builder.toString();
        Log.d(TAG, "run: "+urlStr);
        StringBuilder strBuilder = new StringBuilder();
        try{
            java.net.URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("x-rapidapi-key",API_KEY);
            connection.setRequestProperty("x-rapidapi-host",HOST);
            connection.connect();
            if(connection.getResponseCode() != HttpURLConnection.HTTP_OK)
            {
                StringBuilder result = new StringBuilder();
                BufferedReader reader = null;
                Log.d(TAG, "run: "+connection.getResponseCode());
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                String line;
                while (null != (line = reader.readLine())) {
                    result.append(line).append("\n");
                }
                Log.d(TAG, "run: "+result.toString());
                FirebaseCrashlytics.getInstance().log(result.toString());
                return;
            }
            InputStream inputStream = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String data;
            while ((data = bufferedReader.readLine()) != null) {
                strBuilder.append(data).append("\n");
            }
            Log.d(TAG, "run: "+strBuilder.toString());
            processData(strBuilder.toString());
        }
        catch (Exception e){
            Log.d(TAG, "run: "+e.toString());
            FirebaseCrashlytics.getInstance().log(TAG+"run");
            FirebaseCrashlytics.getInstance().recordException(e);
        }

        
    }

    private void processData(String data) {
        RECIPE_ARRAY_LIST.clear();
        try {
            JSONArray jsonArray = new JSONArray(data);

            for(int i=0;i<jsonArray.length();i++) {
                Recipe recipe = new Recipe();
                recipe.setRecipeId(jsonArray.getJSONObject(i).getString("id"));
                recipe.setRecipeName(jsonArray.getJSONObject(i).getString("title"));
                if(jsonArray.getJSONObject(i).has("image"))
                    recipe.setSourceUrl(jsonArray.getJSONObject(i).getString("image"));
                RECIPE_ARRAY_LIST.add(recipe);
            }
            Log.d(TAG, "processData: "+RECIPE_ARRAY_LIST.toString());
            filtersActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    filtersActivity.passData(RECIPE_ARRAY_LIST);
                }
            });
        }
        catch (Exception e){
            Log.d(TAG, "processData: "+e.toString());
            FirebaseCrashlytics.getInstance().log(TAG+"processData");
            FirebaseCrashlytics.getInstance().recordException(e);
        }
    }
}
