package com.ismt.dibeshrajsubedi.journeyjournal.views.fragments.journey.edit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.ismt.dibeshrajsubedi.journeyjournal.R;

/**
 * Edits the Journey
 */
public class EditFragment extends Fragment {

    private Button btn_journey_update, btn_journey_get_location;
    private TextView til_journey_address;
    private FloatingActionButton fab_image_add;
    private TextInputEditText tf_journey_title, tf_journey_description;
    private ImageView iv_journey_image;

    private void extractElements(ViewGroup view) {
        btn_journey_update = view.findViewById(R.id.btn_journey_update);
        btn_journey_get_location = view.findViewById(R.id.btn_journey_get_location);
        til_journey_address = view.findViewById(R.id.til_journey_address);
        fab_image_add = view.findViewById(R.id.fab_image_add);
        tf_journey_title = view.findViewById(R.id.tf_journey_title);
        tf_journey_description = view.findViewById(R.id.tf_journey_description);
        iv_journey_image = view.findViewById(R.id.iv_journey_image);
    }

    public EditFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_edit, container, false);
        this.extractElements(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //  TODO: Get Journey Module and Populate Data
        // TODO: Handle Button Trigger Events
    }

}