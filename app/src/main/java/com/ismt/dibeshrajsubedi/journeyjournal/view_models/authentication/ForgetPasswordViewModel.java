package com.ismt.dibeshrajsubedi.journeyjournal.view_models.authentication;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

import com.ismt.dibeshrajsubedi.journeyjournal.R;
import com.ismt.dibeshrajsubedi.journeyjournal.dao.authentication.ResetDAO;
import com.ismt.dibeshrajsubedi.journeyjournal.models.authentication.ForgetPasswordModel;
import com.ismt.dibeshrajsubedi.journeyjournal.view_models.helper.JJ_AuthViewModel;

/**
 * Project JourneyJournal with package com.ismt.dibeshrajsubedi.journeyjournal.view_models.authentication was
 * Created by Dibesh Raj Subedi on 3/20/2022.
 */
public class ForgetPasswordViewModel extends JJ_AuthViewModel {

    private final MutableLiveData<ForgetPasswordModel> forgetPasswordViewModelMutableLiveData = new MutableLiveData<>();

    public ForgetPasswordViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<ForgetPasswordModel> getForgetPasswordViewModelMutableLiveData() {
        return forgetPasswordViewModelMutableLiveData;
    }

    public void resetValidation(ResetDAO resetDAO, boolean internetConnected, LifecycleOwner owner) {

        // Email Validation Checks
        String Email = resetDAO.getEmail();
        EmailInValid(Email);

        // TODO : Implement Offline Code Using Room
        if (internetConnected) {
            if (!resetDAO.isNullOrEmpty(Email)) {
                firebaseAuthImpl.resetPassword(resetDAO);
                firebaseAuthImpl.getResetSuccessMutableLiveData().observe(owner, forgetPassword -> {
                    ForgetPasswordModel forgetPasswordModel;
                    if (forgetPassword.getStatus()) {
                        forgetPasswordModel = new ForgetPasswordModel(true, getString(R.string.message_reset_email_sent));
                        forgetPasswordModel.setResetDAO(forgetPassword.getResetDAO());
                    } else {
                        forgetPasswordModel = new ForgetPasswordModel(false, forgetPassword.getMessage());
                    }
                    forgetPasswordViewModelMutableLiveData.postValue(forgetPasswordModel);
                });
            }
        }
    }

}
