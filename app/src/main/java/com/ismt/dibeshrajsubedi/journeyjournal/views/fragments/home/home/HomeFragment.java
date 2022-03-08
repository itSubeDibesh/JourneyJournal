package com.ismt.dibeshrajsubedi.journeyjournal.views.fragments.home.home;

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

/**
 * Implements List of Journey's and respective events
 */
public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;

    private void extractElements(ViewGroup viewGroup) {
        recyclerView = viewGroup.findViewById(R.id.rv_journey_item);
        // TODO: Swipe Refresh Implementation and Swipe Right to Delete or Edit
        swipeRefreshLayout = viewGroup.findViewById(R.id.srl_refresh_list);
    }

    private void implementCoreElements() {
        // Populating List
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(new HomeRecyclerViewAdapter(requireContext()));
        swipeRefreshLayout.setOnRefreshListener(() -> {
            // TODO: Swipe Refresh Implementation
            // setting Refreshing to false
            swipeRefreshLayout.setRefreshing(false);
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_home, container, false);
        extractElements(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        implementCoreElements();
    }
}