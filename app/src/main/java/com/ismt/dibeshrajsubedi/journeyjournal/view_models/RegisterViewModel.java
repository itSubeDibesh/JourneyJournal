package com.ismt.dibeshrajsubedi.journeyjournal.view_models;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

import com.ismt.dibeshrajsubedi.journeyjournal.R;
import com.ismt.dibeshrajsubedi.journeyjournal.domain.classes.Auth;
import com.ismt.dibeshrajsubedi.journeyjournal.domain.classes.Register;
import com.ismt.dibeshrajsubedi.journeyjournal.models.authentication.RegistrationModel;
import com.ismt.dibeshrajsubedi.journeyjournal.models.helper.StatusHelperModel;

/**
 * Project JourneyJournal with package com.ismt.dibeshrajsubedi.journeyjournal.view_models.authentication.register was
 * Created by Dibesh Raj Subedi on 3/15/2022.
 */
public class RegisterViewModel extends JourneyJournalViewModel {
    private final String TAG = "JJ_RegisterViewModel";
    public final MutableLiveData<StatusHelperModel> isNameInValid = new MutableLiveData<>();
    public final MutableLiveData<StatusHelperModel> isPasswordInValid = new MutableLiveData<>();
    public final MutableLiveData<StatusHelperModel> isRetypePasswordNotMatched = new MutableLiveData<>();
    public final MutableLiveData<StatusHelperModel> isRegisterSuccess = new MutableLiveData<>();

    public RegisterViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * Validates Registration and sets MutableLivedata to be observed
     *
     * @param registrationModel RegistrationModel
     * @param internetConnected boolean
     * @param owner             LifecycleOwner
     */
    public void registrationValidation(RegistrationModel registrationModel, boolean internetConnected, LifecycleOwner owner) {
        Log.d(TAG, "registrationValidation: Triggered registrationValidation with internetConnected as " + internetConnected);
        // Extracting And Passing elements
        // TODO :  RegistrationUseCase RegistrationUseCase  Implementation
        //  RegistrationUseCase RegistrationUseCase = new RegistrationUseCase(new RegistrationRepoImpl(new RegisterRemoteImpl(internetConnected, owner), new RegisterLocalImpl()));

        // Name Validation Checks - [Empty Check]
        String Name = registrationModel.getName();
        if (registrationModel.isNullOrEmpty(Name)) {
            isNameInValid.setValue(new StatusHelperModel(true, getString(R.string.error_invalid_name)));
            return;
        } else isNameInValid.setValue(new StatusHelperModel(false));

        // Email Validation Checks - [Empty Check, Email Pattern Check]
        String Email = registrationModel.getEmail();
        if (registrationModel.isNullOrEmpty(Email)) {
            isEmailInValid.setValue(new StatusHelperModel(true, getString(R.string.error_empty_email)));
            return;
        } else if (!registrationModel.isValidEmail(Email)) {
            isEmailInValid.setValue(new StatusHelperModel(true, getString(R.string.error_invalid_email)));
            return;
        } else isEmailInValid.setValue(new StatusHelperModel(false));

        // Password Validation Check - [EmptyCheck, Password Pattern Check]
        String Password = registrationModel.getPassword();
        if (registrationModel.isNullOrEmpty(Password)) {
            isPasswordInValid.setValue(new StatusHelperModel(true, getString(R.string.error_empty_password)));
            return;
        } else if (!registrationModel.isValidPassword(Password, true)) {
            isPasswordInValid.setValue(new StatusHelperModel(true, getString(R.string.error_invalid_password_format)));
            return;
        } else if (!registrationModel.strLenGreaterThan(Password, 6) || !registrationModel.strLenLesserThan(Password, 20)) {
            isPasswordInValid.setValue(new StatusHelperModel(true, getString(R.string.error_password_length)));
            return;
        } else isPasswordInValid.setValue(new StatusHelperModel(false));

        // Password Matched Check - [EmptyCheck, Password Pattern Check, Match Check]
        String RetypePassword = registrationModel.getRetypePassword();
        if (registrationModel.isNullOrEmpty(RetypePassword)) {
            isRetypePasswordNotMatched.setValue(new StatusHelperModel(true, getString(R.string.error_empty_retype_password)));
            return;
        } else if (!registrationModel.strLenGreaterThan(RetypePassword, 6) || !registrationModel.strLenLesserThan(RetypePassword, 20)) {
            isRetypePasswordNotMatched.setValue(new StatusHelperModel(true, getString(R.string.error_password_length)));
            return;
        } else if (!registrationModel.isValidPassword(RetypePassword, true)) {
            isRetypePasswordNotMatched.setValue(new StatusHelperModel(true, getString(R.string.error_invalid_password_format)));
            return;
        } else if (!registrationModel.strIsMatch(Password, RetypePassword)) {
            isRetypePasswordNotMatched.setValue(new StatusHelperModel(true, getString(R.string.error_password_not_match)));
            return;
        } else isRetypePasswordNotMatched.setValue(new StatusHelperModel(false));

        // TODO : Implement Using  Registration UseCase
        //          RegistrationModel RegistrationModel = RegistrationUseCase.registerUser(new Register(Email, Name), new Auth(Email, Password));
        if (internetConnected) {
            firebaseAuthImpl.register(new Register(Email, Name), new Auth(Email, Password));
            firebaseAuthImpl.getDmRegistrationMutableLiveData().observe(owner, regModel -> {
                Log.d(TAG, "registrationValidation: firebaseAuthImpl.getDmRegistrationMutableLiveData invoked with isRegistrationSuccess as " + regModel.isRegistrationSuccess());
                isRegisterSuccess.setValue(new StatusHelperModel(regModel.isRegistrationSuccess(), regModel.getMessage()));
            });
        }
    }
}

