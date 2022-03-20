package com.ismt.dibeshrajsubedi.journeyjournal.views.activities.home;

import android.os.Bundle;
import android.view.View;

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
import com.ismt.dibeshrajsubedi.journeyjournal.view_models.helper.CommonViewModel;

public class HomeActivity extends AppCompatActivity {

    private CommonViewModel CommonViewModel;
    private BottomNavigationView bottomNavigationView;
    private FloatingActionButton fabButton;
    private NavHostFragment navHostFragment;
    private BottomAppBar bottomAppBar;
    private View logout;
    private Toolbar toolbar;
    private NavController navController;

    private void extractElements() {
        // Component Binding to Get Common Transactions
        CommonViewModel = new ViewModelProvider(this).get(CommonViewModel.class);
        // Extract Bottom Navigation View
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        // Extract Bottom Appbar
        bottomAppBar = findViewById(R.id.bottom_appbar);
        // Extracting Fragment Manager
        FragmentManager fragmentManager = getSupportFragmentManager();
        // Extracting NavHost Fragment to Get NavController
        navHostFragment = (NavHostFragment) fragmentManager.findFragmentById(R.id.home_nav_host_fragment);
        // Extracting Navigation Controller From NavHost Fragment
        if (navHostFragment != null) navController = navHostFragment.getNavController();
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
        if (navHostFragment != null)
            // Setting Navigation UI with NavController
            NavigationUI.setupWithNavController(bottomNavigationView, navController);
    }

    private void setNavigationViewBasedOnBundle(Bundle bundle) {
        CommonViewModel.setVisibility(bottomAppBar, bundle.getBoolean(getString(R.string.args_show_bottom_navigation_view), false));
        CommonViewModel.setVisibility(fabButton, bundle.getBoolean(getString(R.string.args_show_bottom_navigation_view), false));
    }

    private void showViewsBasedOnNavigationArguments() {
        navController.addOnDestinationChangedListener((navController, navDestination, bundle) -> {
            if (bundle == null) return;
            // Condition 1: Show Toolbar Based on Argument passed
            CommonViewModel.setVisibility(toolbar, bundle.getBoolean(getString(R.string.args_show_toolbar), false));
            // Condition 2: Show Navigation View Based on Argument passed
            setNavigationViewBasedOnBundle(bundle);
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
        // Trigger Add Journey Button
        fabButton.setOnClickListener(v -> navController.navigate(R.id.action_global_addFragment));
        // Trigger Logout Button Click From Bottom Navigation
        logout.setOnClickListener(v -> CommonViewModel.logoutConfirmation(HomeActivity.this));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        // Step 1: Extract Elements To Use Globally
        extractElements();
        // Step 2: Setup Components
        setUpComponents();
        // Step 3: Bugfix Navigation UI
        bugFixBottomNavigationUI();
        // Step 4: Trigger Button Click Events
        triggerButtonClickEvents();
    }

}