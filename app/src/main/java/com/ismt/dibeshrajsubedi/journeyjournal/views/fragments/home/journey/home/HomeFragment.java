package com.ismt.dibeshrajsubedi.journeyjournal.views.fragments.home.journey.home;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.firebase.auth.FirebaseUser;
import com.ismt.dibeshrajsubedi.journeyjournal.R;
import com.ismt.dibeshrajsubedi.journeyjournal.dao.home.JourneyRetrieverDAO;
import com.ismt.dibeshrajsubedi.journeyjournal.view_models.helper.CommonViewModel;
import com.ismt.dibeshrajsubedi.journeyjournal.view_models.home.JourneyViewModel;

import java.util.ArrayList;

/**
 * Implements List of Journey's and respective events
 */
public class HomeFragment extends Fragment implements HomeRecyclerViewInterface {
    private final String TAG = "JJ_" + HomeFragment.class.getSimpleName();
    private CommonViewModel CommonViewModel;
    private JourneyViewModel journeyViewModel;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayoutManager linearLayoutManager;
    private HomeRecyclerViewAdapter homeRecyclerViewAdapter;
    private FirebaseUser user;
    private LifecycleOwner owner;
    private ArrayList<JourneyRetrieverDAO> journeys;
    private NavController navController;
    private SearchView searchView;

    private void observeMutableLiveData(View view) {
        journeyViewModel.fetchJourney(user.getUid())
                .observe(owner, journeyDAOS -> {
                    journeys = journeyDAOS;
                    this.homeRecyclerViewAdapter = new HomeRecyclerViewAdapter(journeyDAOS, journeyViewModel, owner, this);
                    this.recyclerView.setAdapter(homeRecyclerViewAdapter);
                    // Show Hide Recycler View Based on Empty nature
                    CommonViewModel.recyclerViewVisibility(view, homeRecyclerViewAdapter.getItemCount() == 0);
                });
    }

    private void extractElementsFromIntent() {
        if (requireActivity().getIntent() != null) {
            user = requireActivity().getIntent().getParcelableExtra("USER");
            Log.d(TAG, "extractDetailsFromIntent: fetched User Data from Login Fragment and email as " + user.getEmail());
        }
    }

    /**
     * Extracts Elements on Local Scope
     *
     * @param viewGroup ViewGroup
     */
    private void extractElements(ViewGroup viewGroup) {
        owner = getViewLifecycleOwner();
        Context context = requireContext();
        journeys = new ArrayList<>();
        searchView = viewGroup.findViewById(R.id.sv_search_view);
        this.recyclerView = viewGroup.findViewById(R.id.rv_journey_item);
        this.swipeRefreshLayout = viewGroup.findViewById(R.id.srl_refresh_list);
        this.linearLayoutManager = new LinearLayoutManager(context);
        this.homeRecyclerViewAdapter = new HomeRecyclerViewAdapter(journeys, journeyViewModel, owner, this);
    }

    /**
     * Initialize Elements in Local Scope
     */
    private void initializeListElements(View view) {
        // Setting Layout manager and Adapter
        this.recyclerView.setLayoutManager(this.linearLayoutManager);
        this.recyclerView.setAdapter(this.homeRecyclerViewAdapter);
        this.swipeRefreshLayout.setOnRefreshListener(() -> {
            observeMutableLiveData(view);
            this.swipeRefreshLayout.setRefreshing(false);
        });
        // Implementing Search
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<JourneyRetrieverDAO> journeyRetrieverDAOArrayList = new ArrayList<>();
                String query = newText;
                for(JourneyRetrieverDAO item :journeys){
                    if(item.getJourney().getJourneyTitle().contains(query)|| item.getJourney().getJourneyDescription().contains(query)){
                        journeyRetrieverDAOArrayList.add(item);
                    }
                }
                homeRecyclerViewAdapter.updateList(journeyRetrieverDAOArrayList);
                return true;
            }
        });
        // TODO: Instantiating itemTouch helper and attaching to recycler view to handle swipe right and swipe left
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CommonViewModel = new ViewModelProvider(this).get(CommonViewModel.class);
        journeyViewModel = new ViewModelProvider(this).get(JourneyViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_home, container, false);
        this.extractElements(view);
        this.extractElementsFromIntent();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.initializeListElements(view);
        // Extract Navigation Controller
        navController = Navigation.findNavController(view);
        // Show Hide Recycler View Based on Empty nature
        CommonViewModel.recyclerViewVisibility(view, homeRecyclerViewAdapter.getItemCount() == 0);
        // Handle Back pressed for exit confirmation
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                CommonViewModel.exitConfirmation(requireActivity(), getViewLifecycleOwner());
            }
        });
        // Call Journey
        observeMutableLiveData(view);
    }

    @Override
    public void onJourneyListItemClick(JourneyRetrieverDAO journeyRetrieverDAO) {
        Log.d(TAG, "onJourneyListItemClick: JourneyDAO " + journeyRetrieverDAO.getJourney().getJourneyTitle() + " and key as " + journeyRetrieverDAO.getKey());
        Bundle bundle = new Bundle();
        bundle.putSerializable("JourneyRetrieverDAO", journeyRetrieverDAO);
        navController.navigate(R.id.action_journeysFragment_to_viewFragment, bundle);
    }
}