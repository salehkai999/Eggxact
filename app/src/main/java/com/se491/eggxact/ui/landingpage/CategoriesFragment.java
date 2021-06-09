package com.se491.eggxact.ui.landingpage;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.se491.eggxact.R;
import com.se491.eggxact.dbutil.CategoriesHelper;
import com.se491.eggxact.dbutil.CuisinesHelper;
import com.se491.eggxact.structure.Category;
import com.se491.eggxact.structure.Recipe;
import com.se491.eggxact.ui.categoryact.CategoryActivity;

import java.util.ArrayList;
import java.util.Arrays;

public class CategoriesFragment extends Fragment implements View.OnClickListener,CuisinesAdapter.CuisinesOnClick {

    private static final String TAG = "CategoriesFragment";
    RecyclerView recyclerView;
    RecyclerView recyclerViewTwo;
    CatAdapter catAdapterR1;
    CuisinesAdapter catAdapterR2;
    static ArrayList<Category> catList = new ArrayList<>();
    static ArrayList<Category> catList2 = new ArrayList<>();


    public CategoriesFragment() {
        // Required empty public constructor
    }


    public static CategoriesFragment newInstance() {
        CategoriesFragment fragment = new CategoriesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_categories, container, false);
        //catList.clear();
        //catList2.clear();
        catList = CategoriesHelper.getCategories();
        catList2 = CuisinesHelper.getCategories();
        recyclerView = fragmentView.findViewById(R.id.catFragRecycler);
        recyclerViewTwo = fragmentView.findViewById(R.id.catFragRecycler2);
        catAdapterR1 = new CatAdapter(catList,this);
        catAdapterR2 = new CuisinesAdapter(catList2,this);
        catAdapterR2.setCuisinesOnClick(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(fragmentView.getContext()));
        recyclerView.setAdapter(catAdapterR1);
        recyclerViewTwo.setLayoutManager(new LinearLayoutManager(fragmentView.getContext()));
        recyclerViewTwo.setAdapter(catAdapterR2);
        catAdapterR1.notifyDataSetChanged();
        catAdapterR2.notifyDataSetChanged();
        return fragmentView;
    }

    @Override
    public void onClick(View v) {
        int pos = recyclerView.getChildAdapterPosition(v);
        Category c = catList.get(pos);
        Log.d(TAG, "onClick: "+c.toString());
        Toast.makeText(getContext(), c.getName(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(), CategoryActivity.class);
        intent.putExtra(Category.class.getName(),c);
        startActivity(intent);
    }

    @Override
    public void onCuisineClick(Category category) {
        Toast.makeText(getContext(), category.getName(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(), CategoryActivity.class);
        intent.putExtra(Category.class.getName(),category);
        startActivity(intent);
    }
}