package com.example.findmyevent;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;

public class InterestsSelectionActivity extends Activity {
    RecyclerView recyclerView;

    // Using ArrayList to store images data
    ArrayList interestImg = new ArrayList<>(Arrays.asList(R.drawable.swim_logo,R.drawable.hike_logo,R.drawable.film_logo));
    ArrayList interestName = new ArrayList<>(Arrays.asList("swim","hike","film"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interests_selection);

        // Getting reference of recyclerView
        recyclerView = (RecyclerView) findViewById(R.id.list_interests_selection);

        // Setting the layout as linear
        // layout for vertical orientation
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        // Sending reference and data to Adapter
        ListInterestsSelectionAdapter adapter = new ListInterestsSelectionAdapter(InterestsSelectionActivity.this, interestImg, interestName);

        // Setting Adapter to RecyclerView
        recyclerView.setAdapter(adapter);

        // Control confirm button
        Button confirmBtn = findViewById(R.id.interestsSelectButton);
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText customTxt = findViewById(R.id.editText_custom);
                String intrst = customTxt.getText().toString();     // Gets String written from user
                interestName.add(intrst);                           // Add String to interst name array
                interestImg.add(R.drawable.swim_logo);              // Add Some logo, need to add stock logo for custom text
                // Sending reference and data to Adapter
                ListInterestsSelectionAdapter adapter = new ListInterestsSelectionAdapter(InterestsSelectionActivity.this, interestImg, interestName);
                // Setting Adapter to RecyclerView
                recyclerView.setAdapter(adapter);
            }
        });
    }
}