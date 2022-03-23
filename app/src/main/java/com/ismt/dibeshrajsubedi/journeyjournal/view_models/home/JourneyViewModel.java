package com.ismt.dibeshrajsubedi.journeyjournal.view_models.home;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseUser;
import com.ismt.dibeshrajsubedi.journeyjournal.dao.helper.StatusHelperDAO;
import com.ismt.dibeshrajsubedi.journeyjournal.dao.home.JourneyDAO;
import com.ismt.dibeshrajsubedi.journeyjournal.view_models.helper.JJ_JourneyViewModel;

import java.util.UUID;

/**
 * Project JourneyJournal with package com.ismt.dibeshrajsubedi.journeyjournal.view_models.home was
 * Created by Dibesh Raj Subedi on 3/23/2022.
 */
public class JourneyViewModel extends JJ_JourneyViewModel {
    private final String TAG = "JJ_" + JourneyViewModel.class.getSimpleName();
    private final MutableLiveData<StatusHelperDAO> isAddSuccess = new MutableLiveData<>();
    private final MutableLiveData<StatusHelperDAO> isUpdateSuccess = new MutableLiveData<>();
    private final MutableLiveData<StatusHelperDAO> isDeleteSuccess = new MutableLiveData<>();
    private final MutableLiveData<StatusHelperDAO> isViewSuccess = new MutableLiveData<>();

    public JourneyViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<StatusHelperDAO> getIsAddSuccess() {
        return isAddSuccess;
    }

    public MutableLiveData<StatusHelperDAO> getIsUpdateSuccess() {
        return isUpdateSuccess;
    }

    public MutableLiveData<StatusHelperDAO> getIsDeleteSuccess() {
        return isDeleteSuccess;
    }

    public MutableLiveData<StatusHelperDAO> getIsViewSuccess() {
        return isViewSuccess;
    }

    public void addJourneyValidation(FirebaseUser user, JourneyDAO journeyDAO, boolean internetConnected, Context context, LifecycleOwner owner, boolean isCamera, Bitmap bitmap) {

    }

    public void updateJourneyValidation(FirebaseUser user, JourneyDAO journeyDAO, boolean internetConnected, Context context, LifecycleOwner owner, boolean isCamera, Bitmap bitmap) {

    }

    public void deleteJourney(JourneyDAO journeyDAO, UUID uuid, boolean internetConnected, LifecycleOwner owner) {

    }
}
