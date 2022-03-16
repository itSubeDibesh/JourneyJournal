package com.ismt.dibeshrajsubedi.journeyjournal.views.fragments.home.journey.add;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.ismt.dibeshrajsubedi.journeyjournal.R;

/**
 * Used For Adding Journey
 */
public class AddFragment extends Fragment {

    private TextView til_journey_address;
    private Button btn_journey_add, btn_journey_get_location;
    private FloatingActionButton fab_image_add;
    private ImageView iv_journey_image;
    private TextInputEditText tf_journey_title, tf_journey_description;

    private void extractElements(ViewGroup view) {
        til_journey_address = view.findViewById(R.id.til_journey_address);
        btn_journey_add = view.findViewById(R.id.btn_journey_add);
        btn_journey_get_location = view.findViewById(R.id.btn_journey_get_location);
        fab_image_add = view.findViewById(R.id.fab_image_add);
        tf_journey_title = view.findViewById(R.id.tf_journey_title);
        tf_journey_description = view.findViewById(R.id.tf_journey_description);
        iv_journey_image = view.findViewById(R.id.iv_journey_image);
    }


    private void buttonTrigger() {
        btn_journey_add.setOnClickListener(v -> {
            Toast.makeText(requireContext(), tf_journey_title.getText().toString().trim() + " Added.", Toast.LENGTH_SHORT).show();
        });
        btn_journey_get_location.setOnClickListener(v -> Toast.makeText(requireContext(), "Work on Get Location", Toast.LENGTH_SHORT).show());
        fab_image_add.setOnClickListener(v -> Toast.makeText(requireContext(), "Work onAdd Image", Toast.LENGTH_SHORT).show());
    }

    public AddFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_add, container, false);
        this.extractElements(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.buttonTrigger();
    }
}