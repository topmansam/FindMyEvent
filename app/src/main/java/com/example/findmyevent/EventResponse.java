package com.example.findmyevent;


import java.util.List;

public class EventResponse {
    private Embedded _embedded;  // This field must match the key in the JSON response

    public List<Event> getEvents() {
        if (_embedded != null) {
            return _embedded.events;
        }
        return null;  // Return null or an empty list if there's no embedded events
    }

    public void setEmbedded(Embedded embedded) {
        this._embedded = embedded;
    }

    // Nested class to match the "_embedded" structure of the JSON
    public static class Embedded {
        private List<Event> events;

        public List<Event> getEvents() {
            return events;
        }

        public void setEvents(List<Event> events) {
            this.events = events;
        }
    }
}
