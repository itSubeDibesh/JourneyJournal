package com.ismt.dibeshrajsubedi.journeyjournal.views.fragments.authentication;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.textfield.TextInputLayout;
import com.ismt.dibeshrajsubedi.journeyjournal.R;
import com.ismt.dibeshrajsubedi.journeyjournal.dao.authentication.ResetDAO;
import com.ismt.dibeshrajsubedi.journeyjournal.dao.helper.ConnectivityHelperDAO;
import com.ismt.dibeshrajsubedi.journeyjournal.view_models.authentication.ForgetPasswordViewModel;
import com.ismt.dibeshrajsubedi.journeyjournal.view_models.helper.CommonViewModel;

/**
 * Navigates to Login Fragment and Sends Reset Email
 */
public class ForgotPasswordFragment extends Fragment {
    private final String TAG = "JJ_ForgotPwdFragment";
    private Button reset;
    private TextView login;
    private boolean internetConnected;
    private TextInputLayout ftil_email;
    private NavController navController;
    private LifecycleOwner owner;
    private ForgetPasswordViewModel forgetPasswordViewModel;
    private CommonViewModel commonViewModel;

    /**
     * Extract Elements Globally
     *
     * @param viewGroup ViewGroup
     */
    public void extractElements(ViewGroup viewGroup) {
        owner = getViewLifecycleOwner();
        internetConnected = false;
        login = viewGroup.findViewById(R.id.tv_login);
        ftil_email = viewGroup.findViewById(R.id.ftil_email);
        reset = viewGroup.findViewById(R.id.btn_reset);
    }

    /**
     * Handle Button trigger Event
     */
    private void handleButtonTriggerEvent() {
        reset.setOnClickListener(event -> {
            isInternetConnected();
            Log.d(TAG, "handleButtonTriggerEvents: Reset Button triggered");
            forgetPasswordViewModel.resetValidation(new ResetDAO(
                    commonViewModel.til(ftil_email)
            ), internetConnected, owner);
        });
    }

    /**
     * Observe Mutable Live Data
     */
    private void observeMutableLiveData() {
        // Observer 1: isEmailInValid
        forgetPasswordViewModel.isEmailInValid.observe(owner, helper -> commonViewModel.setObserverError(ftil_email, helper));
        // Observer 2: Forget Password Observer
        forgetPasswordViewModel.getForgetPasswordViewModelMutableLiveData().observe(owner, forgetPasswordModel -> {
            Toast.makeText(requireContext(), forgetPasswordModel.getMessage(), Toast.LENGTH_LONG).show();
            if (forgetPasswordModel.getStatus()) {
                navController.navigate(R.id.action_forgotPasswordFragment_to_loginFragment);
            }
        });
    }

    /**
     * Checks If Internet is Connected
     */
    public void isInternetConnected() {
        ConnectivityHelperDAO helper = commonViewModel.checkInternetConnection(requireContext());
        internetConnected = helper.getStatus();
        Log.d(TAG, "onNetworkChanged: Triggered, Received " + internetConnected + " as Status and " + helper.getMessage() + " message.");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Step 1: Bind View Model using ViewModel Provider
        forgetPasswordViewModel = new ViewModelProvider(this).get(ForgetPasswordViewModel.class);
        commonViewModel = new ViewModelProvider(this).get(CommonViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_forgot_password, container, false);
        // Step 2: Extract Elements from Views
        extractElements(viewGroup);
        return viewGroup;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Step 3: Initialize Navigation Controller
        navController = Navigation.findNavController(view);
        // Step 4: Initialize Button Click Events
        handleButtonTriggerEvent();
        // Step 5: Observe Response Generated from Live Data
        observeMutableLiveData();
        // Step 6: Navigate to Login
        login.setOnClickListener(event -> navController.navigate(R.id.action_forgotPasswordFragment_to_loginFragment));
    }
}