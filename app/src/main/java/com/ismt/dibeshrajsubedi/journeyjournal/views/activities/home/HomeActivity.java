package com.ismt.dibeshrajsubedi.journeyjournal.views.activities.home;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ismt.dibeshrajsubedi.journeyjournal.R;
import com.ismt.dibeshrajsubedi.journeyjournal.views.components.CommonViewModel;

public class HomeActivity extends AppCompatActivity {

    private CommonViewModel commonViewModel;
    private BottomNavigationView bottomNavigationView;
    private BottomAppBar bottomAppBar;
    private FloatingActionButton fabButton;
    private NavHostFragment navHostFragment;
    private FragmentManager fragmentManager;
    private View logout;
    private Toolbar toolbar;
    private NavController navController;

    private void extractElements() {
        // Component Binding to Get Common Transactions
        commonViewModel = new ViewModelProvider(this).get(CommonViewModel.class);
        // Extract Bottom Navigation View
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        // Extracting Bottom Appbar
        bottomAppBar = findViewById(R.id.bottom_appbar);
        // Extracting Fragment Manager
        fragmentManager = getSupportFragmentManager();
        // Extracting NavHost Fragment to Get NavController
        navHostFragment = (NavHostFragment) fragmentManager.findFragmentById(R.id.home_nav_host_fragment);
        // Extracting Navigation Controller From NavHost Fragment
        navController = navHostFragment.getNavController();
        // Extracting Tool Bar
        toolbar = findViewById(R.id.home_toolbar);
        // Extracting FAB for Add Journey
        fabButton = findViewById(R.id.fab_btn_add_journey);
        // Extract Logout button
        logout = findViewById(R.id.app_bar_logout);
    }

    private void setUpToolBarWithNavigationComponent() {
        if (navHostFragment != null) {
            // Extract App Bar Configuration
            AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
            // Setting Navigation UI with tool bar
            NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration);
        }
    }

    private void setUpBottomNavigationViewWithNavController() {
        if (navHostFragment != null) {
            // Setting Navigation UI with NavController
            NavigationUI.setupWithNavController(bottomNavigationView, navController);
        }
    }


    private void setAddEditPage(Bundle bundle) {
        
    }

    private void showViewsBasedOnNavigationArguments() {
        navController.addOnDestinationChangedListener((navController, navDestination, bundle) -> {
            if (bundle == null) return;
            // Condition 1: Show Toolbar Based on Argument passed
            commonViewModel.setVisibility(toolbar, bundle.getBoolean(getString(R.string.args_show_toolbar), false));
            // Condition 2: Show Navigation View Based on Argument passed
            commonViewModel.setVisibility(bottomNavigationView, bundle.getBoolean(getString(R.string.args_show_bottom_navigation_view), false));
            // Condition 3: Set Page based on Argument Bundle Passed for Add or Edit
            setAddEditPage(bundle);
        });
    }

    private void setUpComponents() {
        // Step 1: Setup Toolbar With Navigation Component
        this.setUpToolBarWithNavigationComponent();
        // Step 2: Setup Bottom Navigation with NavController
        this.setUpBottomNavigationViewWithNavController();
        // Step 3: Show view Based on Navigation Argument
        this.showViewsBasedOnNavigationArguments();
    }


    private void bugFixBottomNavigationUI() {
        // Setting Background to Null
        bottomNavigationView.setBackground(null);
        // Disabling the Middle Empty Placeholder
        bottomNavigationView.getMenu().getItem(2).setEnabled(false);
    }

    private void triggerButtonClickEvents() {
        // TODO: Trigger Add Journey Button
        fabButton.setOnClickListener(v -> Toast.makeText(HomeActivity.this, "Add Clicked", Toast.LENGTH_SHORT).show());
        // Trigger Logout Button Click From Bottom Navigation
        logout.setOnClickListener(v -> commonViewModel.logoutConfirmation(HomeActivity.this));
    }

    @Override
    public void onBackPressed() {
        if (bottomNavigationView.getSelectedItemId() == R.id.journeysFragment)
            // Show Exit Confirmation From Home Fragment Only
            commonViewModel.exitConfirmation(HomeActivity.this);
        else
            // Redirect To Home from Other Fragment
            bottomNavigationView.setSelectedItemId(R.id.journeysFragment);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        // Step 1: Extract Elements To Use Globally
        this.extractElements();
        // Step 2: Setup Components
        this.setUpComponents();
        // Step 5: Bugfix Navigation UI
        this.bugFixBottomNavigationUI();
        // Step 6: Trigger Button Click Events
        this.triggerButtonClickEvents();
    }

}