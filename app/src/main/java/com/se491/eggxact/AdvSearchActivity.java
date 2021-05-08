package com.se491.eggxact;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.se491.eggxact.Runnables.RecipeSearchRunnable;

public class AdvSearchActivity extends AppCompatActivity {

    private static final String TAG = "AdvSearchActivity";
    EditText searchByName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adv_search);
        searchByName = findViewById(R.id.searchNameTxt);

        searchByName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    Log.i(TAG,"Enter pressed");
                    Log.d(TAG, "onEditorAction: "+getBaseContext().toString());
                    if(searchByName.getText().toString().trim().isEmpty()){
                        searchByName.setError("Can't be empty!!");
                    }else {
                        doSearch(searchByName.getText().toString().trim());
                        searchByName.setText("");
                    }
                }
                return false;
            }
        });

    }

    private void doSearch(String query) {
        new Thread(new RecipeSearchRunnable(this,query)).start();
    }
}