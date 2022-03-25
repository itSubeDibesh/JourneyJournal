package com.ismt.dibeshrajsubedi.journeyjournal.views.fragments.authentication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.textfield.TextInputLayout;
import com.ismt.dibeshrajsubedi.journeyjournal.R;
import com.ismt.dibeshrajsubedi.journeyjournal.dao.authentication.register.RegisterFormDAO;
import com.ismt.dibeshrajsubedi.journeyjournal.dao.helper.ConnectivityHelperDAO;
import com.ismt.dibeshrajsubedi.journeyjournal.view_models.authentication.RegisterViewModel;
import com.ismt.dibeshrajsubedi.journeyjournal.view_models.helper.CommonViewModel;
import com.ismt.dibeshrajsubedi.journeyjournal.views.activities.HomeActivity;

/**
 * Navigates to Home Activity or Login Fragment
 */
public class RegisterFragment extends Fragment {
    private final String TAG = "JJ_RegisterFragment";
    private boolean internetConnected;
    private LifecycleOwner owner;
    private Button register;
    private TextView login;
    private NavController navController;
    private ImageButton google, twitter;
    private RegisterViewModel registerViewModel;
    private CommonViewModel commonViewModel;
    private TextInputLayout rtil_name, rtil_email, rtil_password, rtil_retype_password;

    public void extractElements(ViewGroup viewGroup) {
        owner = getViewLifecycleOwner();
        internetConnected = false;
        login = viewGroup.findViewById(R.id.tv_login);
        register = viewGroup.findViewById(R.id.btn_register);
        google = viewGroup.findViewById(R.id.btn_google);
        twitter = viewGroup.findViewById(R.id.btn_twitter);
        rtil_name = viewGroup.findViewById(R.id.rtil_name);
        rtil_email = viewGroup.findViewById(R.id.rtil_email);
        rtil_password = viewGroup.findViewById(R.id.rtil_password);
        rtil_retype_password = viewGroup.findViewById(R.id.rtil_retype_password);
        Log.d(TAG, "extractElements: Elements extraction complete");
    }

    private void handleButtonTriggerEvents() {
        // Trigger Register Click event
        register.setOnClickListener(view -> {
            isInternetConnected();
            Log.d(TAG, "handleButtonTriggerEvents: Register Button triggered");
            if (internetConnected) {
                registerViewModel.registrationValidation(new RegisterFormDAO(
                                commonViewModel.til(rtil_name),
                                commonViewModel.til(rtil_email),
                                commonViewModel.til(rtil_password),
                                commonViewModel.til(rtil_retype_password)),
                        internetConnected, owner
                );
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.confirmation_internet_disconnected);
                builder.setIcon(R.drawable.ic_launcher_foreground);
                builder.setMessage(R.string.connection_offline);
                builder.setNegativeButton(R.string.option_ok, (dialog, id) -> dialog.cancel());
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
        // Trigger Google Click event
        google.setOnClickListener(v -> {
            Log.d(TAG, "handleButtonTriggerEvents: Google Button triggered");
            // TODO: Implement Google Click Redirect
            commonViewModel.googleAuth(getActivity(), getViewLifecycleOwner());
        });
        // Trigger Twitter Click event
        twitter.setOnClickListener(v -> {
            Log.d(TAG, "handleButtonTriggerEvents: Twitter Button triggered");
            // TODO: Implement Twitter Click Redirect
            commonViewModel.twitterAuth(getActivity(), getViewLifecycleOwner());
        });
    }

    public void isInternetConnected() {
        ConnectivityHelperDAO helper = commonViewModel.checkInternetConnection(requireContext());
        internetConnected = helper.getStatus();
        Log.d(TAG, "onNetworkChanged: Triggered, Received " + internetConnected + " as Status and " + helper.getMessage() + " message.");
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void observeMutableLiveData() {
        Log.d(TAG, "observeMutableLiveData: Observing Mutable Live Data");
        // Observer 1: isNameInValid
        registerViewModel.isNameInValid.observe(owner, helper -> commonViewModel.setObserverError(rtil_name, helper));
        // Observer 2: isEmailInValid
        registerViewModel.isEmailInValid.observe(owner, helper -> commonViewModel.setObserverError(rtil_email, helper));
        // Observer 3: isPasswordInValid
        registerViewModel.isPasswordInValid.observe(owner, helper -> commonViewModel.setObserverError(rtil_password, helper));
        // Observer 4: isRetypePasswordNotMatched
        registerViewModel.isRetypePasswordNotMatched.observe(owner, helper -> commonViewModel.setObserverError(rtil_retype_password, helper));
        // Observer 5: isRegisterSuccess
        registerViewModel.isRegisterSuccess.observe(owner, helper -> {
            Toast.makeText(requireContext(), helper.getMessage(), Toast.LENGTH_LONG).show();
            if (helper.getStatus()) {
                Intent intent = new Intent(requireActivity(), HomeActivity.class);
                intent.putExtra("USER", helper.getFirebaseUser());
                intent.putExtra("PROFILE", helper.getRegisterDetailsDAO());
                startActivity(intent);
                requireActivity().finish();
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: onCreate Invoked");
        // Step 1: Bind View Model using ViewModel Provider
        registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
        commonViewModel = new ViewModelProvider(this).get(CommonViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: onCreateView Invoked");
        // Inflating View
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_register, container, false);
        // Step 2: Extract Elements from Views
        extractElements(viewGroup);
        return viewGroup;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onViewCreated: onViewCreated Invoked");
        // Step 3: Initialize Navigation Controller
        navController = Navigation.findNavController(view);
        // Step 4: Initialize Button Click Events
        this.handleButtonTriggerEvents();
        // Step 5: Observe Response Generated from Live Data
        this.observeMutableLiveData();
        // Step 6: Handle Other Redirections -> Navigate to Login
        login.setOnClickListener(event -> navController.navigate(R.id.action_registerFragment_to_loginFragment));
    }
}