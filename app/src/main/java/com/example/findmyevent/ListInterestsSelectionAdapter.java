package com.example.findmyevent;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

// Extends the Adapter class to RecyclerView.Adapter
// and implement the unimplemented methods
public class ListInterestsSelectionAdapter extends RecyclerView.Adapter<ListInterestsSelectionAdapter.ListInterestsSelectionViewHolder> {
    ArrayList interestImg;
    ArrayList interestName;
    Context context;

    // Constructor for initialization
    public ListInterestsSelectionAdapter(Context context, ArrayList interestImg, ArrayList interestName) {
        this.context = context;
        this.interestImg = interestImg;
        this.interestName = interestName;
    }

    @NonNull
    @Override
    public ListInterestsSelectionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflating the Layout(Instantiates list_item.xml
        // layout file into View object)
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_interests_selection, parent, false);

        // Passing view to ViewHolder
        ListInterestsSelectionViewHolder listInterestsSelectionViewHolder = new ListInterestsSelectionViewHolder(view);
        return listInterestsSelectionViewHolder;
    }

    // Binding data to the into specified position
    @Override
    public void onBindViewHolder(@NonNull ListInterestsSelectionViewHolder holder, int position) {
        int res = (int) interestImg.get(position);
        holder.images.setImageResource(res);
        holder.text.setText((String) interestName.get(position));
    }

    @Override
    public int getItemCount() {
        // Returns number of items
        // currently available in Adapter
        return interestImg.size();
    }

    // Initializing the Views
    public class ListInterestsSelectionViewHolder extends RecyclerView.ViewHolder {
        ImageView images;
        TextView text;

        public ListInterestsSelectionViewHolder(View view) {
            super(view);
            images = (ImageView) view.findViewById(R.id.interestImg);
            text = (TextView) view.findViewById(R.id.interestName);
        }
    }
}