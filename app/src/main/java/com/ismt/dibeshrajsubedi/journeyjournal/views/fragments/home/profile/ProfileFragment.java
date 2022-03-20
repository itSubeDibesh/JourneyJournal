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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseUser;
import com.ismt.dibeshrajsubedi.journeyjournal.R;
import com.ismt.dibeshrajsubedi.journeyjournal.dao.authentication.register.RegisterDetailsDAO;

import java.util.Objects;

public class ProfileFragment extends Fragment {
    private final String TAG = "JJ_ProfileFragment";
    private FirebaseUser user;
    private RegisterDetailsDAO registerDetailsDAO;
    private ImageButton fab_profile_image_add;
    private ImageView iv_profile_image;
    private TextInputLayout til_email, til_name;
    private TextView tv_reset_password;
    private Button btn_journey_edit;

    private void extractDetailsFromIntent() {
        if (requireActivity().getIntent() != null) {
            user = requireActivity().getIntent().getParcelableExtra("USER");
            registerDetailsDAO = (RegisterDetailsDAO) requireActivity().getIntent().getSerializableExtra("PROFILE");
            Log.d(TAG, "extractDetailsFromIntent: fetched User Data from Login Fragment and email as " + registerDetailsDAO.getDisplayName());
            Log.d(TAG, "extractDetailsFromIntent: fetched User Data from Login Fragment and email as " + user.getEmail());
        }
    }

    private void extractElements(View view) {
        fab_profile_image_add = view.findViewById(R.id.fab_profile_image_add);
        iv_profile_image = view.findViewById(R.id.iv_profile_image);
        til_email = view.findViewById(R.id.til_email);
        til_name = view.findViewById(R.id.til_name);
        tv_reset_password = view.findViewById(R.id.tv_reset_password);
        btn_journey_edit = view.findViewById(R.id.btn_journey_edit);
    }

    private void populateDetailsOnLoad(FirebaseUser user) {
        Objects.requireNonNull(til_email.getEditText()).setText(user.getEmail());
        Objects.requireNonNull(til_name.getEditText()).setText(registerDetailsDAO.getDisplayName());
        if (user.getPhotoUrl() != null) {
            iv_profile_image.setImageURI(user.getPhotoUrl());
        } else {
            iv_profile_image.setImageResource(R.drawable.ic_img_profile);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_profile, container, false);
        // Step 1: Extract Elements
        extractElements(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Step 2: Extract Details From Intent
        extractDetailsFromIntent();
        // Step 3: Populate Data
        populateDetailsOnLoad(user);
    }
}