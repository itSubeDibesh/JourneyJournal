package com.ismt.dibeshrajsubedi.journeyjournal.views.fragments.home.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.snackbar.Snackbar;
import com.ismt.dibeshrajsubedi.journeyjournal.R;
import com.ismt.dibeshrajsubedi.journeyjournal.views.activities.journey.JourneyActivity;
import com.ismt.dibeshrajsubedi.journeyjournal.views.components.JourneyMockup.JourneyHandler;
import com.ismt.dibeshrajsubedi.journeyjournal.views.components.JourneyMockup.JourneyModule;

/**
 * Implements List of Journey's and respective events
 */
public class HomeFragment extends Fragment implements HomeRecyclerViewInterface {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayoutManager linearLayoutManager;
    private HomeRecyclerViewAdapter homeRecyclerViewAdapter;
    private Context context;
    private JourneyHandler handler;
    private JourneyModule deletedJourney;

    private final ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            switch (direction) {
                case ItemTouchHelper.LEFT:
                    // Setting Deleted Movie on Buffer
                    deletedJourney = handler.findJourney(position);
                    // Delete Journey
                    handler.deleteJourney(position);
                    homeRecyclerViewAdapter.notifyItemRemoved(position);
                    Snackbar.make(recyclerView, "Journey: " + deletedJourney.getJourneyTitle() + ", deleted!", Snackbar.LENGTH_LONG)
                            .setAction("Undo", view -> {
                                handler.journeyList().add(position, deletedJourney);
                                homeRecyclerViewAdapter.notifyItemInserted(position);
                            }).show();
                    break;
                case ItemTouchHelper.RIGHT:
                    // Edit Journey
                    break;
            }
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };

    /**
     * Extracts Elements on Local Scope
     *
     * @param viewGroup ViewGroup
     */
    private void extractElements(ViewGroup viewGroup) {
        this.context = requireContext();
        this.handler = new JourneyHandler();
        this.recyclerView = viewGroup.findViewById(R.id.rv_journey_item);
        this.swipeRefreshLayout = viewGroup.findViewById(R.id.srl_refresh_list);
        this.linearLayoutManager = new LinearLayoutManager(this.context);
        this.homeRecyclerViewAdapter = new HomeRecyclerViewAdapter(this.context, this.handler, this);
    }

    /**
     * Initialize Elements in Local Scope
     */
    private void initializeListElements() {
        // Setting Layout manager and Adapter
        this.recyclerView.setLayoutManager(this.linearLayoutManager);
        this.recyclerView.setAdapter(this.homeRecyclerViewAdapter);
        this.swipeRefreshLayout.setOnRefreshListener(() -> {
            this.refreshList();
            this.swipeRefreshLayout.setRefreshing(false);
        });

        // Instantiating itemTouch helper and attaching to recycler view to handle swipe right and swipe left
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(this.recyclerView);

    }

    /**
     * Refreshes the List of Array Data Set
     */
    private void refreshList() {
        HomeRecyclerViewAdapter homeRecyclerViewAdapter = new HomeRecyclerViewAdapter(this.context, this.handler, this);
        this.recyclerView.setAdapter(homeRecyclerViewAdapter);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_home, container, false);
        this.extractElements(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.initializeListElements();
    }

    @Override
    public void onJourneyListItemClick(JourneyModule journeyModule) {
        // Open a Journey Page With Journey Details
        Intent view = new Intent(this.context, JourneyActivity.class);
        view.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        view.putExtra("Action", "VIEW");
        view.putExtra("JourneyModule", journeyModule);
        startActivity(view);
    }
}