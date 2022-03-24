package com.ismt.dibeshrajsubedi.journeyjournal.views.fragments.home.journey.home;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.ismt.dibeshrajsubedi.journeyjournal.R;
import com.ismt.dibeshrajsubedi.journeyjournal.dao.home.JourneyDAO;
import com.ismt.dibeshrajsubedi.journeyjournal.view_models.home.JourneyViewModel;

import java.util.ArrayList;

/**
 * Project JourneyJournal with package com.ismt.dibeshrajsubedi.journeyjournal.views.fragments.home.home was
 * Created by Dibesh Raj Subedi on 3/8/2022.
 */
public class HomeRecyclerViewAdapter extends RecyclerView.Adapter<HomeRecyclerViewAdapter.HomeRecyclerViewHolder> {
    private final ArrayList<JourneyDAO> journeyDAOList;
    private final String TAG = "JJ_" + HomeRecyclerViewAdapter.class.getSimpleName();
    private final JourneyViewModel journeyViewModel;
    private final LifecycleOwner owner;
    private Context context;

    public HomeRecyclerViewAdapter(ArrayList<JourneyDAO> journeyDAOList, JourneyViewModel journeyViewModel, LifecycleOwner owner) {
        Log.d(TAG, "HomeRecyclerViewAdapter: Journey Length " + journeyDAOList.size());
        this.journeyDAOList = journeyDAOList;
        this.journeyViewModel = journeyViewModel;
        this.owner = owner;
    }

    @NonNull
    @Override
    public HomeRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.journey_card_item, parent, false);
        return new HomeRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeRecyclerViewHolder holder, int position) {
        // Set Text, Description, Image and Date Here
        // TODO: Remove Hard Coded String With Journey Module
        JourneyDAO journey = journeyDAOList.get(position);
        // Extraction of Image of Each Holder
        if (journey.getImageUri() != null) {
            Glide.with(context).
                    load(journey.getImageUri())
                    .into(holder.imageView);
        } else {
            holder.imageView.setImageResource(R.drawable.ic_img_landing);
        }

        holder.title.setText(journey.getJourneyTitle());
        if (journey.getJourneyDate() != null) {
            holder.date.setText(journey.getJourneyDate());
            holder.date.setVisibility(View.VISIBLE);
        } else {
            holder.date.setVisibility(View.INVISIBLE);
        }
        holder.description.setText(journey.getJourneyDescription());
    }

    @Override
    public int getItemCount() {
        if (journeyDAOList != null) {
            return journeyDAOList.size();
        } else {
            return 0;
        }
    }

    public static class HomeRecyclerViewHolder extends RecyclerView.ViewHolder {
        // Accessing Data Set from Fragment To Update elements in View.
        TextView title, description, date;
        ImageView imageView;

        public HomeRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_item_title);
            description = itemView.findViewById(R.id.tv_item_description);
            date = itemView.findViewById(R.id.tv_item_date);
            imageView = itemView.findViewById(R.id.iv_item_image);
            // TODO: Handle On Item Click Event https://github.com/itSubeDibesh/JourneyJournal/blob/2681249d0b38abd9f2c1784bb4d8dd044c75380a/app/src/main/java/com/ismt/dibeshrajsubedi/journeyjournal/views/fragments/home/home/HomeRecyclerViewAdapter.java
            itemView.setOnClickListener(view -> Snackbar.make(view, "Item Clicked", Snackbar.LENGTH_LONG).show());
        }
    }
}
