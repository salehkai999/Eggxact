package com.se491.eggxact;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.se491.eggxact.Runnables.RecipeIdSearchRunnable;
import com.se491.eggxact.structure.Recipe;
import com.se491.eggxact.structure.RecipeHolderLookup;
import com.se491.eggxact.structure.RecipeList;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    EditText nameTxt;
    Button testBtn;
    EditText enterRecipeName;
    Button searchIdButton;
    Button AddRecipeButton;
    DatabaseReference recipeHolderDatabase;
    ListView listViewofRecipes;
    RecipeHolderLookup lookup;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nameTxt = findViewById(R.id.nameTxt);
        testBtn = findViewById(R.id.testBtn);
        AddRecipeButton = findViewById(R.id.addRecipeId);
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
                lookup.selectFromRecipeHolder(name, MainActivity.this, listViewofRecipes);
            }
        });


        /* another way to implement buttons is .setOnClickListener or via the layout designer.
        * Keep in mind the method should be like this methodName(View v){ } when using the designer.
        * */
        testBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseTestCall();
            }
        });
    }

    private void addRecipeById() {
        lookup.addtoRecipeHolderTable(enterRecipeName);
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
                Toast.makeText(MainActivity.this, "SAVED To Firebase!!", Toast.LENGTH_SHORT).show();
            }
        }); // saving values to the db
        nameTxt.setText("");
        // reading from the DB!
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) { // basically firebase has a realtime db which means whenever the data changes this gets triggered.!
                String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Data: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failure: ", error.toException());
            }
        });

    }

}

