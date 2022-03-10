package com.ismt.dibeshrajsubedi.journeyjournal.views.fragments.home.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.ismt.dibeshrajsubedi.journeyjournal.R;
import com.ismt.dibeshrajsubedi.journeyjournal.views.activities.journey.JourneyActivity;
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

    /**
     * Extracts Elements on Local Scope
     *
     * @param viewGroup ViewGroup
     */
    private void extractElements(ViewGroup viewGroup) {
        this.context = requireContext();
        this.recyclerView = viewGroup.findViewById(R.id.rv_journey_item);
        this.swipeRefreshLayout = viewGroup.findViewById(R.id.srl_refresh_list);
        this.linearLayoutManager = new LinearLayoutManager(this.context);
        this.homeRecyclerViewAdapter = new HomeRecyclerViewAdapter(this.context, this);
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
    }

    /**
     * Refreshes the List of Array Data Set
     */
    private void refreshList() {
        HomeRecyclerViewAdapter homeRecyclerViewAdapter = new HomeRecyclerViewAdapter(this.context, this);
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
        view.putExtra("Action", "VIEW");
        view.putExtra("JourneyModule", journeyModule);
        startActivity(view);
    }
}