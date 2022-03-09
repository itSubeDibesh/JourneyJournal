package com.ismt.dibeshrajsubedi.journeyjournal.views.fragments.add_edit.edit_journey;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.ismt.dibeshrajsubedi.journeyjournal.R;

/**
 * Edits the Journey
 */
public class EditJourneyFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_edit_journey, container, false);
        return view;
    }
}