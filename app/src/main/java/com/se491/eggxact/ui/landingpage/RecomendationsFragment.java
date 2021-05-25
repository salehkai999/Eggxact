package com.se491.eggxact.ui.landingpage;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.se491.eggxact.R;
import com.se491.eggxact.dbutil.CategoriesHelper;
import com.se491.eggxact.structure.Category;
import com.se491.eggxact.ui.recommendation.RecommendationActivity;

import java.util.ArrayList;

public class RecomendationsFragment extends Fragment implements View.OnClickListener, CuisinesAdapter.CuisinesOnClick {

    private static final String TAG = "RecomendationsFragment";
    RecyclerView recyclerView;
    RecommendationAdapter recommendationAdapter;
    ArrayList<Category> recommendationList = new ArrayList<>();


    public RecomendationsFragment() {
        // Required empty public constructor
    }


    public static RecomendationsFragment newInstance() {
        RecomendationsFragment fragment = new RecomendationsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_recommendation, container, false);
        recommendationList = CategoriesHelper.getCategories();
        recyclerView = fragmentView.findViewById(R.id.recFragRecycler);
        recommendationAdapter = new RecommendationAdapter(recommendationList,this);
        
        recyclerView.setLayoutManager(new LinearLayoutManager(fragmentView.getContext()));
        recyclerView.setAdapter(recommendationAdapter);
        recommendationAdapter.notifyDataSetChanged();
        return fragmentView;
    }

    @Override
    public void onClick(View v) {
        int pos = recyclerView.getChildAdapterPosition(v);
        Category c = recommendationList.get(pos);
        Log.d(TAG, "onClick: "+c.toString());
        Toast.makeText(getContext(), c.getName(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(), RecommendationActivity.class);
        intent.putExtra(Category.class.getName(),c);
        startActivity(intent);
    }

    @Override
    public void onCuisineClick(Category category) {
        Toast.makeText(getContext(), category.getName(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(), RecommendationActivity.class);
        intent.putExtra(Category.class.getName(),category);
        startActivity(intent);
    }
}