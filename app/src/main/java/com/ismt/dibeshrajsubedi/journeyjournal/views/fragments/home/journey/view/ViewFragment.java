package com.ismt.dibeshrajsubedi.journeyjournal.views.fragments.home.journey.view;

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

import com.ismt.dibeshrajsubedi.journeyjournal.R;
import com.ismt.dibeshrajsubedi.journeyjournal.dao.home.JourneyRetrieverDAO;
import com.ismt.dibeshrajsubedi.journeyjournal.view_models.home.JourneyViewModel;

/**
 * View journey Including Delete for itself
 */
public class ViewFragment extends Fragment {
    private final String TAG = "JJ_" + ViewFragment.class.getSimpleName();
    private TextView tv_journey_title, tv_journey_date, tv_journey_location_name, tv_journey_description, btn_share;
    private Button btn_journey_edit, btn_journey_delete;
    private ImageView iv_journey_image;
    private NavController navController;
    private JourneyRetrieverDAO journeyRetrieverDAO;
    private JourneyViewModel journeyViewModel;


    private void extractElementsFromIntents(Bundle bundle) {
        if (bundle != null) {
            journeyRetrieverDAO = (JourneyRetrieverDAO) bundle.getSerializable("JourneyRetrieverDAO");
            Log.d(TAG, "extractElementsFromIntents: Retrieved Key as " + journeyRetrieverDAO.getKey());
        }
    }


    private void extractElements(ViewGroup view) {
        tv_journey_title = view.findViewById(R.id.tv_journey_title);
        tv_journey_date = view.findViewById(R.id.tv_journey_date);
        tv_journey_location_name = view.findViewById(R.id.tv_journey_location_name);
        tv_journey_description = view.findViewById(R.id.tv_journey_description);
        btn_journey_edit = view.findViewById(R.id.btn_journey_edit);
        btn_journey_delete = view.findViewById(R.id.btn_journey_delete);
        btn_share = view.findViewById(R.id.btn_journey_share);
        iv_journey_image = view.findViewById(R.id.iv_journey_image);
    }


    private void populateData() {

    }

    private void handleButtonClickEvent() {

    }

    private void obServeMutableLiveData() {
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
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_view, container, false);
        // Step 1: Extract Elements
        this.extractElements(view);
        // Step 2: Extract ElementsFrom Intents
        extractElementsFromIntents(getArguments());
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