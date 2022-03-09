package com.ismt.dibeshrajsubedi.journeyjournal.views.activities.journey;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.ismt.dibeshrajsubedi.journeyjournal.R;
import com.ismt.dibeshrajsubedi.journeyjournal.views.components.ComponentsViewModel;
import com.ismt.dibeshrajsubedi.journeyjournal.views.components.JourneyMockup.JourneyModule;
import com.ismt.dibeshrajsubedi.journeyjournal.views.fragments.journey.add.AddFragment;
import com.ismt.dibeshrajsubedi.journeyjournal.views.fragments.journey.edit.EditFragment;
import com.ismt.dibeshrajsubedi.journeyjournal.views.fragments.journey.view.ViewFragment;

import java.io.Serializable;

public class JourneyActivity extends AppCompatActivity {

    private ActionBar actionBar;
    private String Action;
    private JourneyModule journeyModule;
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
        journeyModule = (JourneyModule)getIntent().getSerializableExtra("JourneyModule");
        // Instantiating Fragments
        addFragment = new AddFragment();
        editFragment = new EditFragment();
        viewFragment = new ViewFragment();
    }

    /**
     * Sets Page Title on Appbar
     */
    private void setPageTitle() {
        if (Action.equals("ADD")) {
            actionBar.setTitle(R.string.page_add_journey);
            componentsViewModel.loadJourneyContainerFragment(fragmentManager, addFragment);
        } else if (Action.equals("EDIT")) {
            actionBar.setTitle(R.string.page_edit_journey);
            componentsViewModel.loadJourneyContainerFragment(fragmentManager, editFragment);
        } else if (Action.equals("VIEW")) {
            actionBar.setTitle(R.string.page_journey);
            // Creating Bundle to Pass DataSet
            Bundle bundle = new Bundle();
            bundle.putSerializable("JourneyModule", journeyModule);
            viewFragment.setArguments(bundle);
            componentsViewModel.loadJourneyContainerFragment(fragmentManager, viewFragment);
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