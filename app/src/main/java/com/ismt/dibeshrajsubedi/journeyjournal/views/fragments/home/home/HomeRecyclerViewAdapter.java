package com.ismt.dibeshrajsubedi.journeyjournal.views.fragments.home.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ismt.dibeshrajsubedi.journeyjournal.R;
import com.ismt.dibeshrajsubedi.journeyjournal.views.components.JourneyMockup.JourneyHandler;
import com.ismt.dibeshrajsubedi.journeyjournal.views.components.JourneyMockup.JourneyModule;

/**
 * Project JourneyJournal with package com.ismt.dibeshrajsubedi.journeyjournal.views.fragments.home.home was
 * Created by Dibesh Raj Subedi on 3/8/2022.
 */
public class HomeRecyclerViewAdapter extends RecyclerView.Adapter<HomeRecyclerViewAdapter.HomeRecyclerViewHolder> {

    private final Context context;
    private final JourneyHandler journeyHandler;
    private final HomeRecyclerViewInterface homeRecyclerViewClickInterface;

    public HomeRecyclerViewAdapter(Context context, JourneyHandler journeyHandler, HomeRecyclerViewInterface homeRecyclerViewClickInterface) {
        this.context = context;
        this.journeyHandler = journeyHandler;
        this.homeRecyclerViewClickInterface = homeRecyclerViewClickInterface;
    }

    @NonNull
    @Override
    public HomeRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(this.context).inflate(R.layout.journey_card_item, parent, false);
        return new HomeRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeRecyclerViewHolder holder, int position) {
        // Set Text, Description, Image and Date Here
        JourneyModule journeyModule = this.journeyHandler.journeyList().get(position);
        holder.imageView.setImageResource(journeyModule.getJourneyImageResId());
        holder.title.setText(journeyModule.getJourneyTitle());
        holder.date.setText(journeyModule.getJourneyCreatedDate());
        holder.description.setText(journeyModule.getJourneyDescription());
    }

    @Override
    public int getItemCount() {
        return this.journeyHandler.getLength();
    }

    class HomeRecyclerViewHolder extends RecyclerView.ViewHolder {
        // Accessing Data Set from Fragment To Update elements in View.
        TextView title, description, date;
        ImageView imageView;

        public HomeRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_item_title);
            description = itemView.findViewById(R.id.tv_item_description);
            date = itemView.findViewById(R.id.tv_item_date);
            imageView = itemView.findViewById(R.id.iv_item_image);
            itemView.setOnClickListener(view -> homeRecyclerViewClickInterface
                    .onJourneyListItemClick(journeyHandler.findJourney(getAdapterPosition()))
            );
        }
    }
}
