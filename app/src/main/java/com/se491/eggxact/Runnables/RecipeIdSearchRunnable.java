package com.se491.eggxact.Runnables;

import android.net.Uri;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.se491.eggxact.AdvSearchActivity;
import com.se491.eggxact.LandingPageActivity;
import com.se491.eggxact.MainActivity;
import com.se491.eggxact.structure.RecipeHolderLookup;
import com.se491.eggxact.structure.RecipeInfo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RecipeIdSearchRunnable implements Runnable {

    private static final String TAG = "RecipeIdSearchRunnable";
    private static final String API_KEY = "8694c31524msh9489d792de20f42p137d32jsn7a3cd585ce55"; // use your own
//    private static final String API_KEY = "217a7dc8ecmsh533b2d067f06a22p19bd78jsn3ace3adb1dc0"; // Korey Key
    private static final String HOST = "spoonacular-recipe-food-nutrition-v1.p.rapidapi.com";
    private static final String URL_PART1 = "https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com/recipes/"; // id goes after p1 then p2
    private static final String URL_PART2 = "/information";
    private String queryID;
    private AdvSearchActivity advSearchActivity =null;
    private LandingPageActivity landingPageActivity;

    public RecipeIdSearchRunnable(String queryID, LandingPageActivity landingPageActivity) {
        this.queryID = queryID;
        this.landingPageActivity = landingPageActivity;

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
    }

    public RecipeIdSearchRunnable(String queryID) {
        this.queryID = queryID;
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
            recipeInfo.setName(jsonObject.getString("title"));
           // Log.d(TAG, "processData: Mins "+jsonObject.getString("readyInMinutes"));
            recipeInfo.setReadyMinutes(jsonObject.getInt("readyInMinutes"));
            // Log.d(TAG, "processData: Cooking MiNUTES "+jsonObject.getString("cookingMinutes"));
            recipeInfo.setCookingTime(jsonObject.getInt("cookingMinutes"));

            recipeInfo.setPrepTime(jsonObject.getInt("preparationMinutes"));
            recipeInfo.setHealthScore(jsonObject.getDouble("healthScore"));
          // Log.d(TAG, "processData: Img "+jsonObject.getString("image"));
            recipeInfo.setImgURL(jsonObject.getString("image"));
           // Log.d(TAG, "processData: Instructions "+jsonObject.getString("instructions"));
            recipeInfo.setInstructions(jsonObject.getString("instructions"));
            JSONArray jsonArray = jsonObject.getJSONArray("extendedIngredients");
            //Log.d(TAG, "processData: "+jsonArray.length());

            lookup.addtoRecipeHolderTable(recipeInfo.getName(), queryID);

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
        }
        catch (Exception e){
            Log.d(TAG, "run: "+e.toString());
        }
    }


}
