package com.ismt.dibeshrajsubedi.journeyjournal.views.fragments.home.journey;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.ismt.dibeshrajsubedi.journeyjournal.R;
import com.ismt.dibeshrajsubedi.journeyjournal.dao.helper.ConnectivityHelperDAO;
import com.ismt.dibeshrajsubedi.journeyjournal.dao.home.JourneyDAO;
import com.ismt.dibeshrajsubedi.journeyjournal.dao.home.JourneyRetrieverDAO;
import com.ismt.dibeshrajsubedi.journeyjournal.dao.home.LocationDAO;
import com.ismt.dibeshrajsubedi.journeyjournal.view_models.helper.CommonViewModel;
import com.ismt.dibeshrajsubedi.journeyjournal.view_models.home.JourneyViewModel;

/**
 * View journey Including Delete for itself
 */
public class ViewFragment extends Fragment {
    private final String TAG = "JJ_" + ViewFragment.class.getSimpleName();
    private TextView tv_journey_title, tv_journey_date, tv_journey_location_name, tv_journey_description, btn_share, tv_journey_map;
    private Button btn_journey_edit, btn_journey_delete;
    private ImageView iv_journey_image;
    private NavController navController;
    private JourneyRetrieverDAO journeyRetrieverDAO;
    private JourneyViewModel journeyViewModel;
    private CommonViewModel commonViewModel;
    private boolean internetConnected;

    private void extractElementsFromIntents(Bundle bundle) {
        if (bundle != null) {
            journeyRetrieverDAO = (JourneyRetrieverDAO) bundle.getSerializable("JourneyRetrieverDAO");
            Log.d(TAG, "extractElementsFromIntents: Retrieved Key as " + journeyRetrieverDAO.getKey());
        }
    }

    public void isInternetConnected() {
        ConnectivityHelperDAO helper = commonViewModel.checkInternetConnection(requireContext());
        internetConnected = helper.getStatus();
        Log.d(TAG, "onNetworkChanged: Triggered, Received internetConnected as " + internetConnected);
    }

    private void extractElements(ViewGroup view) {
        internetConnected = false;
        tv_journey_title = view.findViewById(R.id.tv_journey_title);
        tv_journey_date = view.findViewById(R.id.tv_journey_date);
        tv_journey_map = view.findViewById(R.id.tv_journey_map);
        tv_journey_location_name = view.findViewById(R.id.tv_journey_location_name);
        tv_journey_description = view.findViewById(R.id.tv_journey_description);
        btn_journey_edit = view.findViewById(R.id.btn_journey_edit);
        btn_journey_delete = view.findViewById(R.id.btn_journey_delete);
        btn_share = view.findViewById(R.id.btn_journey_share);
        iv_journey_image = view.findViewById(R.id.iv_journey_image);
    }


    private void populateData() {
        JourneyDAO journeyDAO = journeyRetrieverDAO.getJourney();
        tv_journey_title.setText(journeyDAO.getJourneyTitle());
        tv_journey_date.setText(journeyDAO.getJourneyDate());
        tv_journey_description.setText(journeyDAO.getJourneyDescription());
        if (journeyDAO.getLocationDAO() != null) {
            // Show Address
            tv_journey_map.setVisibility(View.VISIBLE);
            tv_journey_location_name.setVisibility(View.VISIBLE);
            tv_journey_location_name.setText(journeyDAO.getLocationDAO().getAddress());
        } else {
            tv_journey_location_name.setVisibility(View.GONE);
            tv_journey_map.setVisibility(View.GONE);
        }
        if (journeyDAO.getImageUri() != null) {
            Glide.with(requireContext())
                    .load(journeyDAO.getImageUri())
                    .into(iv_journey_image);
        } else {
            iv_journey_image.setImageResource(R.drawable.ic_img_journey_default);
        }
    }


    private void handleButtonClickEvent() {
        // Update Button Click
        btn_journey_edit.setOnClickListener(event -> {
            // Open Edit Fragment With Details
            Bundle bundle = new Bundle();
            Log.d(TAG, "handleButtonClickEvent: journeyRetrieverDAO key as " + journeyRetrieverDAO.getKey());
            bundle.putSerializable("JourneyRetrieverDAO", journeyRetrieverDAO);
            navController.navigate(R.id.action_viewFragment_to_editFragment, bundle);
        });
        // Share Button Click
        btn_share.setOnClickListener(event -> {
            JourneyDAO journeyDAO = journeyRetrieverDAO.getJourney();
            Intent Share = new Intent(Intent.ACTION_SEND);
            Share.setType("text/plain");
            if (journeyDAO.getLocationDAO() != null) {
                Share.putExtra(Intent.EXTRA_TEXT, journeyDAO.getJourneyTitle() + "\n\n" + journeyDAO.getJourneyDescription() + "\n\n\t\t\tFrom - " + journeyDAO.getLocationDAO().getAddress());
            } else {
                Share.putExtra(Intent.EXTRA_TEXT, journeyDAO.getJourneyTitle() + "\n\n" + journeyDAO.getJourneyDescription());
            }
            startActivity(Intent.createChooser(Share, "Share via"));
        });
        // Delete Button Click
        btn_journey_delete.setOnClickListener(event -> {
            isInternetConnected();
            journeyViewModel.deleteConfirmation(getActivity(), journeyRetrieverDAO, internetConnected, getViewLifecycleOwner());
        });
        // Map Button Click
        tv_journey_map.setOnClickListener(event -> {
            LocationDAO locationDAO = journeyRetrieverDAO.getJourney().getLocationDAO();
            String geoUri = "http://maps.google.com/maps?q=loc:" + locationDAO.getLatitude() + "," + locationDAO.getLongitude() + " (" + locationDAO.getAddress() + ")";
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(geoUri));
            startActivity(intent);
        });
    }

    private void obServeMutableLiveData(View view) {
        journeyViewModel.getIsDeleteSuccess().observe(getViewLifecycleOwner(), statusHelperDAO -> {
            if (statusHelperDAO.getStatus()) {
                Toast.makeText(getContext(), statusHelperDAO.getMessage(), Toast.LENGTH_SHORT).show();
                navController.navigate(R.id.action_viewFragment_to_journeysFragment);
            } else {
                Snackbar.make(view, statusHelperDAO.getMessage(), Snackbar.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Step 0: Binding View Model
        journeyViewModel = new ViewModelProvider(this).get(JourneyViewModel.class);
        commonViewModel = new ViewModelProvider(this).get(CommonViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_view, container, false);
        // Step 1: Extract Elements
        this.extractElements(view);
        // Step 2: Extract ElementsFrom Intents
        extractElementsFromIntents(getArguments());
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Step 3: Extract Navigation Controller
        navController = Navigation.findNavController(view);
        // Step 4: Populate Data
        populateData();
        // Step 5: Handle Trigger Events
        this.handleButtonClickEvent();
        // Step 6: Observe Mutable Live
        this.obServeMutableLiveData(view);
    }
}