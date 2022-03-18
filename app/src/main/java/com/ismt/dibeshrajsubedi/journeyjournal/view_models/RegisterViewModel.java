package com.ismt.dibeshrajsubedi.journeyjournal.view_models;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

import com.ismt.dibeshrajsubedi.journeyjournal.R;
import com.ismt.dibeshrajsubedi.journeyjournal.domain.classes.Auth;
import com.ismt.dibeshrajsubedi.journeyjournal.domain.classes.Register;
import com.ismt.dibeshrajsubedi.journeyjournal.frameworks.firebase.FirebaseAuthImpl;
import com.ismt.dibeshrajsubedi.journeyjournal.models.authentication.RegistrationModel;
import com.ismt.dibeshrajsubedi.journeyjournal.models.helper.StatusHelperModel;

/**
 * Project JourneyJournal with package com.ismt.dibeshrajsubedi.journeyjournal.view_models.authentication.register was
 * Created by Dibesh Raj Subedi on 3/15/2022.
 */
public class RegisterViewModel extends AndroidViewModel {
    private final String TAG = "JJ_RegisterViewModel";
    public final MutableLiveData<StatusHelperModel> isNameInValid = new MutableLiveData<>();
    public final MutableLiveData<StatusHelperModel> isEmailInValid = new MutableLiveData<>();
    public final MutableLiveData<StatusHelperModel> isPasswordInValid = new MutableLiveData<>();
    public final MutableLiveData<StatusHelperModel> isRetypePasswordNotMatched = new MutableLiveData<>();
    public final MutableLiveData<StatusHelperModel> isRegisterSuccess = new MutableLiveData<>();
    private final FirebaseAuthImpl firebaseAuthImpl = new FirebaseAuthImpl();

    public RegisterViewModel(@NonNull Application application) {
        super(application);
    }

    private String getString(int res) {
        return getApplication().getString(res);
    }

    public void registrationValidation(RegistrationModel RegistrationModel, boolean internetConnected, LifecycleOwner owner) {
        Log.d(TAG, "registrationValidation: Triggered registrationValidation with internetConnected as " + internetConnected);
        // Extracting And Passing elements
        // TODO :  RegistrationUseCase RegistrationUseCase  Implementation
        //  RegistrationUseCase RegistrationUseCase = new RegistrationUseCase(new RegistrationRepoImpl(new RegisterRemoteImpl(internetConnected, owner), new RegisterLocalImpl()));

        // Name Validation Checks - [Empty Check]
        String Name = RegistrationModel.getName();
        if (RegistrationModel.isNullOrEmpty(Name)) {
            isNameInValid.setValue(new StatusHelperModel(true, getString(R.string.error_invalid_name)));
            return;
        } else isNameInValid.setValue(new StatusHelperModel(false));

        // Email Validation Checks - [Empty Check, Email Pattern Check]
        String Email = RegistrationModel.getEmail();
        if (RegistrationModel.isNullOrEmpty(Email)) {
            isEmailInValid.setValue(new StatusHelperModel(true, getString(R.string.error_empty_email)));
            return;
        } else if (!RegistrationModel.isValidEmail(Email)) {
            isEmailInValid.setValue(new StatusHelperModel(true, getString(R.string.error_invalid_email)));
            return;
        } else isEmailInValid.setValue(new StatusHelperModel(false));

        // Password Validation Check - [EmptyCheck, Password Pattern Check]
        String Password = RegistrationModel.getPassword();
        if (RegistrationModel.isNullOrEmpty(Password)) {
            isPasswordInValid.setValue(new StatusHelperModel(true, getString(R.string.error_empty_password)));
            return;
        } else if (!RegistrationModel.isValidPassword(Password, true)) {
            isPasswordInValid.setValue(new StatusHelperModel(true, getString(R.string.error_invalid_password_format)));
            return;
        } else if (!RegistrationModel.strLenGreaterThan(Password, 6) || !RegistrationModel.strLenLesserThan(Password, 20)) {
            isPasswordInValid.setValue(new StatusHelperModel(true, getString(R.string.error_password_length)));
            return;
        } else isPasswordInValid.setValue(new StatusHelperModel(false));

        // Password Matched Check - [EmptyCheck, Password Pattern Check, Match Check]
        String RetypePassword = RegistrationModel.getRetypePassword();
        if (RegistrationModel.isNullOrEmpty(RetypePassword)) {
            isRetypePasswordNotMatched.setValue(new StatusHelperModel(true, getString(R.string.error_empty_retype_password)));
            return;
        } else if (!RegistrationModel.strLenGreaterThan(RetypePassword, 6) || !RegistrationModel.strLenLesserThan(RetypePassword, 20)) {
            isRetypePasswordNotMatched.setValue(new StatusHelperModel(true, getString(R.string.error_password_length)));
            return;
        } else if (!RegistrationModel.isValidPassword(RetypePassword, true)) {
            isRetypePasswordNotMatched.setValue(new StatusHelperModel(true, getString(R.string.error_invalid_password_format)));
            return;
        } else if (!RegistrationModel.strIsMatch(Password, RetypePassword)) {
            isRetypePasswordNotMatched.setValue(new StatusHelperModel(true, getString(R.string.error_password_not_match)));
            return;
        } else isRetypePasswordNotMatched.setValue(new StatusHelperModel(false));

        // TODO : Implement Using  Registration UseCase
        //          RegistrationModel RegistrationModel = RegistrationUseCase.registerUser(new Register(Email, Name), new Auth(Email, Password));
        if (internetConnected) {
            firebaseAuthImpl.register(new Register(Email, Name), new Auth(Email, Password));
            firebaseAuthImpl.getDmRegistrationMutableLiveData().observe(owner, registrationModel -> {
                Log.d(TAG, "registrationValidation: firebaseAuthImpl.getDmRegistrationMutableLiveData invoked with isRegistrationSuccess as " + registrationModel.isRegistrationSuccess());
                isRegisterSuccess.setValue(new StatusHelperModel(registrationModel.isRegistrationSuccess(), registrationModel.getMessage()));
                if (!registrationModel.isRegistrationSuccess()) return;
            });
        }
    }
}

