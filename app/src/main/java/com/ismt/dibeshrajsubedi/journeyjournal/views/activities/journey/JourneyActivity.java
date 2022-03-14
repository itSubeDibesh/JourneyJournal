package com.ismt.dibeshrajsubedi.journeyjournal.views.activities.journey;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.ismt.dibeshrajsubedi.journeyjournal.R;
import com.ismt.dibeshrajsubedi.journeyjournal.views.components.ComponentsViewModel;
import com.ismt.dibeshrajsubedi.journeyjournal.views.fragments.journey.add.AddFragment;
import com.ismt.dibeshrajsubedi.journeyjournal.views.fragments.journey.edit.EditFragment;
import com.ismt.dibeshrajsubedi.journeyjournal.views.fragments.journey.view.ViewFragment;

public class JourneyActivity extends AppCompatActivity {

    private ActionBar actionBar;
    private String Action;
    private ComponentsViewModel componentsViewModel;
    private AddFragment addFragment;
    private EditFragment editFragment;
    private ViewFragment viewFragment;
    private FragmentManager fragmentManager;

    /**
     * Extract Elements Globally
     */
    private void extractElements() {
        componentsViewModel = new ViewModelProvider(this).get(ComponentsViewModel.class);
        fragmentManager = getSupportFragmentManager();
        actionBar = getSupportActionBar();
        // Setting Page Title to Empty
        assert actionBar != null;
        actionBar.setTitle("");
        // Extracting Data From Intent
        Action = getIntent().getStringExtra("Action");
        // Instantiating Fragments
        addFragment = new AddFragment();
        editFragment = new EditFragment();
        viewFragment = new ViewFragment();
    }

    /**
     * Loads Fragment calling Component ViewModel
     *
     * @param fragment Fragment
     */
    private void loadFragment(Fragment fragment) {
        componentsViewModel.loadFragment(fragmentManager, fragment);
    }

    /**
     * Sets Page Title on Appbar
     */
    private void setPageTitleWithDetails() {
        // Creating Bundle to Pass DataSet
        Bundle bundle = new Bundle();
        switch (Action) {
            case "ADD":
                actionBar.setTitle(R.string.page_add_journey);
                this.loadFragment(addFragment);
                break;
            case "EDIT":
                actionBar.setTitle(R.string.page_update_journey);
                editFragment.setArguments(bundle);
                this.loadFragment(editFragment);
                break;
            case "VIEW":
                actionBar.setTitle(R.string.page_view_journey);
                viewFragment.setArguments(bundle);
                this.loadFragment(viewFragment);
                break;
            default:
                actionBar.setTitle(R.string.app_name);
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journey);
        this.extractElements();
        this.setPageTitleWithDetails();
    }
}