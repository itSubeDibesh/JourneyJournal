package com.ismt.dibeshrajsubedi.journeyjournal.views.fragments.home.journey.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.ismt.dibeshrajsubedi.journeyjournal.R;

/**
 * Project JourneyJournal with package com.ismt.dibeshrajsubedi.journeyjournal.views.fragments.home.home was
 * Created by Dibesh Raj Subedi on 3/8/2022.
 */
public class HomeRecyclerViewAdapter extends RecyclerView.Adapter<HomeRecyclerViewAdapter.HomeRecyclerViewHolder> {

    public HomeRecyclerViewAdapter() { }

    @NonNull
    @Override
    public HomeRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.journey_card_item, parent, false);
        return new HomeRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeRecyclerViewHolder holder, int position) {
        // Set Text, Description, Image and Date Here
        // TODO: Remove Hard Coded String With Journey Module
        holder.imageView.setImageResource(R.drawable.ic_img_landing);
        holder.title.setText("Welcome to Nepal");
        holder.date.setText("14/07/1999");
        holder.description.setText(R.string.dummy_lorem);
    }

    @Override
    public int getItemCount() {
        // ToDo: get size and return it
        return 10;
    }

    static class HomeRecyclerViewHolder extends RecyclerView.ViewHolder {
        // Accessing Data Set from Fragment To Update elements in View.
        TextView title, description, date;
        ImageView imageView;

        public HomeRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_item_title);
            description = itemView.findViewById(R.id.tv_item_description);
            date = itemView.findViewById(R.id.tv_item_date);
            imageView = itemView.findViewById(R.id.iv_item_image);
            // TODO: Handle On Item Click Event
            itemView.setOnClickListener(view -> Snackbar.make(view, "Item Clicked", Snackbar.LENGTH_LONG).show());
        }
    }
}
