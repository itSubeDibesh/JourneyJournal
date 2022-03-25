package com.ismt.dibeshrajsubedi.journeyjournal.view_models.home;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseUser;
import com.ismt.dibeshrajsubedi.journeyjournal.R;
import com.ismt.dibeshrajsubedi.journeyjournal.dao.authentication.register.RegisterDetailsDAO;
import com.ismt.dibeshrajsubedi.journeyjournal.dao.helper.StatusHelperDAO;
import com.ismt.dibeshrajsubedi.journeyjournal.models.home.ProfileModel;
import com.ismt.dibeshrajsubedi.journeyjournal.view_models.helper.JJ_AuthViewModel;

/**
 * Project JourneyJournal with package com.ismt.dibeshrajsubedi.journeyjournal.view_models.home was
 * Created by Dibesh Raj Subedi on 3/22/2022.
 */
public class ProfileViewModel extends JJ_AuthViewModel {
    public final MutableLiveData<ProfileModel> isProfileUpdated = new MutableLiveData<>();
    public final MutableLiveData<StatusHelperDAO> isNameInValid = new MutableLiveData<>();
    private final String TAG = "JJ_ProfileViewModel";

    public ProfileViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<ProfileModel> getIsProfileUpdated() {
        return isProfileUpdated;
    }

    public MutableLiveData<StatusHelperDAO> getIsNameInValid() {
        return isNameInValid;
    }

    public void validateProfile(FirebaseUser user, RegisterDetailsDAO registerDetailsDAO, Uri image, boolean internetConnected, Context context, LifecycleOwner owner, boolean isCamera, Bitmap bitmap) {
        Log.d(TAG, "validateProfile: Triggered validateProfile with internetConnected as " + internetConnected);

        // Name Validation Checks - [Empty Check]
        String Name = registerDetailsDAO.getDisplayName();
        if (registerDetailsDAO.isNullOrEmpty(Name)) {
            isNameInValid.setValue(new StatusHelperDAO(true, getString(R.string.error_invalid_name)));
            return;
        } else isNameInValid.setValue(new StatusHelperDAO(false));

        // TODO : Implement Offline Code Using Room
        if (internetConnected) {
            if (!registerDetailsDAO.isNullOrEmpty(Name)) {
                firebaseAuthImpl.updateProfile(user, registerDetailsDAO, image, context, owner,isCamera, bitmap);
                firebaseAuthImpl.getUpdateSuccessMutableLiveData().observe(owner, profileModel -> {
                    Log.d(TAG, "validateProfile: firebaseAuthImpl.getUpdateSuccessMutableLiveData invoked with getStatus as " + profileModel.getStatus());
                    isProfileUpdated.postValue(profileModel);
                });
            }
        }

    }

}
