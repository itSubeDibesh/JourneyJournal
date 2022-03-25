package com.ismt.dibeshrajsubedi.journeyjournal.views.fragments.home;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
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
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseUser;
import com.ismt.dibeshrajsubedi.journeyjournal.R;
import com.ismt.dibeshrajsubedi.journeyjournal.dao.authentication.ResetDAO;
import com.ismt.dibeshrajsubedi.journeyjournal.dao.authentication.register.RegisterDetailsDAO;
import com.ismt.dibeshrajsubedi.journeyjournal.dao.helper.ConnectivityHelperDAO;
import com.ismt.dibeshrajsubedi.journeyjournal.view_models.authentication.ForgetPasswordViewModel;
import com.ismt.dibeshrajsubedi.journeyjournal.view_models.helper.CommonViewModel;
import com.ismt.dibeshrajsubedi.journeyjournal.view_models.home.ProfileViewModel;

import java.util.Objects;

public class ProfileFragment extends Fragment {
    // Permission Requests
    public static final int CAMERA_REQUEST_CODE = 102;
    public static final int GALLERY_REQUEST_CODE = 105;

    private final String TAG = "JJ_ProfileFragment";

    private FirebaseUser user;
    private LifecycleOwner owner;
    private boolean internetConnected;
    private RegisterDetailsDAO registerDetailsDAO;

    // Input Fields
    private FloatingActionButton fab_profile_image_camera, fab_profile_image_gallery;
    private ImageView iv_profile_image;
    private TextInputLayout til_email, til_name;
    private TextView tv_reset_password;
    private Button btn_journey_edit;

    // View Models
    private ForgetPasswordViewModel forgetPasswordViewModel;
    private CommonViewModel commonViewModel;
    private ProfileViewModel profileViewModel;

    // Image Variables and helpers
    private Uri extractedUri;
    private Uri image;
    private Bitmap imageBitmap;
    private boolean isCamera = false;

    private void extractDetailsFromIntent() {
        if (requireActivity().getIntent() != null) {
            user = requireActivity().getIntent().getParcelableExtra("USER");
            if (requireActivity().getIntent().getStringExtra("ImageURI") != null) {
                extractedUri = Uri.parse(requireActivity().getIntent().getStringExtra("ImageURI"));
            }
            registerDetailsDAO = (RegisterDetailsDAO) requireActivity().getIntent().getSerializableExtra("PROFILE");
            Log.d(TAG, "extractDetailsFromIntent: fetched User Data from Login Fragment and email as " + user.getEmail() + " And Image URI as " + user.getPhotoUrl() + " and extractedUri as " + extractedUri);
        }
    }

    public void isInternetConnected() {
        ConnectivityHelperDAO helper = commonViewModel.checkInternetConnection(requireContext());
        internetConnected = helper.getStatus();
        Log.d(TAG, "onNetworkChanged: Triggered, Received internetConnected as " + internetConnected);
    }

    private void observeMutableLiveData(View view) {
        forgetPasswordViewModel.getForgetPasswordViewModelMutableLiveData().observe(owner, forgetPasswordModel -> {
            if (forgetPasswordModel.getStatus()) {
                Toast.makeText(requireContext(), forgetPasswordModel.getMessage(), Toast.LENGTH_LONG).show();
                commonViewModel.logout(owner, requireActivity());
            } else {
                Snackbar.make(view, forgetPasswordModel.getMessage(), Snackbar.LENGTH_LONG).show();
            }
        });
        profileViewModel.getIsProfileUpdated().observe(owner, profileModel -> {
            if (profileModel.getStatus()) {
                Toast.makeText(requireContext(), profileModel.getMessage(), Toast.LENGTH_LONG).show();
                commonViewModel.logout(owner, requireActivity());
            } else {
                Snackbar.make(view, profileModel.getMessage(), Snackbar.LENGTH_LONG).show();
            }
        });
        profileViewModel.getIsNameInValid().observe(owner, helper -> commonViewModel.setObserverError(til_email, helper));
    }

    private void extractElements(View view) {
        owner = getViewLifecycleOwner();
        image = null;
        imageBitmap = null;
        fab_profile_image_camera = view.findViewById(R.id.fab_profile_image_camera);
        fab_profile_image_gallery = view.findViewById(R.id.fab_profile_image_gallery);
        iv_profile_image = view.findViewById(R.id.iv_profile_image);
        til_email = view.findViewById(R.id.til_email);
        til_name = view.findViewById(R.id.til_name);
        tv_reset_password = view.findViewById(R.id.tv_reset_password);
        btn_journey_edit = view.findViewById(R.id.btn_journey_edit);
    }

    private void populateDetailsOnLoad() {
        Objects.requireNonNull(til_email.getEditText()).setText(user.getEmail());
        if (registerDetailsDAO != null) {
            Objects.requireNonNull(til_name.getEditText()).setText(registerDetailsDAO.getDisplayName());
        }
        if (extractedUri != null) {
            Log.d(TAG, "populateDetailsOnLoad: URI "+extractedUri);
            Glide.with(requireContext())
                    .load(extractedUri)
                    .into(iv_profile_image);
        } else {
            iv_profile_image.setImageResource(R.drawable.ic_img_profile);
        }
    }

    private void handleTriggerEvent() {
        // Camera Image
        fab_profile_image_camera.setOnClickListener(event -> {
            Toast.makeText(requireContext(), R.string.message_opening_camera, Toast.LENGTH_SHORT).show();
            isCamera = true;
            Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(camera, CAMERA_REQUEST_CODE);
        });
        // Gallery Image
        fab_profile_image_gallery.setOnClickListener(event -> {
            Toast.makeText(requireContext(), R.string.message_opening_gallery, Toast.LENGTH_SHORT).show();
            isCamera = false;
            Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(gallery, GALLERY_REQUEST_CODE);
        });
        // Click on Update Button
        btn_journey_edit.setOnClickListener(event -> {
            isInternetConnected();
            profileViewModel.validateProfile(user, new RegisterDetailsDAO(
                    registerDetailsDAO.getEmail(),
                    commonViewModel.til(til_name)
            ), image, internetConnected, requireContext(), owner, isCamera, imageBitmap);
        });
        // Click on Reset Password Click Event
        tv_reset_password.setOnClickListener(event -> {
            isInternetConnected();
            forgetPasswordViewModel.resetValidation(new ResetDAO(user.getEmail()), internetConnected, owner);
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == CAMERA_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                imageBitmap = (Bitmap) data.getExtras().get("data");
                image = null;
                iv_profile_image.setImageBitmap(imageBitmap);
            }
        }

        if (requestCode == GALLERY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                image = data.getData();
                imageBitmap = null;
                iv_profile_image.setImageURI(image);
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Step 0: Binding View Model
        forgetPasswordViewModel = new ViewModelProvider(this).get(ForgetPasswordViewModel.class);
        commonViewModel = new ViewModelProvider(this).get(CommonViewModel.class);
        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
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