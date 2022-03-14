package com.ismt.dibeshrajsubedi.journeyjournal.views.activities.home;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ismt.dibeshrajsubedi.journeyjournal.R;
import com.ismt.dibeshrajsubedi.journeyjournal.views.components.ConfirmationViewModel;

public class HomeActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private ConfirmationViewModel confirmationViewModel;
    private FloatingActionButton fabButton;
    private View logout;

    private void extractElements() {
        // Extract Bottom Navigation View
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        // Component Binding to Get Common Transactions
        confirmationViewModel = new ViewModelProvider(this).get(ConfirmationViewModel.class);
        // Extracting FAB for Add Journey
        fabButton = findViewById(R.id.fab_btn_add_journey);
        // Extract Logout button
        logout = findViewById(R.id.app_bar_logout);
    }

    private void bugFixBottomNavigationUI() {
        // Setting Background to Null
        bottomNavigationView.setBackground(null);
        // Disabling the Middle Empty Placeholder
        bottomNavigationView.getMenu().getItem(2).setEnabled(false);
    }

    private void setupBottomNavigationControllerWithNavHostFragment() {
        // Extracting NavHost Fragment to Get NavController
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.home_nav_host_fragment);
        // Extracting Nav Controller
        if (navHostFragment != null) {
            NavController navController = navHostFragment.getNavController();
            // Setting Navigation UI
            NavigationUI.setupWithNavController(bottomNavigationView, navController);
        }
    }

    private void triggerButtonClickEvents() {
        // TODO: Trigger Add Journey Button
        fabButton.setOnClickListener(v -> Toast.makeText(HomeActivity.this, "Add Clicked", Toast.LENGTH_SHORT).show());
        // Trigger Logout Button Click From Bottom Navigation
        logout.setOnClickListener(v -> confirmationViewModel.logoutConfirmation(HomeActivity.this));
    }

    @Override
    public void onBackPressed() {
        if (bottomNavigationView.getSelectedItemId() == R.id.journeysFragment)
            // Show Exit Confirmation From Home Fragment Only
            confirmationViewModel.exitConfirmation(HomeActivity.this);
        else
            // Redirect To Home from Other Fragment
            bottomNavigationView.setSelectedItemId(R.id.journeysFragment);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        this.extractElements();
        this.bugFixBottomNavigationUI();
        this.triggerButtonClickEvents();
        this.setupBottomNavigationControllerWithNavHostFragment();
    }
}