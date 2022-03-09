package com.ismt.dibeshrajsubedi.journeyjournal.views.fragments.journey.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.ismt.dibeshrajsubedi.journeyjournal.R;
import com.ismt.dibeshrajsubedi.journeyjournal.views.components.JourneyMockup.JourneyModule;

/**
 * View journey Including Delete for itself
 */
public class ViewFragment extends Fragment {

    private JourneyModule journeyModule;

    private void getBundleData(Bundle bundle){
        journeyModule = (JourneyModule) bundle.getSerializable("JourneyModule");
        // TODO : Dataset Received need to Implement UI and Organize nad Write Code Workflow.
        Log.d("TAG", "getBundleData: "+journeyModule.getJourneyTitle());
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_view, container, false);
        assert getArguments() != null;
        getBundleData(getArguments());
        return view;
    }
}