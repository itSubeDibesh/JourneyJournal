package com.ismt.dibeshrajsubedi.journeyjournal.views.activities;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.ismt.dibeshrajsubedi.journeyjournal.R;
import com.ismt.dibeshrajsubedi.journeyjournal.view_models.helper.CommonViewModel;

public class AuthenticationActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);
        CommonViewModel commonViewModel = new ViewModelProvider(this).get(CommonViewModel.class);
        commonViewModel.cacheLoggedIn(this, this);
    }
}