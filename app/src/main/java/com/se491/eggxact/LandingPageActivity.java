package com.se491.eggxact;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.se491.eggxact.Runnables.RecipeIdSearchRunnable;
import com.se491.eggxact.structure.RecipeHolderLookup;
import com.se491.eggxact.ui.landingpage.FragmentAdapter;

public class LandingPageActivity extends AppCompatActivity {



    private static final String TAG = "LandingPageActivity";
    EditText nameTxt;
    TextView dataTxt;
    Button testBtn;

    EditText enterRecipeName;
    Button searchIdButton;
    Button AddRecipeButton;
    DatabaseReference recipeHolderDatabase;
    ListView listViewofRecipes;
    RecipeHolderLookup lookup;

    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.pager);
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        TabLayout.Tab t1 = tabLayout.getTabAt(0);
        if (t1 != null) {
            t1.setIcon(R.drawable.baseline_home_24);
        }
        TabLayout.Tab t2 = tabLayout.getTabAt(1);
        if (t2 != null) {
            t2.setIcon(R.drawable.baseline_article_24);
        }
        TabLayout.Tab t3 = tabLayout.getTabAt(2);
        if (t3 != null) {
            t3.setIcon(R.drawable.outline_shuffle_24);
        }

        TabLayout.Tab t4 = tabLayout.getTabAt(3);
        if (t4 != null) {
            t4.setIcon(R.drawable.baseline_favorite_24);
        }

        TabLayout.Tab t5 = tabLayout.getTabAt(4);
        if(t5 != null){
            t5.setIcon(R.drawable.outline_account_circle_24);
        }




        /*
        nameTxt = findViewById(R.id.nameTxt);
        testBtn = findViewById(R.id.testBtn);
        AddRecipeButton = findViewById(R.id.addRecipeId);
        dataTxt = findViewById(R.id.dataTxt);
        listViewofRecipes = (ListView) findViewById(R.id.ListViewRecipes);
        recipeHolderDatabase = FirebaseDatabase.getInstance().getReference("recipeHolder");
        lookup = new RecipeHolderLookup(recipeHolderDatabase);

        enterRecipeName = findViewById(R.id.nameTxt);
        searchIdButton = findViewById(R.id.searchId);

        AddRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addRecipeById();
            }
        });


        searchIdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = enterRecipeName.getText().toString().trim();
                lookup.selectFromRecipeHolder(name, LandingPageActivity.this, listViewofRecipes);
            }
        });


        /* another way to implement buttons is .setOnClickListener or via the layout designer.
         * Keep in mind the method should be like this methodName(View v){ } when using the designer.
         * */
        /*
        testBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseTestCall();
            }
        }); */
    }

    private void addRecipeById() {
       // lookup.addtoRecipeHolderTable(enterRecipeName);
    }

    public void searchId(View v){
        new Thread(new RecipeIdSearchRunnable("156992",this)).start(); // Using static data now will be modified later "just as proof of concept"
    }

    public void nameSearch(View v){
        Intent searchIntent = new Intent(this,AdvSearchActivity.class);
        startActivity(searchIntent);
    }

    private void firebaseTestCall(){
        FirebaseDatabase db = FirebaseDatabase.getInstance(); // getting a firebase instance.
        DatabaseReference dbRef = db.getReference("Tests"); // reference to the db in this case called test
        /*
         * We aren't yet adding children so the db only holds 1 value for now.
         * */
        dbRef.setValue(nameTxt.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override // onCompleteListeners are optional but can be helpful
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(LandingPageActivity.this, "SAVED To Firebase!!", Toast.LENGTH_SHORT).show();
            }
        }); // saving values to the db
        nameTxt.setText("");
        // reading from the DB!
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) { // basically firebase has a realtime db which means whenever the data changes this gets triggered.!
                String value = dataSnapshot.getValue(String.class);
                dataTxt.setText(value);
                Log.d(TAG, "Data: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failure: ", error.toException());
            }
        });

    }


}