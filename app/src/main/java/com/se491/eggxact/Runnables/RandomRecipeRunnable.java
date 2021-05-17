package com.se491.eggxact.Runnables;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.se491.eggxact.LandingPageActivity;
import com.se491.eggxact.structure.RecipeInfo;
import com.se491.eggxact.ui.landingpage.RandomFragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class RandomRecipeRunnable implements  Runnable {

    /*
    *
    *  USED ONLY TO ADD DATA TO FIREBASE
    *
     */
    private static final String TAG = "RandomRecipeRunnable";
    private static final ArrayList<RecipeInfo> RECIPE_INFOS = new ArrayList<>();
    private final String URL = "https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com/recipes/random?number=20";
    private DatabaseReference databaseReference;
   // private static final String API_KEY = "8694c31524msh9489d792de20f42p137d32jsn7a3cd585ce55";
   // private static final String API_KEY = "8db465b75emshe24da93e91b0cfep19f013jsn7627f77a15ae";
    private static final String API_KEY = "";
    private static final String HOST = "spoonacular-recipe-food-nutrition-v1.p.rapidapi.com";

    public RandomRecipeRunnable() {
    }


    @Override
    public void run() {
        Uri.Builder builder = Uri.parse(URL).buildUpon();
        String urlString = builder.toString();
        Log.d(TAG, "run: "+urlString);
        StringBuilder strBuilder = new StringBuilder();
        try{
            java.net.URL url = new URL(urlString);
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
                //  Log.d(TAG, "run: "+result.toString());
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

            JSONObject jsonObjectDummy = new JSONObject(data);
            JSONArray jArray = jsonObjectDummy.getJSONArray("recipes");
            for(int i=0;i<jArray.length();i++) {
                JSONObject jsonObject = jArray.getJSONObject(i);
                RecipeInfo recipeInfo = new RecipeInfo();
                // Log.d(TAG, "processData: Title "+jsonObject.getString("title"));
                if(jsonObject.has("title"))
                    recipeInfo.setName(jsonObject.getString("title"));
                // Log.d(TAG, "processData: Mins "+jsonObject.getString("readyInMinutes"));
                if(jsonObject.has("readyInMinutes"))
                    recipeInfo.setReadyMinutes(jsonObject.getInt("readyInMinutes"));
                // Log.d(TAG, "processData: Cooking MiNUTES "+jsonObject.getString("cookingMinutes"));
                if(jsonObject.has("cookingMinutes"))
                    recipeInfo.setCookingTime(jsonObject.getInt("cookingMinutes"));
                if(jsonObject.has("preparationMinutes"))
                    recipeInfo.setPrepTime(jsonObject.getInt("preparationMinutes"));
                if(jsonObject.has("healthScore"))
                    recipeInfo.setHealthScore(jsonObject.getDouble("healthScore"));
                // Log.d(TAG, "processData: Img "+jsonObject.getString("image"));
                if(jsonObject.has("image"))
                    recipeInfo.setImgURL(jsonObject.getString("image"));
                // Log.d(TAG, "processData: Instructions "+jsonObject.getString("instructions"));
                if(jsonObject.has("instructions"))
                    recipeInfo.setInstructions(jsonObject.getString("instructions"));
                JSONArray jsonArray = null;
                if(jsonObject.has("extendedIngredients"))
                     jsonArray = jsonObject.getJSONArray("extendedIngredients");

                if(jsonArray != null) {
                    for (int j = 0; j < jsonArray.length(); j++) {
                        JSONObject jObj = jsonArray.getJSONObject(j);
                        Log.d(TAG, "processData: " + jObj.toString());
                        recipeInfo.addIngredient(jObj.getString("originalString"));
                        // Log.d(TAG, "processData: "+jObj.toString());
                    }
                }
                RECIPE_INFOS.add(recipeInfo);
                Log.d(TAG, "processData: "+recipeInfo.toString());
            }

            databaseReference = FirebaseDatabase.getInstance().getReference().child("Random");
            for(RecipeInfo recipeInfo : RECIPE_INFOS){
                databaseReference.push().setValue(recipeInfo);
            }

        }
        catch (Exception e){
            Log.d(TAG, "run: "+e.toString());
        }
    }



}
