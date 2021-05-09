package com.se491.eggxact.Runnables;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.se491.eggxact.AdvSearchActivity;
import com.se491.eggxact.MainActivity;
import com.se491.eggxact.structure.Recipe;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class RecipeSearchRunnable implements Runnable {

    private static final String TAG = "RecipeSearchRunnable";
    private static final String API_KEY = "8694c31524msh9489d792de20f42p137d32jsn7a3cd585ce55";
    private static final String HOST = "spoonacular-recipe-food-nutrition-v1.p.rapidapi.com";
    private static final String URL = "https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com/recipes/search";
    private static final ArrayList<Recipe> RECIPE_ARRAY_LIST = new ArrayList<>();
    private AdvSearchActivity advSearchActivity;
    private String query;

    public RecipeSearchRunnable(AdvSearchActivity advSearchActivity, String query) {
        this.advSearchActivity = advSearchActivity;
        this.query = query;
    }


    @Override
    public void run() {

        Uri.Builder builder = Uri.parse(URL).buildUpon();
        builder.appendQueryParameter("query",query);
        builder.appendQueryParameter("number","10");
        builder.appendQueryParameter("offset","0");
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
        }

    }

    private void processData(String data) {
        try {
            RECIPE_ARRAY_LIST.clear();
            JSONObject jsonObject = new JSONObject(data);
            JSONArray jsonArray = jsonObject.getJSONArray("results");
            for(int i=0;i<jsonArray.length();i++){
                jsonObject = jsonArray.getJSONObject(i);
                Recipe recipe = new Recipe(jsonObject.getString("id"),jsonObject.getString("title"));
                Log.d(TAG, "processData: ID "+jsonObject.getString("id"));
                Log.d(TAG, "processData: Title "+jsonObject.getString("title"));
                Log.d(TAG, "processData: readyInMinutes "+jsonObject.getInt("readyInMinutes"));
                Log.d(TAG, "processData: servings "+jsonObject.getInt("servings"));
                Log.d(TAG, "processData: sourceUrl "+jsonObject.getString("sourceUrl"));
                RECIPE_ARRAY_LIST.add(recipe);
            }
            advSearchActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    advSearchActivity.showData(RECIPE_ARRAY_LIST);
                }
            });
        }
        catch (Exception e){
            Log.d(TAG, "run: "+e.toString());
        }
    }

}