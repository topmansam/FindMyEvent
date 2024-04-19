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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        eventsRecyclerView = findViewById(R.id.eventsRecyclerView);
        eventsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        eventList = new ArrayList<>();
        eventAdapter = new EventAdapter(eventList);
        eventsRecyclerView.setAdapter(eventAdapter);

        // Retrieve the selected interests passed from InterestsSelectionActivity
        String selectedInterests = getIntent().getStringExtra("selectedInterests");
        Log.d("EventListActivity", "Received interests: " + selectedInterests);

        loadEvents(selectedInterests);// Load events from API
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
                        Log.d("API Call", "Success: Loaded " + events.size() + " events. For interests: " + selectedInterests);
                    } else {
                        Log.d("API Call", "No events to load for interests: " + selectedInterests);
                    }
                } else {
                    Log.e("API Call", "Response not successful: " + response.code());
                    try {
                        if (response.errorBody() != null) {
                            String errorBody = response.errorBody().string();
                            Log.e("API Error Body", errorBody);
                        }
                    } catch (IOException e) {
                        Log.e("API Call", "Error reading error body", e);
                    }
                }
            }

            @Override
            public void onFailure(Call<EventResponse> call, Throwable t) {
                Log.e("API Failure", "Error loading events for " + selectedInterests + ": " + t.getMessage());
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