package com.ismt.dibeshrajsubedi.journeyjournal.views.fragments.authentication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.ismt.dibeshrajsubedi.journeyjournal.R;
import com.ismt.dibeshrajsubedi.journeyjournal.dao.helper.ConnectivityHelperDAO;
import com.ismt.dibeshrajsubedi.journeyjournal.view_models.helper.CommonViewModel;


/**
 * Core Application Begins From This Fragment and Passes to Login or Register Fragment
 */
public class LandingFragment extends Fragment {

    private CommonViewModel CommonViewModel;
    private Button login, register;
    private NavController navController;
    private boolean internetConnected;

    public void isInternetConnected() {
        ConnectivityHelperDAO helper = CommonViewModel.checkInternetConnection(requireContext());
        internetConnected = helper.getStatus();
        if (!internetConnected) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(R.string.confirmation_internet_disconnected);
            builder.setIcon(R.drawable.ic_launcher_foreground);
            builder.setMessage(R.string.connection_offline);
            builder.setNegativeButton(R.string.option_ok, (dialog, id) -> dialog.cancel());
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    public void extractElements(View view) {
        internetConnected = false;
        login = view.findViewById(R.id.btn_login);
        register = view.findViewById(R.id.btn_register);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CommonViewModel = new ViewModelProvider(this).get(CommonViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_landing, container, false);
        extractElements(view);
        isInternetConnected();
        return view;
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
                CommonViewModel.exitConfirmation(requireActivity(), getViewLifecycleOwner());
            }
        });
    }
}