package com.ismt.dibeshrajsubedi.journeyjournal.views.fragments.authentication;

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

import androidx.activity.OnBackPressedCallback;
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
import com.ismt.dibeshrajsubedi.journeyjournal.dao.authentication.LoginDAO;
import com.ismt.dibeshrajsubedi.journeyjournal.dao.helper.ConnectivityHelperDAO;
import com.ismt.dibeshrajsubedi.journeyjournal.view_models.authentication.LoginViewModel;
import com.ismt.dibeshrajsubedi.journeyjournal.view_models.helper.CommonViewModel;
import com.ismt.dibeshrajsubedi.journeyjournal.views.activities.HomeActivity;

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
        ConnectivityHelperDAO helper = commonViewModel.checkInternetConnection(requireContext());
        internetConnected = helper.getStatus();
        Log.d(TAG, "onNetworkChanged: Triggered, Received " + internetConnected + " as Status and " + helper.getMessage() + " message.");
    }

    private void handleButtonTriggerEvents() {
        // Navigate to Login
        login.setOnClickListener(event -> {
            isInternetConnected();
            Log.d(TAG, "handleButtonTriggerEvents: Login Button triggered");
            if (internetConnected) {
                loginViewModel.loginValidation(new LoginDAO(
                                commonViewModel.til(ltil_email),
                                commonViewModel.til(ltil_password)),
                        internetConnected, owner);
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
        // Navigate Google
        google.setOnClickListener(event -> {
            Log.d(TAG, "handleButtonTriggerEvents: Google Button triggered");
            // TODO: Implement Google Click Redirect
            commonViewModel.googleAuth(getActivity(), getViewLifecycleOwner());
        });
        // Navigate Twitter
        twitter.setOnClickListener(event -> {
            Log.d(TAG, "handleButtonTriggerEvents: Twitter Button triggered");
            // TODO: Implement Twitter Click Redirect
            commonViewModel.twitterAuth(getActivity(), getViewLifecycleOwner());
        });
    }

    private void observeMutableLiveData() {
        Log.d(TAG, "observeMutableLiveData: Observing Mutable Live Data");
        // Observer 1: isEmailInValid
        loginViewModel.isEmailInValid.observe(owner, helper -> commonViewModel.setObserverError(ltil_email, helper));
        // Observer 2: isPasswordInValid
        loginViewModel.isPasswordInValid.observe(owner, helper -> commonViewModel.setObserverError(ltil_password, helper));
        // Observer 3: isLoginSuccess
        loginViewModel.isLoginSuccess.observe(owner, helper -> {
            Log.d(TAG, "observeMutableLiveData: loginViewModel.isLoginSuccess invoked with isLoginSuccess as " + helper.getStatus());
            Toast.makeText(getContext(), helper.getMessage(), Toast.LENGTH_LONG).show();
            if (helper.getStatus()) {
                Intent intent = new Intent(requireActivity(), HomeActivity.class);
                intent.putExtra("USER", helper.getFirebaseUser());
                intent.putExtra("PROFILE", helper.getRegisterDetailsDAO());
                if (helper.getFirebaseUser().getPhotoUrl() != null) {
                    intent.putExtra("ImageURI", helper.getFirebaseUser().getPhotoUrl().toString());
                }
                startActivity(intent);
                requireActivity().finish();
            }
        });
        // Observer 4: isUserLoggedIn
        loginViewModel.isUserLoggedIn.observe(owner, helper -> {
            Log.d(TAG, "observeMutableLiveData: loginViewModel.isUserLoggedIn invoked with isUserLoggedIn as " + helper.getStatus());
            Toast.makeText(getContext(), helper.getMessage(), Toast.LENGTH_LONG).show();
            if (helper.getStatus()) {
                Intent intent = new Intent(requireActivity(), HomeActivity.class);
                intent.putExtra("USER", helper.getFirebaseUser());
                intent.putExtra("PROFILE", helper.getRegisterDetailsDAO());
                if (helper.getFirebaseUser().getPhotoUrl() != null) {
                    intent.putExtra("ImageURI", helper.getFirebaseUser().getPhotoUrl().toString());
                }
                startActivity(intent);
                requireActivity().finish();
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Step 1: Bind View Model using ViewModel Provider
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        commonViewModel = new ViewModelProvider(this).get(CommonViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_login, container, false);
        // Step 2: Extract Elements from Views
        extractElements(viewGroup);
        return viewGroup;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Step 3: Initialize Navigation Controller
        navController = Navigation.findNavController(view);
        // Step 4: Trigger is User Already LoggedIn
        loginViewModel.isUseAlreadyLoggedIn(owner);
        // Step 5: Observe Response Generated from Live Data
        this.observeMutableLiveData();
        // Step 6: Initialize Button Click Events
        this.handleButtonTriggerEvents();
        // Step 7:  Handle Other Redirections
        // Step 7.1: Navigate to Register
        register.setOnClickListener(event -> navController.navigate(R.id.action_loginFragment_to_registerFragment));
        // Step 7.2: Navigate to Forgot Password
        forgot.setOnClickListener(event -> navController.navigate(R.id.action_loginFragment_to_forgotPasswordFragment));
        // Step 8:  Handle Back pressed for exit confirmation
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                commonViewModel.exitConfirmation(requireActivity(), getViewLifecycleOwner());
            }
        });
    }

}