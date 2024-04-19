package com.example.findmyevent;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;
/*
* Class for when we click an individual event.
* */
public class EventDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        // Retrieve the event URL
        String eventUrl = getIntent().getStringExtra("EVENT_URL");

        // Find the Buy Tickets button by ID
        Button buyTicketsButton = findViewById(R.id.event_detail_buy_tickets_button);

        // Set an OnClickListener to the Buy Tickets button
        buyTicketsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("EventDetailActivity", "Buy Tickets button clicked. URL: " + eventUrl);

                // Open the URL in a web browser
                openWebPage(eventUrl);
            }
        });
    }

    private void openWebPage(String url) {
        // Check if the URL is not null or empty
        if (url != null && !url.isEmpty()) {
            // Parse the URL and create an Intent to open it
            Uri webpage = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, webpage);

            // Verify that the Intent can be handled and start the activity
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            } else {
                // Log an error if no application can handle the web URL Intent
                Log.e("EventDetailActivity", "No application can handle this intent");
            }
        } else {
            // Log an error if the URL is invalid or empty
            Log.e("EventDetailActivity", "URL is invalid or empty");
        }
    }
}