package com.ismt.dibeshrajsubedi.journeyjournal.views.fragments.home.journey.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.ismt.dibeshrajsubedi.journeyjournal.R;
import com.ismt.dibeshrajsubedi.journeyjournal.views.components.CommonViewModel;

/**
 * Implements List of Journey's and respective events
 */
public class HomeFragment extends Fragment {

    private CommonViewModel commonViewModel;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayoutManager linearLayoutManager;
    private HomeRecyclerViewAdapter homeRecyclerViewAdapter;

    /**
     * Extracts Elements on Local Scope
     *
     * @param viewGroup ViewGroup
     */
    private void extractElements(ViewGroup viewGroup) {
        Context context = requireContext();
        this.recyclerView = viewGroup.findViewById(R.id.rv_journey_item);
        this.swipeRefreshLayout = viewGroup.findViewById(R.id.srl_refresh_list);
        this.linearLayoutManager = new LinearLayoutManager(context);
        this.homeRecyclerViewAdapter = new HomeRecyclerViewAdapter(context);
    }

    /**
     * Initialize Elements in Local Scope
     */
    private void initializeListElements() {
        // Setting Layout manager and Adapter
        this.recyclerView.setLayoutManager(this.linearLayoutManager);
        this.recyclerView.setAdapter(this.homeRecyclerViewAdapter);
        this.swipeRefreshLayout.setOnRefreshListener(() -> this.swipeRefreshLayout.setRefreshing(false));
        // TODO: Instantiating itemTouch helper and attaching to recycler view to handle swipe right and swipe left
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        commonViewModel = new ViewModelProvider(this).get(CommonViewModel.class);
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
        // Handle Back pressed for exit confirmation
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                commonViewModel.exitConfirmation(requireActivity());
            }
        });
    }
}