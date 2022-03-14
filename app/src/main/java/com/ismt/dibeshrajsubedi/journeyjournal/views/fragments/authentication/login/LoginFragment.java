package com.ismt.dibeshrajsubedi.journeyjournal.views.fragments.authentication.login;

import android.content.Intent;
import android.os.Bundle;
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
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.ismt.dibeshrajsubedi.journeyjournal.R;
import com.ismt.dibeshrajsubedi.journeyjournal.views.activities.home.HomeActivity;
import com.ismt.dibeshrajsubedi.journeyjournal.views.components.ConfirmationViewModel;

/**
 * Redirects to Home page or Register Fragment or Forgot Fragment
 */
public class LoginFragment extends Fragment {

    private ConfirmationViewModel confirmationViewModel;
    private Button login;
    private ImageButton google, twitter;
    private TextView forgot, register;
    private NavController navController;

    /**
     * Extract Elements Globally
     *
     * @param viewGroup ViewGroup
     */
    public void extractElements(ViewGroup viewGroup) {
        login = viewGroup.findViewById(R.id.btn_login);
        google = viewGroup.findViewById(R.id.btn_google);
        twitter = viewGroup.findViewById(R.id.btn_twitter);
        register = viewGroup.findViewById(R.id.tv_register);
        forgot = viewGroup.findViewById(R.id.tv_forgot_password);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        confirmationViewModel = new ViewModelProvider(this).get(ConfirmationViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_login, container, false);
        extractElements(viewGroup);
        return viewGroup;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Initialize Navigation Controller
        navController = Navigation.findNavController(view);
        // Navigate to Login
        login.setOnClickListener(event -> {
            startActivity(new Intent(requireActivity(), HomeActivity.class));
            requireActivity().finish();
        });
        // Navigate to Register
        register.setOnClickListener(event -> navController.navigate(R.id.action_loginFragment_to_registerFragment));
        // Navigate to Forgot Password
        forgot.setOnClickListener(event -> navController.navigate(R.id.action_loginFragment_to_forgotPasswordFragment));
        // Navigate Google
        google.setOnClickListener(event -> Toast.makeText(requireContext(), "Work on Google", Toast.LENGTH_LONG).show());
        // Navigate Twitter
        twitter.setOnClickListener(event -> Toast.makeText(requireContext(), "Work on Twitter", Toast.LENGTH_LONG).show());
        // Handle Back pressed for exit confirmation
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                confirmationViewModel.exitConfirmation(requireActivity());
            }
        });
    }
}