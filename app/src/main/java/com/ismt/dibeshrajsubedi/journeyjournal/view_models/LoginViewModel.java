package com.ismt.dibeshrajsubedi.journeyjournal.view_models;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

import com.ismt.dibeshrajsubedi.journeyjournal.R;
import com.ismt.dibeshrajsubedi.journeyjournal.models.authentication.LoginModel;
import com.ismt.dibeshrajsubedi.journeyjournal.models.helper.StatusHelperModel;

/**
 * Project JourneyJournal with package com.ismt.dibeshrajsubedi.journeyjournal.view_models was
 * Created by Dibesh Raj Subedi on 3/19/2022.
 */
public class LoginViewModel extends JourneyJournalViewModel {
    private final String TAG = "JJ_LoginViewModel";
    public final MutableLiveData<StatusHelperModel> isPasswordInValid = new MutableLiveData<>();
    public final MutableLiveData<StatusHelperModel> isLoginSuccess = new MutableLiveData<>();
    public final MutableLiveData<StatusHelperModel> isUserLoggedIn = new MutableLiveData<>();

    public LoginViewModel(@NonNull Application application) {
        super(application);
    }

    public void loginValidation(LoginModel loginModel,boolean internetConnected, LifecycleOwner owner ){
        Log.d(TAG, "loginValidation: Triggered loginValidation with internetConnected as " + internetConnected);

        // Email Validation Checks - [Empty Check, Email Pattern Check]
        String Email = loginModel.getEmail();
        if (loginModel.isNullOrEmpty(Email)) {
            isEmailInValid.setValue(new StatusHelperModel(true, getString(R.string.error_empty_email)));
            return;
        } else if (!loginModel.isValidEmail(Email)) {
            isEmailInValid.setValue(new StatusHelperModel(true, getString(R.string.error_invalid_email)));
            return;
        } else isEmailInValid.setValue(new StatusHelperModel(false));

        // Password Validation Check - [EmptyCheck, Password Pattern Check]
        String Password = loginModel.getPassword();
        if (loginModel.isNullOrEmpty(Password)) {
            isPasswordInValid.setValue(new StatusHelperModel(true, getString(R.string.error_empty_password)));
            return;
        } else if (!loginModel.isValidPassword(Password, true)) {
            isPasswordInValid.setValue(new StatusHelperModel(true, getString(R.string.error_invalid_password_format)));
            return;
        } else if (!loginModel.strLenGreaterThan(Password, 6) || !loginModel.strLenLesserThan(Password, 20)) {
            isPasswordInValid.setValue(new StatusHelperModel(true, getString(R.string.error_password_length)));
            return;
        } else isPasswordInValid.setValue(new StatusHelperModel(false));

        // ToDo: Implement UseCase and Other elements of clean architecture
        if(internetConnected){
            // TODO: internetConnected
        }
    }

}
