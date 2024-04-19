package com.example.findmyevent;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.ArrayList;
import java.util.List;
import java.net.URL;
/*
* List all events using Event Adapter to display in list format.
* */
public class EventListActivity extends AppCompatActivity {

    private RecyclerView eventsRecyclerView;
    private EventAdapter eventAdapter;
    private List<Event> eventList;
    private TextView textViewNoEvents;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        eventsRecyclerView = findViewById(R.id.eventsRecyclerView);
        eventsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        textViewNoEvents = findViewById(R.id.textViewNoEvents); // Ensure this is initialized here

        eventList = new ArrayList<>();
        eventAdapter = new EventAdapter(eventList);
        eventsRecyclerView.setAdapter(eventAdapter);

        // Retrieve the selected interests passed from InterestsSelectionActivity
        String selectedInterests = getIntent().getStringExtra("selectedInterests");
        Log.d("EventListActivity", "Received interests: " + selectedInterests);

        loadEvents(selectedInterests); // Load events from API
    }
    // We use Retrofit by square, an open source library to make API calls simpler and more maintanable. No need for httpurl conenction and deserialization
    // https://square.github.io/retrofit/
    private void loadEvents(String selectedInterests) {
        TicketmasterApi api = RetrofitClient.getClient("https://app.ticketmaster.com/discovery/v2/").create(TicketmasterApi.class);
        Call<EventResponse> call = api.searchEvents("GoeK5VydqGc4q81CAX80AHGURWhnacLW", selectedInterests, "en-us");

        call.enqueue(new Callback<EventResponse>() {
            @Override
            public void onResponse(@NonNull Call<EventResponse> call, @Nullable Response<EventResponse> response) {
                if (response != null && response.isSuccessful() && response.body() != null) {
                    List<Event> events = response.body().getEvents();
                    if (events != null && !events.isEmpty()) {
                        eventList.clear();
                        eventList.addAll(events);
                        eventAdapter.notifyDataSetChanged();
                        textViewNoEvents.setVisibility(View.GONE); // Hide the no events text
                    } else {
                        textViewNoEvents.setVisibility(View.VISIBLE); // Show the no events text
                    }
                } else {
                    textViewNoEvents.setVisibility(View.VISIBLE); // Show the no events text if response not successful
                    Log.e("API Call", "Response not successful: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<EventResponse> call, Throwable t) {
                textViewNoEvents.setVisibility(View.VISIBLE); // Show the no events text on failure
                Log.e("API Failure", "Error loading events: " + t.getMessage());
            }
        });
    }


    private void updateUI(String jsonResponse) {
        Gson gson = new Gson();
        EventResponse eventResponse = gson.fromJson(jsonResponse, EventResponse.class);
        List<Event> events = eventResponse.getEvents();
        if (events != null && !events.isEmpty()) {
            eventList.clear();
            eventList.addAll(events);
            eventAdapter.notifyDataSetChanged();
            Log.d("Update UI", "Events loaded successfully.");
        } else {
            Log.e("Update UI", "No events found.");
        }
    }
}