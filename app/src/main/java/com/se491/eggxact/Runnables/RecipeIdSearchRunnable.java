package com.se491.eggxact.Runnables;

import android.net.Uri;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.se491.eggxact.AdvSearchActivity;
import com.se491.eggxact.LandingPageActivity;
import com.se491.eggxact.MainActivity;
import com.se491.eggxact.R;
import com.se491.eggxact.RecipeActivity;
import com.se491.eggxact.structure.Category;
import com.se491.eggxact.structure.RecipeHolderLookup;
import com.se491.eggxact.structure.RecipeInfo;
import com.se491.eggxact.ui.categoryact.CategoryActivity;
import com.se491.eggxact.ui.ratingsact.RatingsActAdapter;
import com.se491.eggxact.ui.ratingsact.RatingsActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RecipeIdSearchRunnable implements Runnable {

    private static final String TAG = "RecipeIdSearchRunnable";
    private static String API_KEY = "";
    //private static final String API_KEY = "217a7dc8ecmsh533b2d067f06a22p19bd78jsn3ace3adb1dc0"; // Korey Key
    private static final String HOST = "spoonacular-recipe-food-nutrition-v1.p.rapidapi.com";
    private static final String URL_PART1 = "https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com/recipes/"; // id goes after p1 then p2
    private static final String URL_PART2 = "/information";
    private String queryID;
    private AdvSearchActivity advSearchActivity =null;
    private RatingsActivity ratingsActivity;
    private CategoryActivity categoryActivity;
    private RecipeActivity recipeActivity;



    private RecipeHolderLookup lookup;

/*      
    public RecipeIdSearchRunnable(String queryID, MainActivity mainActivity, DatabaseReference reference) {
        this.queryID = queryID;
        this.mainActivity = mainActivity;
        lookup = new RecipeHolderLookup(reference);

    }
*/
    public RecipeIdSearchRunnable(String queryID, AdvSearchActivity advSearchActivity) {
        this.queryID = queryID;
        this.advSearchActivity = advSearchActivity;
        API_KEY = this.advSearchActivity.getString(R.string.API_KEY1);
    }

    public RecipeIdSearchRunnable(String queryID,RatingsActivity ratingsActivity){
        this.queryID = queryID;
        this.ratingsActivity = ratingsActivity;
        API_KEY = this.ratingsActivity.getString(R.string.API_KEY1);
    }



    public RecipeIdSearchRunnable(String queryID) {
        this.queryID = queryID;
    }

    public RecipeIdSearchRunnable(String recipeId, CategoryActivity categoryActivity) {
        this.queryID = recipeId;
        this.categoryActivity = categoryActivity;
        API_KEY = this.categoryActivity.getString(R.string.API_KEY1);
    }

    public RecipeIdSearchRunnable(String recipeId, RecipeActivity recipeActivity) {
        this.queryID = recipeId;
        this.recipeActivity = recipeActivity;
        API_KEY = this.recipeActivity.getString(R.string.API_KEY1);
    }


    @Override
    public void run() {

        Uri.Builder builder = Uri.parse(URL_PART1+queryID+URL_PART2).buildUpon();
        String urlString = builder.toString();
        Log.d(TAG, "run: "+urlString);
        StringBuilder strBuilder = new StringBuilder();
        try{
            URL url = new URL(urlString);
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
            RecipeInfo recipeInfo = new RecipeInfo();
            JSONObject jsonObject = new JSONObject(data);
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
            JSONArray jsonArray = new JSONArray();
            if(jsonObject.has("extendedIngredients"))
             jsonArray = jsonObject.getJSONArray("extendedIngredients");
            //Log.d(TAG, "processData: "+jsonArray.length());


            //lookup.addtoRecipeHolderTable(recipeInfo.getName(), queryID);

            for(int i=0;i<jsonArray.length();i++){
                JSONObject jObj = jsonArray.getJSONObject(i);
                Log.d(TAG, "processData: "+jObj.toString());
                recipeInfo.addIngredient(jObj.getString("originalString"));
               // Log.d(TAG, "processData: "+jObj.toString());
            }

            Log.d(TAG, "processData: "+recipeInfo.toString());
            if(advSearchActivity != null){
                advSearchActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        advSearchActivity.passRecipeObject(recipeInfo);
                    }
                });
            }

            else if(ratingsActivity != null){
                ratingsActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ratingsActivity.passRecipeObject(recipeInfo);
                    }
                });
            }

            else if(categoryActivity != null){
                categoryActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        categoryActivity.passRecipeObject(recipeInfo);
                    }
                });
            }

            else {
                recipeActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        recipeActivity.passRecipeObject(recipeInfo);
                    }
                });
            }
        }
        catch (Exception e){
            Log.d(TAG, "run: "+e.toString());
        }
    }


}
