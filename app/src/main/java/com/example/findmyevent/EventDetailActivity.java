package com.example.findmyevent;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class EventDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        // Retrieve the event details
        String eventName = getIntent().getStringExtra("EVENT_NAME");
        String eventId = getIntent().getStringExtra("EVENT_ID");
        String eventUrl = getIntent().getStringExtra("EVENT_URL");

        // Find views by ID and set the event details
        TextView eventNameTextView = findViewById(R.id.event_detail_name);
        eventNameTextView.setText(eventName);
        // Set other details as well
    }
}
