package com.example.findmyevent;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TicketmasterApi {
    // Method with all parameters
    @GET("events.json")
    Call<EventResponse> searchEvents(
            @Query("apikey") String apiKey,
            @Query("classificationName") String classificationName,
            @Query("locale") String locale
    );

    // Overloaded method without the optional parameters
    @GET("events.json")
    Call<EventResponse> searchEvents(
            @Query("apikey") String apiKey
    );

    @GET("events.json")
    Call<EventResponse> searchEvents(
            @Query("apikey") String apiKey,
            @Query("classificationName") String classificationName,
            @Query("locale") String locale,
            @Query("startDateTime") String startDateTime,
            @Query("endDateTime") String endDateTime
    );
}