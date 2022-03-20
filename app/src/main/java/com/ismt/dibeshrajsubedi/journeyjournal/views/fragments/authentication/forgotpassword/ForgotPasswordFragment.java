package com.ismt.dibeshrajsubedi.journeyjournal.views.fragments.authentication.forgotpassword;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.ismt.dibeshrajsubedi.journeyjournal.R;

/**
 * Navigates to Login Fragment and Sends Reset Email
 */
public class ForgotPasswordFragment extends Fragment {

    private Button reset;
    private TextView login;
    private NavController navController;

    /**
     * Extract Elements Globally
     *
     * @param viewGroup ViewGroup
     */
    public void extractElements(ViewGroup viewGroup) {
        login = viewGroup.findViewById(R.id.tv_login);
        reset = viewGroup.findViewById(R.id.btn_reset);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_forgot_password, container, false);
        extractElements(viewGroup);
        return viewGroup;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Initialize Navigation Controller
        navController = Navigation.findNavController(view);
        // Navigate to Login
        // TODO -> Implement Reset Email Send Procedure and redirect to Login
        reset.setOnClickListener(event -> navController.navigate(R.id.action_forgotPasswordFragment_to_loginFragment));
        // Navigate to Login
        login.setOnClickListener(event -> navController.navigate(R.id.action_forgotPasswordFragment_to_loginFragment));
    }
}