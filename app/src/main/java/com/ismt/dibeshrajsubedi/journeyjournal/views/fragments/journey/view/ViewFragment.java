package com.ismt.dibeshrajsubedi.journeyjournal.views.fragments.journey.view;

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
import androidx.lifecycle.ViewModelProvider;

import com.ismt.dibeshrajsubedi.journeyjournal.R;
import com.ismt.dibeshrajsubedi.journeyjournal.views.components.ComponentsViewModel;

/**
 * View journey Including Delete for itself
 */
public class ViewFragment extends Fragment {
    private ComponentsViewModel componentsViewModel;
    private TextView tv_journey_title, tv_journey_date, tv_journey_location_name, tv_journey_description, btn_share;
    private Button btn_journey_edit, btn_journey_delete;
    private ImageView iv_journey_image;

    private void extractElements(ViewGroup view) {
        componentsViewModel = new ViewModelProvider(this).get(ComponentsViewModel.class);
        tv_journey_title = view.findViewById(R.id.tv_journey_title);
        tv_journey_date = view.findViewById(R.id.tv_journey_date);
        tv_journey_location_name = view.findViewById(R.id.tv_journey_location_name);
        tv_journey_description = view.findViewById(R.id.tv_journey_description);
        btn_journey_edit = view.findViewById(R.id.btn_journey_edit);
        btn_journey_delete = view.findViewById(R.id.btn_journey_delete);
        btn_share = view.findViewById(R.id.btn_journey_share);
        iv_journey_image = view.findViewById(R.id.iv_journey_image);
    }

    public ViewFragment() {
        // Required empty public constructor
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
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //  TODO: Get Journey Module and Populate Data
        // TODO: Handle Button Trigger Events
    }
}