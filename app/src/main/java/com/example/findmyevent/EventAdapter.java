package com.example.findmyevent;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import java.util.List;

/*
* Handles the event data and prepares it to be displayed in list format with recylerview
* */

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {
    private List<Event> events;

    public EventAdapter(List<Event> events) {
        this.events = events;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        Event event = events.get(position);
        Log.d("EventSize", String.valueOf(events.size()));
        Log.d("EventAdapter", "Event at position " + position + ": " + event.getName() + " - Image URL: " + (event.getImages().isEmpty() ? "No image" : event.getImages().get(0).getUrl()));
        holder.eventName.setText(event.getName());
        holder.eventDetails.setText("Event ID: " + event.getId());
        if (event.getImages() != null && !event.getImages().isEmpty()) {
            Picasso.get()
                    .load(event.getImages().get(0).getUrl())
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    .into(holder.eventImage);
        } else {
            holder.eventImage.setImageResource(R.drawable.ic_launcher_foreground);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), EventDetailActivity.class);
                intent.putExtra("EVENT_NAME", event.getName());
                intent.putExtra("EVENT_ID", event.getId());
                intent.putExtra("EVENT_URL", event.getUrl());

                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public static class EventViewHolder extends RecyclerView.ViewHolder {
        TextView eventName, eventDetails;
        ImageView eventImage;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            eventName = itemView.findViewById(R.id.eventName);
            eventDetails = itemView.findViewById(R.id.eventDetails);
            eventImage = itemView.findViewById(R.id.eventImage);
        }
    }
}