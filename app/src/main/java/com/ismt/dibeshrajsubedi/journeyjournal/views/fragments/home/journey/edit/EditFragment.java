package com.ismt.dibeshrajsubedi.journeyjournal.views.fragments.home.journey.edit;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.ismt.dibeshrajsubedi.journeyjournal.R;
import com.ismt.dibeshrajsubedi.journeyjournal.dao.home.JourneyRetrieverDAO;
import com.ismt.dibeshrajsubedi.journeyjournal.view_models.home.JourneyViewModel;

public class EditFragment extends Fragment {
    private final String TAG = "JJ_" + EditFragment.class.getSimpleName();
    private JourneyRetrieverDAO journeyRetrieverDAO;
    private JourneyViewModel journeyViewModel;
    private NavController navController;
    private TextInputLayout til_update_journey_title, til_update_journey_description;
    private TextView tv_journey_address;
    private Button btn_journey_get_location, btn_journey_update;

    // Elements
    private ImageView iv_journey_image;
    private FloatingActionButton fab_profile_image_gallery, fab_image_camera;


    private void extractElementsFromIntents() {
        if (this.getArguments() != null) {
            journeyRetrieverDAO = (JourneyRetrieverDAO) this.getArguments().getSerializable("JourneyRetrieverDAO");
            Log.d(TAG, "extractElementsFromIntents: Retrieved Key as " + journeyRetrieverDAO.getKey());
        }
    }

    private void extractElements(View view) {

    }

    private void populateData() {

    }

    private void obServeMutableLiveData() {

    }

    private void handleButtonClickEvent() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Step 0: Binding View Model
        journeyViewModel = new ViewModelProvider(this).get(JourneyViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_edit, container, false);
        // Step 1: Extract ElementsFrom Intents
        extractElementsFromIntents();
        // Step 2: Extract Elements
        this.extractElements(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Step 3: Extract Navigation Controller
        navController = Navigation.findNavController(view);
        // Step 4: Populate Data
        populateData();
        // Step 5: Handle Trigger Events
        this.handleButtonClickEvent();
        // Step 6: Observe Mutable Live
        this.obServeMutableLiveData();
    }
}