package com.example.findmyevent;

import android.content.Context;
import android.util.Log;
import android.widget.CheckBox;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

// Extends the Adapter class to RecyclerView.Adapter
// and implement the unimplemented methods
public class ListInterestsSelectionAdapter extends RecyclerView.Adapter<ListInterestsSelectionAdapter.ListInterestsSelectionViewHolder> {
    ArrayList interestImg;
    ArrayList interestName;
    Context context;
    private static Boolean[] chkArr = new Boolean[1000];

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

        CustomSwitchListener customSwitchListener = new CustomSwitchListener();
        // Passing view to ViewHolder
        return new ListInterestsSelectionViewHolder(view, customSwitchListener);
    }

    // Binding data to the into specified position
    @Override
    public void onBindViewHolder(@NonNull ListInterestsSelectionViewHolder holder, int position) {
        int res = (int) interestImg.get(position);
        holder.images.setImageResource(res);
        holder.text.setText((String) interestName.get(position));
        holder.customSwitchListener.updatePosition(position, holder);
    }

    @Override
    public int getItemCount() {
        // Returns number of items
        // currently available in Adapter
        return interestImg.size();
    }

    public Boolean[] getSelectedIds() {
        return chkArr;
    }

    public ArrayList getInterestName() {
        return this.interestName;
    }

    // Initializing the Views
    public class ListInterestsSelectionViewHolder extends RecyclerView.ViewHolder implements CheckboxListener {
        ImageView images;
        TextView text;
        CheckBox checkBox;

        CustomSwitchListener customSwitchListener;


        public ListInterestsSelectionViewHolder(View view, CustomSwitchListener customSwitchListener) {
            super(view);
            images = (ImageView) view.findViewById(R.id.interestImg);
            text = (TextView) view.findViewById(R.id.interestName);
            checkBox = (CheckBox) view.findViewById(R.id.interestCheckbox);
            this.customSwitchListener = customSwitchListener;
            checkBox.setOnCheckedChangeListener(this.customSwitchListener);
        }

        @Override
        public void updateCheck(int pos, boolean val) {
            Log.d("InterestAdapter", "Setting checkbox " + text.getText() + " to " + val);
            checkBox.setChecked(val);
        }
    }

    public interface CheckboxListener {
        void updateCheck(int pos, boolean val);
    }

    private class CustomSwitchListener implements CompoundButton.OnCheckedChangeListener {
        private int position;
        CheckboxListener checkboxListener;


        /**
         * Updates the position according to onBindViewHolder
         *
         * @param position - position of the focused item
         */
        public void updatePosition(int position, ListInterestsSelectionViewHolder holder) {
            this.position = position;
            checkboxListener = holder;
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            chkArr[position] = isChecked;
            checkboxListener.updateCheck(position, isChecked);
        }
    }

}