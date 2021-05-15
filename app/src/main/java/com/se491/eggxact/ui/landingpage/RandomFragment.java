package com.se491.eggxact.ui.landingpage;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ThemedSpinnerAdapter;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.se491.eggxact.AdvSearchActivity;
import com.se491.eggxact.R;
import com.se491.eggxact.Runnables.RandomRecipeRunnable;
import com.se491.eggxact.structure.RecipeInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;


public class RandomFragment extends Fragment {

    private static final String TAG = "RandomFragment";
    Button generate;
    private final String URL = "https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com/recipes/random?number=1";
    private static final String API_KEY = "8694c31524msh9489d792de20f42p137d32jsn7a3cd585ce55";
    private static final String HOST = "spoonacular-recipe-food-nutrition-v1.p.rapidapi.com";
    //public static RecipeInfo recipeInfo = new RecipeInfo();

    public RandomFragment() {
        // Required empty public constructor
    }


    public static RandomFragment newInstance() {
        RandomFragment fragment = new RandomFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_random, container, false);
      //  generate = fragmentView.findViewById(R.id.generate);
      //  RandomFragment fragment = this;

        return fragmentView;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
        //new Thread(new RandomRecipeRunnable(this));
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");


    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    public void random(View v){
        Log.d(TAG, "random: ");
    }



    public void passData(JSONObject jsonObject) {
        try{
            RecipeInfo recipeInfo = new RecipeInfo();
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
            //Log.d(TAG, "processData: "+jsonArray.length());


            //lookup.addtoRecipeHolderTable(recipeInfo.getName(), queryID);

            if(jsonArray != null) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jObj = jsonArray.getJSONObject(i);
                    Log.d(TAG, "processData: " + jObj.toString());
                    recipeInfo.addIngredient(jObj.getString("originalString"));
                    // Log.d(TAG, "processData: "+jObj.toString());
                }
            }
            Log.d(TAG, "passData: "+recipeInfo.toString());
        }
        catch (Exception e){
        Log.d(TAG, "run: "+e.toString());
        }
    }
}