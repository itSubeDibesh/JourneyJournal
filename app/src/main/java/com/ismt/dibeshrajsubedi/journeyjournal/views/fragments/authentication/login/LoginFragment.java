package com.ismt.dibeshrajsubedi.journeyjournal.views.fragments.authentication.login;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.textfield.TextInputLayout;
import com.ismt.dibeshrajsubedi.journeyjournal.R;
import com.ismt.dibeshrajsubedi.journeyjournal.models.authentication.LoginModel;
import com.ismt.dibeshrajsubedi.journeyjournal.models.helper.MConnectivityHelper;
import com.ismt.dibeshrajsubedi.journeyjournal.view_models.CommonViewModel;
import com.ismt.dibeshrajsubedi.journeyjournal.view_models.LoginViewModel;

/**
 * Redirects to Home page or Register Fragment or Forgot Fragment
 */
public class LoginFragment extends Fragment {
    private final String TAG = "JJ_LoginFragment";
    private CommonViewModel commonViewModel;
    private LifecycleOwner owner;
    private LoginViewModel loginViewModel;
    private boolean internetConnected;
    private Button login;
    private ImageButton google, twitter;
    private TextView forgot, register;
    private NavController navController;
    private TextInputLayout ltil_email, ltil_password;

    public void extractElements(ViewGroup viewGroup) {
        internetConnected = false;
        owner = getViewLifecycleOwner();
        ltil_email = viewGroup.findViewById(R.id.ltil_email);
        ltil_password = viewGroup.findViewById(R.id.ltil_password);
        login = viewGroup.findViewById(R.id.btn_login);
        google = viewGroup.findViewById(R.id.btn_google);
        twitter = viewGroup.findViewById(R.id.btn_twitter);
        register = viewGroup.findViewById(R.id.tv_register);
        forgot = viewGroup.findViewById(R.id.tv_forgot_password);
        Log.d(TAG, "extractElements: Elements extraction complete");
    }

    public void isInternetConnected() {
        MConnectivityHelper helper = commonViewModel.checkInternetConnection(requireContext());
        internetConnected = helper.getStatus();
        Log.d(TAG, "onNetworkChanged: Triggered, Received " + internetConnected + " as Status and " + helper.getMessage() + " message.");
    }

    private void handleButtonTriggerEvents() {
        // Navigate to Login
        login.setOnClickListener(event -> {
            isInternetConnected();
            Log.d(TAG, "handleButtonTriggerEvents: Register Button triggered");
            loginViewModel.loginValidation(new LoginModel(
                            commonViewModel.til(ltil_email),
                            commonViewModel.til(ltil_password)),
                    internetConnected, owner);
        });
        // Navigate Google
        google.setOnClickListener(event -> {
            Log.d(TAG, "handleButtonTriggerEvents: Google Button triggered");
            // TODO: Implement Google Click Redirect
        });
        // Navigate Twitter
        twitter.setOnClickListener(event -> {
            Log.d(TAG, "handleButtonTriggerEvents: Twitter Button triggered");
            // TODO: Implement Twitter Click Redirect
        });
    }

    private void observeMutableLiveData(View view) {
        Log.d(TAG, "observeMutableLiveData: Observing Mutable Live Data");
//            startActivity(new Intent(requireActivity(), HomeActivity.class));
//            requireActivity().finish();
        // Observer 1: isEmailInValid
        loginViewModel.isEmailInValid.observe(owner, helper -> commonViewModel.setObserverError(ltil_email, helper));
        // Observer 2: isPasswordInValid
        loginViewModel.isPasswordInValid.observe(owner, helper -> commonViewModel.setObserverError(ltil_password, helper));
        // Observer 3: isLoginSuccess
        loginViewModel.isLoginSuccess.observe(owner, helper->{
            // TODO: isLoginSuccess
        });
        // Observer 4: isUserLoggedIn
        loginViewModel.isLoginSuccess.observe(owner, helper->{
            // TODO: isUserLoggedIn
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: onCreate Invoked");
        // Step 1: Bind View Model using ViewModel Provider
        commonViewModel = new ViewModelProvider(this).get(CommonViewModel.class);
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: onCreateView Invoked");
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_login, container, false);
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
        // Step 6:  Handle Other Redirections
        // Step 6.1: Navigate to Register
        register.setOnClickListener(event -> navController.navigate(R.id.action_loginFragment_to_registerFragment));
        // Step 6.2: Navigate to Forgot Password
        forgot.setOnClickListener(event -> navController.navigate(R.id.action_loginFragment_to_forgotPasswordFragment));
        // Step 7:  Handle Back pressed for exit confirmation
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                commonViewModel.exitConfirmation(requireActivity());
            }
        });
    }

}