package com.ismt.dibeshrajsubedi.journeyjournal.views.activities.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.ui.AppBarConfiguration;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ismt.dibeshrajsubedi.journeyjournal.R;
import com.ismt.dibeshrajsubedi.journeyjournal.views.activities.add_edit.AddEditActivity;
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
    private FloatingActionButton fabButton;

    private AppBarConfiguration appBarConfiguration;
    private NavController navController;

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
        fabButton = findViewById(R.id.fab_btn_add_journey);
        // Add Edit Page Config using NavController
    }

    /**
     * Loads Up The Fragment on Frame Layout
     * @param fragment Fragment
     * @return boolean
     */
    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.home_frame_layout_container, fragment)
                    .commit();
            return true;
        }
        return false;
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
        this.loadFragment(homeFragment);
        // Navigate on Button Clicked
        bottomNavigationView.setOnItemSelectedListener(item -> {
                    if (item.getItemId() == R.id.app_bar_home)
                        return this.loadFragment(homeFragment);
                    else if (item.getItemId() == R.id.app_bar_about)
                        return this.loadFragment(aboutFragment);
                    else if (item.getItemId() == R.id.app_bar_profile)
                        return this.loadFragment(profileFragment);
                    else if (item.getItemId() == R.id.app_bar_logout) {
                        componentsViewModel.logoutConfirmation(HomeActivity.this);
                        return false;
                    } else {
                        actionBar.setTitle(R.string.app_name);
                        return false;
                    }
                }
        );
        // Trigger Add Journey Button
        fabButton.setOnClickListener(view -> {
            Intent add = new Intent(HomeActivity.this, AddEditActivity.class);
            add.putExtra("ACTION","ADD");
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
        extractElements();
        triggerEvents();
    }
}