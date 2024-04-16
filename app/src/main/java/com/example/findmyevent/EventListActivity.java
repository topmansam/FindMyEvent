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

public class EventListActivity extends AppCompatActivity {

    private RecyclerView eventsRecyclerView;
    private EventAdapter eventAdapter;
    private List<Event> eventList; // Assuming Event is a class you have defined to hold event data

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        eventsRecyclerView = findViewById(R.id.eventsRecyclerView);
        eventsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        eventList = new ArrayList<>();
        eventAdapter = new EventAdapter(eventList);
        eventsRecyclerView.setAdapter(eventAdapter);

        loadEvents(); // Load events from API
        //fetchEvents(); // Load events using HttpURLConnection
    }

    private void loadEvents() {
        TicketmasterApi api = RetrofitClient.getClient("https://app.ticketmaster.com/discovery/v2/").create(TicketmasterApi.class);
        //Call<EventResponse> call = api.searchEvents("GoeK5VydqGc4q81CAX80AHGURWhnacLW", "Music", "en-us");
        Call<EventResponse> call = api.searchEvents("GoeK5VydqGc4q81CAX80AHGURWhnacLW");
        call.enqueue(new Callback<EventResponse>() {
            @Override
            public void onResponse(@NonNull Call<EventResponse> call, @Nullable Response<EventResponse> response) {
                if (response != null && response.isSuccessful() && response.body() != null) {
                    List<Event> events = response.body().getEvents();
                    if (events != null && !events.isEmpty()) {
                        eventList.clear();
                        eventList.addAll(events);
                        eventAdapter.notifyDataSetChanged();
                        Log.d("API Call", "Success: Loaded " + events.size() + " events.");
                        Log.d("API Call", "Events updated. List size: " + eventList.size()); // Add this line
                    } else {
                        Log.e("API Call", "No events to load");
                    }
                } else {
                    // Response was not successful; log status code and error body
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
                Log.e("API Failure", t.getMessage());
            }
        });
    }

    private void fetchEvents() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("https://app.ticketmaster.com/discovery/v2/events.json?apikey=GoeK5VydqGc4q81CAX80AHGURWhnacLW&classificationName=Music&locale=en-us");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.connect();

                    int responseCode = conn.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        String inputLine;
                        StringBuilder response = new StringBuilder();
                        while ((inputLine = in.readLine()) != null) {
                            response.append(inputLine);
                        }
                        in.close();
                        // Process the response on the main thread
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                updateUI(response.toString()); // Update your UI with the response
                            }
                        });
                    } else {
                        Log.e("HTTP Error", "HTTP error code: " + responseCode);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("Network Error", "Error fetching events: " + e.getMessage());
                }
            }
        });
        thread.start();
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