package com.ismt.dibeshrajsubedi.journeyjournal.views.activities.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ismt.dibeshrajsubedi.journeyjournal.R;
import com.ismt.dibeshrajsubedi.journeyjournal.views.activities.journey.JourneyActivity;
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
    private FragmentManager fragmentManager;
    private FloatingActionButton fabButton;

    /**
     * Extract Elements Globally
     */
    private void extractElements() {
        componentsViewModel = new ViewModelProvider(this).get(ComponentsViewModel.class);
        fragmentManager = getSupportFragmentManager();
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        homeFragment = new HomeFragment();
        profileFragment = new ProfileFragment();
        aboutFragment = new AboutFragment();
        fabButton = findViewById(R.id.fab_btn_add_journey);
    }

    /**
     * Triggers Elements Globally
     */
    private void triggerEvents() {
        // Setting Background to Null
        bottomNavigationView.setBackground(null);
        // Disabling the Middle Empty Placeholder
        bottomNavigationView.getMenu().getItem(2).setEnabled(false);
        // Load Home Fragment as Default
        componentsViewModel.loadHomeContainerFragment(fragmentManager, homeFragment);
        // Navigate on Button Clicked
        bottomNavigationView.setOnItemSelectedListener(item -> {
                    if (item.getItemId() == R.id.app_bar_home)
                        return componentsViewModel.loadHomeContainerFragment(fragmentManager, homeFragment);
                    else if (item.getItemId() == R.id.app_bar_about)
                        return componentsViewModel.loadHomeContainerFragment(fragmentManager, aboutFragment);
                    else if (item.getItemId() == R.id.app_bar_profile)
                        return componentsViewModel.loadHomeContainerFragment(fragmentManager, profileFragment);
                    else if (item.getItemId() == R.id.app_bar_logout) {
                        componentsViewModel.logoutConfirmation(HomeActivity.this);
                        return false;
                    } else {
                        return false;
                    }
                }
        );
        // Trigger Add Journey Button
        fabButton.setOnClickListener(view -> {
            Intent add = new Intent(HomeActivity.this, JourneyActivity.class);
            add.putExtra("Action", "ADD");
            startActivity(add);
        });
    }

    @Override
    public void onBackPressed() {
        if (bottomNavigationView.getSelectedItemId() == R.id.app_bar_home) {
            // Show Exit Confirmation From Home Fragment Only
            componentsViewModel.exitConfirmation(HomeActivity.this);
        } else {
            // Redirect To Home from Other Fragment
            bottomNavigationView.setSelectedItemId(R.id.app_bar_home);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        this.extractElements();
        this.triggerEvents();
    }
}