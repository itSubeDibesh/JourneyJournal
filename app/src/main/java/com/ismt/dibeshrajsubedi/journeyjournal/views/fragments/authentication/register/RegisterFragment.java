package com.ismt.dibeshrajsubedi.journeyjournal.views.fragments.authentication.register;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ismt.dibeshrajsubedi.journeyjournal.R;
import com.ismt.dibeshrajsubedi.journeyjournal.views.activities.home.HomeActivity;

/**
 * Navigates to Home Activity or Login Fragment
 */
public class RegisterFragment extends Fragment {

    private Button register;
    private TextView login;
    private NavController navController;


    /**
     * Extract Elements Globally
     *
     * @param viewGroup ViewGroup
     */
    public void extractElements(ViewGroup viewGroup) {
        login = viewGroup.findViewById(R.id.tv_login);
        register = viewGroup.findViewById(R.id.btn_register);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_register, container, false);
        extractElements(viewGroup);
        return viewGroup;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Initialize Navigation Controller
        navController = Navigation.findNavController(view);
        // Navigate to Home Fragment
        register.setOnClickListener(event -> {
            startActivity(new Intent(requireActivity(), HomeActivity.class));
            requireActivity().finish();
        });
        // Navigate to Login
        login.setOnClickListener(event -> navController.navigate(R.id.action_registerFragment_to_loginFragment));
    }
}