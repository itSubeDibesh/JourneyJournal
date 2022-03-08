package com.ismt.dibeshrajsubedi.journeyjournal.views.activities.home;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ismt.dibeshrajsubedi.journeyjournal.R;
import com.ismt.dibeshrajsubedi.journeyjournal.views.components.ComponentsViewModel;
import com.ismt.dibeshrajsubedi.journeyjournal.views.fragments.home.about.AboutFragment;
import com.ismt.dibeshrajsubedi.journeyjournal.views.fragments.home.home.HomeFragment;
import com.ismt.dibeshrajsubedi.journeyjournal.views.fragments.home.profile.ProfileFragment;

public class HomeActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private HomeFragment homeFragment;
    private ProfileFragment profileFragment;
    private AboutFragment aboutFragment;
    private ComponentsViewModel componentsViewModel;
    private ActionBar actionBar;

    /**
     * Extract Elements Globally
     */
    public void extractElements() {
        componentsViewModel = new ViewModelProvider(this).get(ComponentsViewModel.class);
        actionBar = getSupportActionBar();
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        homeFragment = new HomeFragment();
        profileFragment = new ProfileFragment();
        aboutFragment = new AboutFragment();
    }

    /**
     * Triggers Elements Globally
     */
    public void triggerEvents() {
        // Setting Background to Null
        bottomNavigationView.setBackground(null);
        // Disabling the Middle Empty Placeholder
        bottomNavigationView.getMenu().getItem(2).setEnabled(false);
        // Load Home Fragment as Default
        getSupportFragmentManager().beginTransaction().replace(R.id.home_frame_layout_container, homeFragment).commit();
        // Navigate on Button Clicked
        bottomNavigationView.setOnItemSelectedListener(item -> {
                    if (item.getItemId() == R.id.app_bar_home) {
                        getSupportFragmentManager().beginTransaction().replace(R.id.home_frame_layout_container, homeFragment).commit();
                        return true;
                    } else if (item.getItemId() == R.id.app_bar_about) {
                        getSupportFragmentManager().beginTransaction().replace(R.id.home_frame_layout_container, aboutFragment).commit();
                        return true;
                    } else if (item.getItemId() == R.id.app_bar_profile) {
                        getSupportFragmentManager().beginTransaction().replace(R.id.home_frame_layout_container, profileFragment).commit();
                        return true;
                    } else if (item.getItemId() == R.id.app_bar_logout) {
                        componentsViewModel.logoutConfirmation(HomeActivity.this);
                        return false;
                    } else {
                        actionBar.setTitle(R.string.app_name);
                        return false;
                    }
                }
        );
    }

    @Override
    public void onBackPressed() {
        componentsViewModel.exitConfirmation(HomeActivity.this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        extractElements();
        triggerEvents();
    }
}