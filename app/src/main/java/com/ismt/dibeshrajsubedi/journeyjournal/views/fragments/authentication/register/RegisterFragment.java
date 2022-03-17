package com.ismt.dibeshrajsubedi.journeyjournal.views.fragments.authentication.register;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.ismt.dibeshrajsubedi.journeyjournal.R;
import com.ismt.dibeshrajsubedi.journeyjournal.models.authentication.MRegistration;
import com.ismt.dibeshrajsubedi.journeyjournal.models.helper.MConnectivityHelper;
import com.ismt.dibeshrajsubedi.journeyjournal.view_models.authentication.register.VMRegister;
import com.ismt.dibeshrajsubedi.journeyjournal.view_models.helper.VMCommon;

/**
 * Navigates to Home Activity or Login Fragment
 */
public class RegisterFragment extends Fragment {
    private final String TAG = "JJ_RegisterFragment";
    private boolean internetConnected;
    private Button register;
    private TextView login;
    private NavController navController;
    private ImageButton google, twitter;
    private VMRegister vmRegister;
    private VMCommon vmCommon;
    private TextInputLayout rtil_name, rtil_email, rtil_password, rtil_retype_password;

    public void extractElements(ViewGroup viewGroup) {
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
            // Step 2: Check is Internet Connected
            isInternetConnected();
            Log.d(TAG, "handleButtonTriggerEvents: Register Button triggered");
            vmRegister.registrationValidation(new MRegistration(
                            vmCommon.til(rtil_name),
                            vmCommon.til(rtil_email),
                            vmCommon.til(rtil_password),
                            vmCommon.til(rtil_retype_password)),
                    internetConnected
            );
        });
        // Trigger Google Click event
        google.setOnClickListener(v -> {
            Log.d(TAG, "handleButtonTriggerEvents: Google Button triggered");
            // TODO: Implement Google Click Redirect
        });
        // Trigger Twitter Click event
        twitter.setOnClickListener(v -> {
            Log.d(TAG, "handleButtonTriggerEvents: Twitter Button triggered");
            // TODO: Implement Twitter Click Redirect
        });
    }

    public void isInternetConnected() {
        MConnectivityHelper helper = vmCommon.checkInternetConnection(requireContext());
        internetConnected = helper.getStatus();
        Log.d(TAG, "onNetworkChanged: Triggered, Received " + internetConnected + " as Status and " + helper.getMessage() + " message.");
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void observeMutableLiveData(View view) {
        Log.d(TAG, "observeMutableLiveData: Observing Mutable Live Data");
        // Observer 1: isNameInValid
        vmRegister.isNameInValid.observe(getViewLifecycleOwner(), helper -> vmCommon.setObserverError(rtil_name, helper));
        // Observer 2: isEmailInValid
        vmRegister.isEmailInValid.observe(getViewLifecycleOwner(), helper -> vmCommon.setObserverError(rtil_email, helper));
        // Observer 3: isPasswordInValid
        vmRegister.isPasswordInValid.observe(getViewLifecycleOwner(), helper -> vmCommon.setObserverError(rtil_password, helper));
        // Observer 4: isRetypePasswordNotMatched
        vmRegister.isRetypePasswordNotMatched.observe(getViewLifecycleOwner(), helper -> vmCommon.setObserverError(rtil_retype_password, helper));
        // Observer 5: isRegisterSuccess
        vmRegister.isRegisterSuccess.observe(getViewLifecycleOwner(), helper -> {
            // TODO: Implement Register Success Redirect
            if (helper.getStatus()) {
                Snackbar.make(view, R.string.success_register, Snackbar.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: onCreate Invoked");
        // Step 1: Bind View Model using ViewModel Provider
        vmRegister = new ViewModelProvider(this).get(VMRegister.class);
        vmCommon = new ViewModelProvider(this).get(VMCommon.class);
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
        this.observeMutableLiveData(view);
        // Step 6: Handle Other Redirections -> Navigate to Login
        login.setOnClickListener(event -> navController.navigate(R.id.action_registerFragment_to_loginFragment));
    }

}