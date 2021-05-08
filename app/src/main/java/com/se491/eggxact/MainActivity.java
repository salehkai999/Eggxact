package com.se491.eggxact;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    EditText nameTxt;
    TextView dataTxt;
    Button testBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nameTxt = findViewById(R.id.nameTxt);
        testBtn = findViewById(R.id.testBtn);
        dataTxt = findViewById(R.id.dataTxt);
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

    public void searchId(View v){
        new Thread(new RecipeIdSearchRunnable("156992",this)).start(); // Using static data now will be modified later "just as proof of concept"
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

