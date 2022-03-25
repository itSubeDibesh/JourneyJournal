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
import com.ismt.dibeshrajsubedi.journeyjournal.R;
import com.ismt.dibeshrajsubedi.journeyjournal.dao.home.JourneyRetrieverDAO;
import com.ismt.dibeshrajsubedi.journeyjournal.view_models.home.JourneyViewModel;

import java.util.ArrayList;

/**
 * Project JourneyJournal with package com.ismt.dibeshrajsubedi.journeyjournal.views.fragments.home.home was
 * Created by Dibesh Raj Subedi on 3/8/2022.
 */
public class HomeRecyclerViewAdapter extends RecyclerView.Adapter<HomeRecyclerViewAdapter.HomeRecyclerViewHolder> {
    private final String TAG = "JJ_" + HomeRecyclerViewAdapter.class.getSimpleName();
    private final JourneyViewModel journeyViewModel;
    private final LifecycleOwner owner;
    private final HomeRecyclerViewInterface homeRecyclerViewInterface;
    private ArrayList<JourneyRetrieverDAO> journeyDAOList;
    private Context context;

    public HomeRecyclerViewAdapter(ArrayList<JourneyRetrieverDAO> journeyDAOList, JourneyViewModel journeyViewModel, LifecycleOwner owner, HomeRecyclerViewInterface homeRecyclerViewInterface) {
        this.journeyDAOList = journeyDAOList;
        this.journeyViewModel = journeyViewModel;
        this.owner = owner;
        this.homeRecyclerViewInterface = homeRecyclerViewInterface;
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
        JourneyRetrieverDAO journey = journeyDAOList.get(position);
        // Extraction of Image of Each Holder
        if (journey.getJourney().getImageUri() != null) {
            Glide.with(context).
                    load(journey.getJourney().getImageUri())
                    .into(holder.imageView);
        } else {
            holder.imageView.setImageResource(R.drawable.ic_img_journey_default);
        }

        holder.title.setText(journey.getJourney().getJourneyTitle());
        if (journey.getJourney().getJourneyDate() != null) {
            holder.date.setText(journey.getJourney().getJourneyDate());
            holder.date.setVisibility(View.VISIBLE);
        } else {
            holder.date.setVisibility(View.INVISIBLE);
        }
        holder.description.setText(journey.getJourney().getJourneyDescription());
    }

    public void updateList(ArrayList<JourneyRetrieverDAO> list) {
        journeyDAOList = new ArrayList<>();
        journeyDAOList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (journeyDAOList != null) {
            return journeyDAOList.size();
        } else {
            return 0;
        }
    }

    class HomeRecyclerViewHolder extends RecyclerView.ViewHolder {
        // Accessing Data Set from Fragment To Update elements in View.
        TextView title, description, date;
        ImageView imageView;
        String TAG = "JJ_" + HomeRecyclerViewHolder.class.getSimpleName();

        public HomeRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_item_title);
            description = itemView.findViewById(R.id.tv_item_description);
            date = itemView.findViewById(R.id.tv_item_date);
            imageView = itemView.findViewById(R.id.iv_item_image);
            itemView.setOnClickListener(view -> {
                JourneyRetrieverDAO journeyRetrieverDAO = journeyDAOList.get(getAdapterPosition());
                Log.d(TAG, "HomeRecyclerViewHolder: journeyRetrieverDAO " + journeyRetrieverDAO.getKey());
                homeRecyclerViewInterface.onJourneyListItemClick(journeyRetrieverDAO);
            });
        }
    }
}
