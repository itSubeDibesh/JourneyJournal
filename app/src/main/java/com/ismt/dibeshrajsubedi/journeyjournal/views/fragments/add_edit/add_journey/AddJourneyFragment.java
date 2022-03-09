package com.ismt.dibeshrajsubedi.journeyjournal.views.fragments.add_edit.add_journey;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ismt.dibeshrajsubedi.journeyjournal.R;

/**
 * Used For Adding Journey
 */
public class AddJourneyFragment extends Fragment {

    public AddJourneyFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_add_journey, container, false);
        return view;
    }
}