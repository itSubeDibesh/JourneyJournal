package com.ismt.dibeshrajsubedi.journeyjournal.views.fragments.journey.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.ismt.dibeshrajsubedi.journeyjournal.R;
import com.ismt.dibeshrajsubedi.journeyjournal.views.activities.journey.JourneyActivity;
import com.ismt.dibeshrajsubedi.journeyjournal.views.components.ComponentsViewModel;
import com.ismt.dibeshrajsubedi.journeyjournal.views.components.JourneyMockup.JourneyModule;

/**
 * View journey Including Delete for itself
 */
public class ViewFragment extends Fragment {
    private ComponentsViewModel componentsViewModel;
    private TextView tv_journey_title, tv_journey_date, tv_journey_location_name, tv_journey_description, btn_share;
    private Button btn_journey_edit, btn_journey_delete;
    private ImageView iv_journey_image;
    private JourneyModule journeyModule;

    private void extractElements(ViewGroup view) {
        componentsViewModel = new ViewModelProvider(this).get(ComponentsViewModel.class);
        tv_journey_title = view.findViewById(R.id.tv_journey_title);
        tv_journey_date = view.findViewById(R.id.tv_journey_date);
        tv_journey_location_name = view.findViewById(R.id.tv_journey_location_name);
        tv_journey_description = view.findViewById(R.id.tv_journey_description);
        btn_journey_edit = view.findViewById(R.id.btn_journey_edit);
        btn_journey_delete = view.findViewById(R.id.btn_journey_delete);
        iv_journey_image = view.findViewById(R.id.iv_journey_image);
        btn_share = view.findViewById(R.id.btn_journey_share);
    }

    private void setStringElement(String actual_value, TextView textView) {
        textView.setText(actual_value);
    }

    private void populateUserInterface(Bundle bundle) {
        journeyModule = (JourneyModule) bundle.getSerializable("JourneyModule");
        this.setStringElement(journeyModule.getJourneyTitle(), tv_journey_title);
        this.setStringElement(journeyModule.getJourneyCreatedDate(), tv_journey_date);
        this.setStringElement(journeyModule.getAddress(), tv_journey_location_name);
        tv_journey_description.setText(journeyModule.getJourneyDescription());
        iv_journey_image.setImageResource(journeyModule.getJourneyImageResId());
    }

    public ViewFragment() {
        // Required empty public constructor
    }

    private void buttonTrigger() {
        btn_journey_delete.setOnClickListener(v -> componentsViewModel.deleteConfirmation(getActivity()));
        btn_share.setOnClickListener(v -> Toast.makeText(requireContext(), "Work on Share", Toast.LENGTH_SHORT).show());
        btn_journey_edit.setOnClickListener(v -> {
            // Open a Journey Edit Page With Journey Details
            Intent view = new Intent(getContext(), JourneyActivity.class);
            view.putExtra("Action", "EDIT");
            view.putExtra("JourneyModule", journeyModule);
            startActivity(view);
            getActivity().finish();
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_view, container, false);
        this.extractElements(view);
        assert getArguments() != null;
        this.populateUserInterface(getArguments());
        this.buttonTrigger();
        return view;
    }

}