package com.ismt.dibeshrajsubedi.journeyjournal.view_models.authentication;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

import com.ismt.dibeshrajsubedi.journeyjournal.R;
import com.ismt.dibeshrajsubedi.journeyjournal.dao.authentication.register.RegisterFormDAO;
import com.ismt.dibeshrajsubedi.journeyjournal.dao.helper.StatusHelperDAO;
import com.ismt.dibeshrajsubedi.journeyjournal.models.helper.LoginProfileHelperModel;
import com.ismt.dibeshrajsubedi.journeyjournal.view_models.helper.JJ_AuthViewModel;

/**
 * Project JourneyJournal with package com.ismt.dibeshrajsubedi.journeyjournal.view_models.authentication.register was
 * Created by Dibesh Raj Subedi on 3/15/2022.
 */
public class RegisterViewModel extends JJ_AuthViewModel {
    private final String TAG = "JJ_RegisterViewModel";
    public final MutableLiveData<StatusHelperDAO> isNameInValid = new MutableLiveData<>();
    public final MutableLiveData<StatusHelperDAO> isRetypePasswordNotMatched = new MutableLiveData<>();
    public final MutableLiveData<LoginProfileHelperModel> isRegisterSuccess = new MutableLiveData<>();

    public RegisterViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * Validates Registration and sets MutableLivedata to be observed
     *
     * @param registerFormDAO   RegisterFormDAO
     * @param internetConnected boolean
     * @param owner             LifecycleOwner
     */
    public void registrationValidation(RegisterFormDAO registerFormDAO, boolean internetConnected, LifecycleOwner owner) {
        Log.d(TAG, "registrationValidation: Triggered registrationValidation with internetConnected as " + internetConnected);

        // Name Validation Checks - [Empty Check]
        String Name = registerFormDAO.getName();
        if (registerFormDAO.isNullOrEmpty(Name)) {
            isNameInValid.setValue(new StatusHelperDAO(true, getString(R.string.error_invalid_name)));
            return;
        } else isNameInValid.setValue(new StatusHelperDAO(false));

        // Email Validation Checks
        String Email = registerFormDAO.getEmail();
        EmailInValid(Email);

        // Password Validation Check
        String Password = registerFormDAO.getPassword();
        PasswordInValid(Password);

        // Password Matched Check - [EmptyCheck, Password Pattern Check, Match Check]
        String RetypePassword = registerFormDAO.getRetypePassword();
        if (registerFormDAO.isNullOrEmpty(RetypePassword)) {
            isRetypePasswordNotMatched.setValue(new StatusHelperDAO(true, getString(R.string.error_empty_retype_password)));
            return;
        } else if (!registerFormDAO.strLenGreaterThan(RetypePassword, 6) || !registerFormDAO.strLenLesserThan(RetypePassword, 20)) {
            isRetypePasswordNotMatched.setValue(new StatusHelperDAO(true, getString(R.string.error_password_length)));
            return;
        } else if (!registerFormDAO.isValidPassword(RetypePassword, true)) {
            isRetypePasswordNotMatched.setValue(new StatusHelperDAO(true, getString(R.string.error_invalid_password_format)));
            return;
        } else if (!registerFormDAO.strIsMatch(Password, RetypePassword)) {
            isRetypePasswordNotMatched.setValue(new StatusHelperDAO(true, getString(R.string.error_password_not_match)));
            return;
        } else isRetypePasswordNotMatched.setValue(new StatusHelperDAO(false));

        // TODO : Implement Offline Code Using Room
        if (internetConnected) {
            firebaseAuthImpl.register(registerFormDAO);
            firebaseAuthImpl.getRegisterModelMutableLiveData().observe(owner, regModel -> {
                Log.d(TAG, "registrationValidation: firebaseAuthImpl.getDmRegistrationMutableLiveData invoked with isRegistrationSuccess as " + regModel.getStatus());
                isRegisterSuccess.setValue(regModel);
            });
        }
    }
}

