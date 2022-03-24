package com.ismt.dibeshrajsubedi.journeyjournal.view_models.home;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

import com.ismt.dibeshrajsubedi.journeyjournal.dao.home.JourneyDAO;
import com.ismt.dibeshrajsubedi.journeyjournal.models.home.JourneyModel;
import com.ismt.dibeshrajsubedi.journeyjournal.view_models.helper.JJ_JourneyViewModel;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Project JourneyJournal with package com.ismt.dibeshrajsubedi.journeyjournal.view_models.home was
 * Created by Dibesh Raj Subedi on 3/23/2022.
 */
public class JourneyViewModel extends JJ_JourneyViewModel {
    private final String TAG = "JJ_" + JourneyViewModel.class.getSimpleName();
    private final MutableLiveData<JourneyModel> isAddSuccess = new MutableLiveData<>();
    private final MutableLiveData<JourneyModel> isUpdateSuccess = new MutableLiveData<>();
    private final MutableLiveData<JourneyModel> isDeleteSuccess = new MutableLiveData<>();

    public JourneyViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<JourneyModel> getIsAddSuccess() {
        return isAddSuccess;
    }

    public MutableLiveData<JourneyModel> getIsUpdateSuccess() {
        return isUpdateSuccess;
    }

    public MutableLiveData<JourneyModel> getIsDeleteSuccess() {
        return isDeleteSuccess;
    }


    public void addJourneyValidation(JourneyDAO journeyDAO, Uri image, boolean internetConnected, Context context, LifecycleOwner owner, boolean isCamera, Bitmap bitmap) {
        Log.d(TAG, "addJourneyValidation: Triggered addJourneyValidation with internetConnected as " + internetConnected);

        // Title Validation Checks - [Empty Check]
        String Title = journeyDAO.getJourneyTitle();
        TitleInvalid(Title);

        // Description Validation Checks - [Empty Check]
        String Description = journeyDAO.getJourneyDescription();
        DescriptionInvalid(Description);

        // TODO : Implement Offline Code Using Room
        if (internetConnected) {
            if (!journeyDAO.isNullOrEmpty(Title) && !journeyDAO.isNullOrEmpty(Description)) {
                database.addJourney(journeyDAO, image, context, owner, isCamera, bitmap);
                database.getAddJourney().observe(owner, journeyModel -> {
                    Log.d(TAG, "addJourneyValidation: received isSuccess as " + journeyModel.getStatus());
                    isAddSuccess.postValue(journeyModel);
                });
            }
        }

    }

    public MutableLiveData<ArrayList<JourneyDAO>> fetchJourney(String UUid){
        database.fetchJourneys(UUid);
        return database.getFetchJourney();
    }

    public void updateJourneyValidation(JourneyDAO journeyDAO, Uri image, boolean internetConnected, Context context, LifecycleOwner owner, boolean isCamera, Bitmap bitmap) {

    }

    public void deleteJourney(JourneyDAO journeyDAO, UUID uuid, boolean internetConnected, LifecycleOwner owner) {

    }
}
