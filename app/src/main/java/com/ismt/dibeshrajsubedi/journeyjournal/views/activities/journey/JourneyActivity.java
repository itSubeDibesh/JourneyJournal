package com.ismt.dibeshrajsubedi.journeyjournal.views.activities.journey;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.ismt.dibeshrajsubedi.journeyjournal.R;
import com.ismt.dibeshrajsubedi.journeyjournal.views.components.ComponentsViewModel;
import com.ismt.dibeshrajsubedi.journeyjournal.views.fragments.journey.add.AddFragment;
import com.ismt.dibeshrajsubedi.journeyjournal.views.fragments.journey.edit.EditFragment;

public class JourneyActivity extends AppCompatActivity {

    private ActionBar actionBar;
    private String Action;
    private ComponentsViewModel componentsViewModel;
    private AddFragment addJourneyFragment;
    private EditFragment editJourneyFragment;
    private FragmentManager fragmentManager;

    /**
     * Extract Elements Globally
     */
    private void extractElements() {
        componentsViewModel = new ViewModelProvider(this).get(ComponentsViewModel.class);
        fragmentManager = getSupportFragmentManager();
        Intent intent = getIntent();
        actionBar = getSupportActionBar();
        // Setting Page Title to Empty
        assert actionBar != null;
        actionBar.setTitle("");
        // Extracting Action From Intent
        Action = intent.getStringExtra("Action");
        // Instantiating Fragments
        addJourneyFragment = new AddFragment();
        editJourneyFragment = new EditFragment();
    }

    /**
     * Sets Page Title on Appbar
     */
    private void setPageTitle() {
        if (Action.equals("ADD")) {
            actionBar.setTitle(R.string.page_add_journey);
            componentsViewModel.loadJourneyAddEditContainerFragment(fragmentManager, addJourneyFragment);
        } else if (Action.equals("EDIT")) {
            actionBar.setTitle(R.string.page_edit_journey);
            componentsViewModel.loadJourneyAddEditContainerFragment(fragmentManager, editJourneyFragment);
        } else {
            actionBar.setTitle(R.string.app_name);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journey);
        this.extractElements();
        this.setPageTitle();
    }
}