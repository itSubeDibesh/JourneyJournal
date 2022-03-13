package com.ismt.dibeshrajsubedi.journeyjournal.views.activities.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ismt.dibeshrajsubedi.journeyjournal.R;
import com.ismt.dibeshrajsubedi.journeyjournal.views.activities.journey.JourneyActivity;
import com.ismt.dibeshrajsubedi.journeyjournal.views.components.ComponentsViewModel;

public class HomeActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private ComponentsViewModel componentsViewModel;
    private FloatingActionButton fabButton;
    private View logout;

    /**
     * Extract Elements Globally
     */
    private void extractElements() {
        // Extract Bottom Navigation View
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        // Component Binding to Get Common Transactions
        componentsViewModel = new ViewModelProvider(this).get(ComponentsViewModel.class);
        // Extracting FAB for Add Journey
        fabButton = findViewById(R.id.fab_btn_add_journey);
        // Extract Logout button
        logout = findViewById(R.id.app_bar_logout);
    }

    /**
     * Sets Up Bottom Navigation View
     */
    private void setupBottomNavigationView() {
        // Setting Background to Null
        bottomNavigationView.setBackground(null);
        // Disabling the Middle Empty Placeholder
        bottomNavigationView.getMenu().getItem(2).setEnabled(false);
    }

    /**
     * Setting Up Navigation Controller
     */
    private void setupNavigationController() {
        // Extracting NavHost Fragment to Get NavController
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.home_nav_host_fragment);
        // Extracting Nav Controller
        assert navHostFragment != null;
        NavController navController = navHostFragment.getNavController();
        // Setting Navigation UI
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
    }

    /**
     * Triggers Button Elements
     */
    private void initializeButtonClickEvents() {
        // Trigger Add Journey Button
        fabButton.setOnClickListener(v -> {
            Intent add = new Intent(HomeActivity.this, JourneyActivity.class);
            add.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            add.putExtra("Action", "ADD");
            startActivity(add);
        });
        // Trigger Logout Button Click From Bottom Navigation
        logout.setOnClickListener(v -> componentsViewModel.logoutConfirmation(HomeActivity.this));
    }


    @Override
    public void onBackPressed() {
        if (bottomNavigationView.getSelectedItemId() == R.id.journeysFragment)
            // Show Exit Confirmation From Home Fragment Only
            componentsViewModel.exitConfirmation(HomeActivity.this);
        else
            // Redirect To Home from Other Fragment
            bottomNavigationView.setSelectedItemId(R.id.journeysFragment);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        this.extractElements();
        this.setupBottomNavigationView();
        this.initializeButtonClickEvents();
        this.setupNavigationController();
    }
}