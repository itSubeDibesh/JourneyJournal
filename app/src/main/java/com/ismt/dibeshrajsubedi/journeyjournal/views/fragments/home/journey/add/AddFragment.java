package com.ismt.dibeshrajsubedi.journeyjournal.views.fragments.home.journey.add;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
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
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.ismt.dibeshrajsubedi.journeyjournal.R;
import com.ismt.dibeshrajsubedi.journeyjournal.dao.home.LocationDAO;

import java.util.List;
import java.util.Locale;

/**
 * Used For Adding Journey
 */
public class AddFragment extends Fragment implements LocationListener {
    private String TAG = AddFragment.class.getSimpleName();
    private TextView til_journey_address;
    private Button btn_journey_add, btn_journey_get_location;
    private FloatingActionButton fab_image_add;
    private ImageView iv_journey_image;
    private TextInputEditText tf_journey_title, tf_journey_description;
    private NavController navController;
    private LocationManager locationManager;
    private LocationDAO locationDAO;

    private void extractElements(ViewGroup view) {
        til_journey_address = view.findViewById(R.id.til_journey_address);
        btn_journey_add = view.findViewById(R.id.btn_journey_add);
        btn_journey_get_location = view.findViewById(R.id.btn_journey_get_location);
        fab_image_add = view.findViewById(R.id.fab_image_add);
        tf_journey_title = view.findViewById(R.id.tf_journey_title);
        tf_journey_description = view.findViewById(R.id.tf_journey_description);
        iv_journey_image = view.findViewById(R.id.iv_journey_image);
    }

    private void buttonTrigger() {
        btn_journey_add.setOnClickListener(v -> {
            Toast.makeText(requireContext(), tf_journey_title.getText().toString().trim() + " Added.", Toast.LENGTH_SHORT).show();
        });

        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            }, 100);
        }

        btn_journey_get_location.setOnClickListener(v -> {
            Toast.makeText(requireContext(), "Extracting Location", Toast.LENGTH_SHORT).show();
            getLocation();
//            navController.navigate(R.id.action_addFragment_to_mapsFragment);
        });
        fab_image_add.setOnClickListener(v -> Toast.makeText(requireContext(), "Work onAdd Image", Toast.LENGTH_SHORT).show());
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_add, container, false);
        this.extractElements(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        this.buttonTrigger();
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        try{
            Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
            List<Address> addressList = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
            String address = addressList.get(0).getLocality()+", "+addressList.get(0).getCountryName();
            Log.d(TAG, "onLocationChanged: "+addressList.get(0));
            til_journey_address.setText(address);
            locationDAO = new LocationDAO(location.getLatitude(),location.getLongitude(), address);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) { LocationListener.super.onStatusChanged(provider, status, extras); }

    @Override
    public void onProviderEnabled(@NonNull String provider) { LocationListener.super.onProviderEnabled(provider); }

    @Override
    public void onProviderDisabled(@NonNull String provider) { LocationListener.super.onProviderDisabled(provider); }
}