package com.ismt.dibeshrajsubedi.journeyjournal.views.fragments.home.profile;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseUser;
import com.ismt.dibeshrajsubedi.journeyjournal.R;
import com.ismt.dibeshrajsubedi.journeyjournal.dao.authentication.ResetDAO;
import com.ismt.dibeshrajsubedi.journeyjournal.dao.authentication.register.RegisterDetailsDAO;
import com.ismt.dibeshrajsubedi.journeyjournal.dao.helper.ConnectivityHelperDAO;
import com.ismt.dibeshrajsubedi.journeyjournal.view_models.authentication.ForgetPasswordViewModel;
import com.ismt.dibeshrajsubedi.journeyjournal.view_models.helper.CommonViewModel;

import java.util.Objects;

public class ProfileFragment extends Fragment {
    private final String TAG = "JJ_ProfileFragment";
    private FirebaseUser user;
    private LifecycleOwner owner;
    private boolean internetConnected;
    private RegisterDetailsDAO registerDetailsDAO;
    private ImageButton fab_profile_image_add;
    private ImageView iv_profile_image;
    private TextInputLayout til_email, til_name;
    private TextView tv_reset_password;
    private Button btn_journey_edit;
    private ForgetPasswordViewModel forgetPasswordViewModel;
    private CommonViewModel commonViewModel;

    private void extractDetailsFromIntent() {
        if (requireActivity().getIntent() != null) {
            user = requireActivity().getIntent().getParcelableExtra("USER");
            registerDetailsDAO = (RegisterDetailsDAO) requireActivity().getIntent().getSerializableExtra("PROFILE");
            Log.d(TAG, "extractDetailsFromIntent: fetched User Data from Login Fragment and email as " + user.getEmail());
        }
    }

    public void isInternetConnected() {
        ConnectivityHelperDAO helper = commonViewModel.checkInternetConnection(requireContext());
        internetConnected = helper.getStatus();
        Log.d(TAG, "onNetworkChanged: Triggered, Received " + internetConnected + " as Status and " + helper.getMessage() + " message.");
    }

    private void handleTriggerEvent() {
        tv_reset_password.setOnClickListener(event -> {
            isInternetConnected();
            forgetPasswordViewModel.resetValidation(new ResetDAO(user.getEmail()), internetConnected, owner);
        });
    }

    private void observeMutableLiveData(View view) {
        forgetPasswordViewModel.getForgetPasswordViewModelMutableLiveData().observe(owner, forgetPasswordModel -> {
            if (forgetPasswordModel.isSuccess()) {
                Toast.makeText(requireContext(), forgetPasswordModel.getMessage(), Toast.LENGTH_LONG).show();
                commonViewModel.logout(owner, requireActivity());
            } else {
                Snackbar.make(view, forgetPasswordModel.getMessage(), Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void extractElements(View view) {
        owner = getViewLifecycleOwner();
        fab_profile_image_add = view.findViewById(R.id.fab_profile_image_add);
        iv_profile_image = view.findViewById(R.id.iv_profile_image);
        til_email = view.findViewById(R.id.til_email);
        til_name = view.findViewById(R.id.til_name);
        tv_reset_password = view.findViewById(R.id.tv_reset_password);
        btn_journey_edit = view.findViewById(R.id.btn_journey_edit);
    }

    private void populateDetailsOnLoad() {
        Objects.requireNonNull(til_email.getEditText()).setText(user.getEmail());
        // ToDo: Bug On Initial Load might be data handling from Intent
        if (registerDetailsDAO != null) {
            Objects.requireNonNull(til_name.getEditText()).setText(registerDetailsDAO.getDisplayName());
        }
        if (user.getPhotoUrl() != null) {
            iv_profile_image.setImageURI(user.getPhotoUrl());
        } else {
            iv_profile_image.setImageResource(R.drawable.ic_img_profile);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Step 0: Binding View Model
        forgetPasswordViewModel = new ViewModelProvider(this).get(ForgetPasswordViewModel.class);
        commonViewModel = new ViewModelProvider(this).get(CommonViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_profile, container, false);
        // Step 1: Extract Elements
        extractElements(view);
        // Step 2: Extract Details From Intent
        extractDetailsFromIntent();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Step 3: Populate Data
        populateDetailsOnLoad();
        // Step 4: Handle Trigger Events
        handleTriggerEvent();
        // Step 5: Observe Mutable Live
        observeMutableLiveData(view);
    }
}