package com.se491.eggxact.ui.landingpage;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.se491.eggxact.AdvSearchActivity;
import com.se491.eggxact.LandingPageActivity;
import com.se491.eggxact.R;
import com.se491.eggxact.Runnables.RandomRecipeRunnable;
import com.se491.eggxact.dbutil.RandomGenerator;
import com.se491.eggxact.structure.IngredientsAdapter;
import com.se491.eggxact.structure.RecipeInfo;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class RandomFragment extends Fragment {

    private static final String TAG = "RandomFragment";
    private static RecipeInfo randomRecipe;
   // private SwipeRefreshLayout swiper;
    private RandomAdapter randomAdapter;
    private ImageView randomImage;
    TextView titleView;
    TextView prepTimeView;
    TextView cookTimeView;
    TextView totalTimeView;
    TextView instructionsView;
    RecyclerView ingredientsView;

    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Favorites");
    private String id = user.getUid();

    private FirebaseUser userP;



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
        titleView = fragmentView.findViewById(R.id.randomTitle);
        prepTimeView = fragmentView.findViewById(R.id.randomPrepTime);
        cookTimeView = fragmentView.findViewById(R.id.randomCookTime);
        totalTimeView = fragmentView.findViewById(R.id.randomTotalTime);
        instructionsView = fragmentView.findViewById(R.id.randomInstructions);
        ingredientsView = fragmentView.findViewById(R.id.randomIngredientsRecycler);
        randomImage = fragmentView.findViewById(R.id.randomImg);
        //notFav = fragmentView.findViewById(R.id.imgbtn_notfav_fragrand);
      //  swiper = fragmentView.findViewById(R.id.swipe);
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
        randomRecipe = RandomGenerator.getRandomRecipe();
       // Log.d(TAG, "onResume: "+randomRecipe.toString());
        if(randomRecipe != null)
            showData();
    }



    private void showData() {
        Log.d(TAG, "showData: ");
        titleView.setText(randomRecipe.getName());
        prepTimeView.setText("Prep Time: " + randomRecipe.getPrepTime() + " mins");
        cookTimeView.setText("Cook Time: " + randomRecipe.getCookingTime() + " mins");
        totalTimeView.setText("Total Time: "+ randomRecipe.getReadyMinutes() + " mins");
        //IngredientsAdapter ingredientsAdapter = new IngredientsAdapter(this.getActivity(), randomRecipe.getIngredients());
        //ingredientsView.setAdapter(ingredientsAdapter);
        randomAdapter = new RandomAdapter(randomRecipe.getIngredients());
        ingredientsView.setAdapter(randomAdapter);
        ingredientsView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
         /* used regex to remove html tags for some reason when
         you get randoms through the API it adds html tags so this is used to remove them, will be update on Firebase later ** TODO REMOVE HTML TAGS FROM FIREBASE */
        instructionsView.setText(randomRecipe.getInstructions().replaceAll("\\<.*?>",""));
        instructionsView.setMovementMethod(new ScrollingMovementMethod());

        Picasso.get().load(randomRecipe.getImgURL()).error(R.drawable.brokenimage)
                .placeholder(R.drawable.placeholder).into(randomImage);

        //((LandingPageActivity)getActivity()).hideBar();
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



    public void passData() {

    }

    /*
    @Override
    public void onRefresh() {
        swiper.setRefreshing(true);
        regenarateRandom();
    }

    private void regenarateRandom() {
        randomRecipe = RandomGenerator.getRandomRecipe();
        showData();
        swiper.setRefreshing(false);
    }*/
}