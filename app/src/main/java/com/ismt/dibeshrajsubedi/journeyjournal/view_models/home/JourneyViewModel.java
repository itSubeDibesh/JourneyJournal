package com.ismt.dibeshrajsubedi.journeyjournal.view_models.home;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

import com.ismt.dibeshrajsubedi.journeyjournal.R;
import com.ismt.dibeshrajsubedi.journeyjournal.dao.helper.StatusHelperDAO;
import com.ismt.dibeshrajsubedi.journeyjournal.dao.home.JourneyDAO;
import com.ismt.dibeshrajsubedi.journeyjournal.dao.home.JourneyRetrieverDAO;
import com.ismt.dibeshrajsubedi.journeyjournal.models.home.JourneyModel;
import com.ismt.dibeshrajsubedi.journeyjournal.view_models.helper.JJ_JourneyViewModel;

import java.util.ArrayList;

/**
 * Project JourneyJournal with package com.ismt.dibeshrajsubedi.journeyjournal.view_models.home was
 * Created by Dibesh Raj Subedi on 3/23/2022.
 */
public class JourneyViewModel extends JJ_JourneyViewModel {
    private final String TAG = "JJ_" + JourneyViewModel.class.getSimpleName();
    private final MutableLiveData<JourneyModel> isAddSuccess = new MutableLiveData<>();
    private final MutableLiveData<JourneyModel> isUpdateSuccess = new MutableLiveData<>();
    private final MutableLiveData<StatusHelperDAO> isDeleteSuccess = new MutableLiveData<>();

    public JourneyViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<JourneyModel> getIsAddSuccess() {
        return isAddSuccess;
    }

    public MutableLiveData<JourneyModel> getIsUpdateSuccess() {
        return isUpdateSuccess;
    }

    public MutableLiveData<StatusHelperDAO> getIsDeleteSuccess() {
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

    public MutableLiveData<ArrayList<JourneyRetrieverDAO>> fetchJourney(String UUid) {
        database.fetchJourneys(UUid);
        return database.getFetchJourney();
    }

    public void updateJourneyValidation(String journeyId, JourneyDAO journeyDAO, Uri image, boolean internetConnected, Context context, LifecycleOwner owner, boolean isCamera, Bitmap bitmap) {
        Log.d(TAG, "updateJourneyValidation: Triggered updateJourneyValidation with internetConnected as " + internetConnected);

        // Title Validation Checks - [Empty Check]
        String Title = journeyDAO.getJourneyTitle();
        TitleInvalid(Title);

        // Description Validation Checks - [Empty Check]
        String Description = journeyDAO.getJourneyDescription();
        DescriptionInvalid(Description);

        // TODO : Implement Offline Code Using Room
        if (internetConnected) {
            if (!journeyDAO.isNullOrEmpty(Title) && !journeyDAO.isNullOrEmpty(Description)) {
                database.updateJourney(journeyId, journeyDAO, image, context, owner, isCamera, bitmap);
                database.getUpdateJourney().observe(owner, journeyModel -> {
                    Log.d(TAG, "updateJourneyValidation: received isSuccess as " + journeyModel.getStatus());
                    isUpdateSuccess.postValue(journeyModel);
                });
            }
        }
    }

    private void deleteJourney(String journeyId, JourneyDAO journeyDAO, boolean internetConnected, LifecycleOwner owner) {
        Log.d(TAG, "deleteJourney: Triggered deleteJourney with internetConnected as " + internetConnected);
        if (internetConnected) {
            database.deleteJourney(journeyId, journeyDAO, owner);
            database.getDeleteJourney();
            database.getDeleteJourney().observe(owner, data -> {
                isDeleteSuccess.postValue(data);
            });
        } else
            isDeleteSuccess.postValue(new StatusHelperDAO(false, "Internet Disconnected"));
    }

    public void deleteConfirmation(Activity activity, JourneyRetrieverDAO journeyRetrieverDAO, boolean internetConnected, LifecycleOwner owner) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(R.string.confirmation_delete);
        builder.setIcon(R.drawable.ic_launcher_foreground);
        builder
                .setMessage(R.string.consent_delete_journey).setCancelable(true)
                .setPositiveButton(R.string.option_yes, (dialog, id) -> {
                    if (internetConnected) {
                        dialog.dismiss();
                        deleteJourney(journeyRetrieverDAO.getKey(), journeyRetrieverDAO.getJourney(), internetConnected, owner);
                        Toast.makeText(activity, "Deleting, " + journeyRetrieverDAO.getJourney().getJourneyTitle(), Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton(R.string.option_no, (dialog, id) -> dialog.cancel());
        AlertDialog alert = builder.create();
        alert.show();

    }

}
