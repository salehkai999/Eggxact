package com.se491.eggxact.ui.comment;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.se491.eggxact.R;

import java.util.ArrayList;

public class CommentRecyclerActivity extends AppCompatActivity implements View.OnClickListener {
        private static final String TAG = "CommentRecyclerActivity";
        private static final ArrayList<String> comments = new ArrayList<>();
        Button submitButton;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
                Log.d(TAG, "onCreate: FOUND");
                RecyclerView myRecycler = findViewById(R.id.my_recycler_view);
                submitButton = findViewById(R.id.button);

                comments.add("Please add your review of the recipe here!");

                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
                myRecycler.setLayoutManager(layoutManager);

                CommentRecyclerAdapter adapter = new CommentRecyclerAdapter(comments);
                myRecycler.setAdapter(adapter);

                submitButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                EditText commentSection = findViewById(R.id.commenttext);
                                if(commentSection.getText() != null) {
                                        adapter.addItem(commentSection.getText().toString());
                                }
                        }
                });



        }

        @Override
        public void onClick(View v) {
        }

    }
