package com.ismt.dibeshrajsubedi.journeyjournal.views.fragments.authentication.landing;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.ismt.dibeshrajsubedi.journeyjournal.R;
import com.ismt.dibeshrajsubedi.journeyjournal.viewModels.CommonViewModel;


/**
 * Core Application Begins From This Fragment and Passes to Login or Register Fragment
 */
public class LandingFragment extends Fragment {

    private CommonViewModel commonViewModel;
    private Button login, register;
    private NavController navController;

    /**
     * Extract Elements Globally
     *
     * @param viewGroup ViewGroup
     */
    public void extractElements(ViewGroup viewGroup) {
        login = viewGroup.findViewById(R.id.btn_login);
        register = viewGroup.findViewById(R.id.btn_register);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        commonViewModel = new ViewModelProvider(this).get(CommonViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_landing, container, false);
        extractElements(viewGroup);
        return viewGroup;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Initialize Navigation Controller
        navController = Navigation.findNavController(view);
        // Navigate to Login
        login.setOnClickListener(event -> navController.navigate(R.id.action_landingFragment_to_loginFragment));
        // Navigate to Register
        register.setOnClickListener(event -> navController.navigate(R.id.action_landingFragment_to_registerFragment));
        // Handle Back pressed for exit confirmation
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                commonViewModel.exitConfirmation(requireActivity());
            }
        });
    }
}