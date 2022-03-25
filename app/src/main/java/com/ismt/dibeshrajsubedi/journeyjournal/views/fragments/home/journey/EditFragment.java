package com.ismt.dibeshrajsubedi.journeyjournal.views.fragments.home.journey;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.ismt.dibeshrajsubedi.journeyjournal.R;
import com.ismt.dibeshrajsubedi.journeyjournal.dao.helper.ConnectivityHelperDAO;
import com.ismt.dibeshrajsubedi.journeyjournal.dao.home.JourneyDAO;
import com.ismt.dibeshrajsubedi.journeyjournal.dao.home.JourneyRetrieverDAO;
import com.ismt.dibeshrajsubedi.journeyjournal.dao.home.LocationDAO;
import com.ismt.dibeshrajsubedi.journeyjournal.view_models.helper.CommonViewModel;
import com.ismt.dibeshrajsubedi.journeyjournal.view_models.home.JourneyViewModel;

import java.util.List;
import java.util.Locale;

public class EditFragment extends Fragment implements LocationListener {
    // Permission Requests
    public static final int CAMERA_REQUEST_CODE = 102;
    public static final int GALLERY_REQUEST_CODE = 105;

    private final String TAG = "JJ_" + EditFragment.class.getSimpleName();
    private JourneyRetrieverDAO journeyRetrieverDAO;
    private JourneyViewModel journeyViewModel;
    private CommonViewModel commonViewModel;
    private NavController navController;

    // Image Helpers and Placeholders
    private Uri image;
    private Bitmap imageBitmap;
    private boolean isCamera = false;

    private LifecycleOwner owner;
    private boolean internetConnected;
    private LocationManager locationManager;
    private LocationDAO locationDAO;

    // Elements
    private ImageView iv_journey_image;
    private FloatingActionButton fab_profile_image_gallery, fab_image_camera;
    private TextInputLayout til_update_journey_title, til_update_journey_description;
    private TextView tv_journey_address;
    private Button btn_journey_get_location, btn_journey_update;


    private void extractElementsFromIntents() {
        if (this.getArguments() != null) {
            journeyRetrieverDAO = (JourneyRetrieverDAO) this.getArguments().getSerializable("JourneyRetrieverDAO");
            Log.d(TAG, "extractElementsFromIntents: Retrieved Key as " + journeyRetrieverDAO.getKey());
        }
    }

    private void extractElements(View view) {
        locationDAO = null;
        owner = getViewLifecycleOwner();
        internetConnected = false;
        image = null;
        imageBitmap = null;
        iv_journey_image = view.findViewById(R.id.iv_journey_image);
        fab_profile_image_gallery = view.findViewById(R.id.fab_profile_image_gallery);
        fab_image_camera = view.findViewById(R.id.fab_image_camera);
        til_update_journey_title = view.findViewById(R.id.til_update_journey_title);
        til_update_journey_description = view.findViewById(R.id.til_update_journey_description);
        tv_journey_address = view.findViewById(R.id.tv_journey_address);
        btn_journey_get_location = view.findViewById(R.id.btn_journey_get_location);
        btn_journey_update = view.findViewById(R.id.btn_journey_update);
    }

    private void getPermission() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            }, 100);
        }
    }

    public void isInternetConnected() {
        ConnectivityHelperDAO helper = commonViewModel.checkInternetConnection(requireContext());
        internetConnected = helper.getStatus();
        Log.d(TAG, "onNetworkChanged: Triggered, Received internetConnected as " + internetConnected);
    }

    private void populateData() {
        JourneyDAO journeyDAO = journeyRetrieverDAO.getJourney();
        til_update_journey_title.getEditText().setText(journeyDAO.getJourneyTitle());
        til_update_journey_description.getEditText().setText(journeyDAO.getJourneyDescription());
        if (journeyDAO.getLocationDAO() != null) {
            // Show Address
            tv_journey_address.setText(journeyDAO.getLocationDAO().getAddress());
        }
        if (journeyDAO.getImageUri() != null) {
            Glide.with(requireContext())
                    .load(journeyDAO.getImageUri())
                    .into(iv_journey_image);
        } else {
            iv_journey_image.setImageResource(R.drawable.ic_img_journey_default);
        }
        locationDAO= journeyDAO.getLocationDAO();
    }

    private void obServeMutableLiveData(View view) {
        journeyViewModel.getIsTitleInvalid().observe(owner, helper -> commonViewModel.setObserverError(til_update_journey_title, helper));
        journeyViewModel.getIsDescriptionInvalid().observe(owner, helper -> commonViewModel.setObserverError(til_update_journey_description, helper));
        journeyViewModel.getIsUpdateSuccess().observe(owner, journeyModel -> {
            // DO Something
            if (journeyModel.getStatus()) {
                Toast.makeText(requireContext(), journeyModel.getMessage(), Toast.LENGTH_LONG).show();
                navController.navigate(R.id.action_editFragment_to_journeysFragment);
            } else {
                Snackbar.make(view, journeyModel.getMessage(), Snackbar.LENGTH_LONG).show();
            }
        });

    }

    private void handleButtonClickEvent() {
        // Get Permission
        getPermission();
        // Handle Camera Open
        fab_image_camera.setOnClickListener(v -> {
            Toast.makeText(requireContext(), R.string.message_opening_camera, Toast.LENGTH_SHORT).show();
            isCamera = true;
            Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(camera, CAMERA_REQUEST_CODE);
        });
        // Handle Gallery Open
        fab_profile_image_gallery.setOnClickListener(v -> {
            Toast.makeText(requireContext(), R.string.message_opening_gallery, Toast.LENGTH_SHORT).show();
            isCamera = false;
            Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(gallery, GALLERY_REQUEST_CODE);
        });
        // Handle getAddress Open
        btn_journey_get_location.setOnClickListener(v -> {
            Toast.makeText(requireContext(), R.string.message_extracting_location, Toast.LENGTH_SHORT).show();
            getLocation();
        });
        // Handle Update Open
        btn_journey_update.setOnClickListener(event -> {
            // DO Tasks
            isInternetConnected();
            journeyViewModel.updateJourneyValidation(
                    journeyRetrieverDAO.getKey(),
                    new JourneyDAO(
                            journeyRetrieverDAO.getJourney().getJourneyAuthor(),
                            commonViewModel.til(til_update_journey_title),
                            commonViewModel.getDateString(),
                            journeyRetrieverDAO.getJourney().getImageUri(),
                            locationDAO,
                            commonViewModel.til(til_update_journey_description)
                    ), image, internetConnected,
                    getContext(), owner, isCamera,
                    imageBitmap
            );
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == CAMERA_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                imageBitmap = (Bitmap) data.getExtras().get("data");
                image = null;
                iv_journey_image.setImageBitmap(imageBitmap);
            }
        }

        if (requestCode == GALLERY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                image = data.getData();
                imageBitmap = null;
                iv_journey_image.setImageURI(image);
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Step 0: Binding View Model
        commonViewModel = new ViewModelProvider(this).get(CommonViewModel.class);
        journeyViewModel = new ViewModelProvider(this).get(JourneyViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_edit, container, false);
        // Step 1: Extract ElementsFrom Intents
        extractElementsFromIntents();
        // Step 2: Extract Elements
        this.extractElements(view);
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

    @SuppressLint("MissingPermission")
    private void getLocation() {
        try {
            locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        try {
            Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
            List<Address> addressList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            String address = addressList.get(0).getLocality() + ", " + addressList.get(0).getCountryName();
            Log.d(TAG, "onLocationChanged: " + addressList.get(0));
            tv_journey_address.setText(address);
            locationDAO = new LocationDAO(location.getLatitude(), location.getLongitude(), address);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        LocationListener.super.onStatusChanged(provider, status, extras);
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {
        LocationListener.super.onProviderEnabled(provider);
    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
        LocationListener.super.onProviderDisabled(provider);
    }
}