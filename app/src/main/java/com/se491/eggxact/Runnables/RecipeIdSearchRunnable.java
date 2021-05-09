package com.se491.eggxact.Runnables;

import android.net.Uri;
import android.util.Log;

import com.se491.eggxact.AdvSearchActivity;
import com.se491.eggxact.MainActivity;

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
    private static final String HOST = "spoonacular-recipe-food-nutrition-v1.p.rapidapi.com";
    private static final String URL_PART1 = "https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com/recipes/"; // id goes after p1 then p2
    private static final String URL_PART2 = "/information";
    private String queryID;
    private MainActivity mainActivity;
    private AdvSearchActivity advSearchActivity;

    public RecipeIdSearchRunnable(String queryID, MainActivity mainActivity) {
        this.queryID = queryID;
        this.mainActivity = mainActivity;
    }

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
            JSONObject jsonObject = new JSONObject(data);
            Log.d(TAG, "processData: Title "+jsonObject.getString("title"));
            Log.d(TAG, "processData: Mins "+jsonObject.getString("readyInMinutes"));
            Log.d(TAG, "processData: Img "+jsonObject.getString("image"));
            Log.d(TAG, "processData: Instructions "+jsonObject.getString("instructions"));
            JSONArray jsonArray = jsonObject.getJSONArray("extendedIngredients");
            Log.d(TAG, "processData: "+jsonArray.length());
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jObj = jsonArray.getJSONObject(i);
                Log.d(TAG, "processData: "+jObj.toString());
            }

        }
        catch (Exception e){
            Log.d(TAG, "run: "+e.toString());
        }
    }


}
