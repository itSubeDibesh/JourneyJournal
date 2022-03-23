package com.ismt.dibeshrajsubedi.journeyjournal.view_models.helper;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.ismt.dibeshrajsubedi.journeyjournal.R;
import com.ismt.dibeshrajsubedi.journeyjournal.dao.helper.StatusHelperDAO;
import com.ismt.dibeshrajsubedi.journeyjournal.helper.InputValidationHelper;
import com.ismt.dibeshrajsubedi.journeyjournal.repository.firebase.FirebaseAuthImpl;

/**
 * Project JourneyJournal with package com.ismt.dibeshrajsubedi.journeyjournal.view_models was
 * Created by Dibesh Raj Subedi on 3/19/2022.
 */
public class JJ_AuthViewModel extends AndroidViewModel {

    /**
     * Instantiating InputValidationHelper
     */
    private final InputValidationHelper validationHelper = new InputValidationHelper();

    /**
     * Instantiating isEmailInvalid MutableLiveDat as common live data on most of case
     */
    public final MutableLiveData<StatusHelperDAO> isEmailInValid = new MutableLiveData<>();

    /**
     * Instantiating isPasswordInValid MutableLiveDat as common live data on most of case
     */
    public final MutableLiveData<StatusHelperDAO> isPasswordInValid = new MutableLiveData<>();

    public JJ_AuthViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * Returns String Value from resource dataset
     *
     * @param res int
     * @return String
     */
    protected String getString(int res) {
        return getApplication().getString(res);
    }

    /**
     * Instantiating firebase Auth Implementation
     */
    protected final FirebaseAuthImpl firebaseAuthImpl = new FirebaseAuthImpl();

    /**
     * Validates Input and sets isEmailInValid MutableLiveData
     *
     * @param Email String
     */
    protected void EmailInValid(String Email) {
        // Email Validation Checks - [Empty Check, Email Pattern Check]
        if (validationHelper.isNullOrEmpty(Email)) {
            isEmailInValid.setValue(new StatusHelperDAO(true, getString(R.string.error_empty_email)));
            return;
        } else if (!validationHelper.isValidEmail(Email)) {
            isEmailInValid.setValue(new StatusHelperDAO(true, getString(R.string.error_invalid_email)));
            return;
        } else isEmailInValid.setValue(new StatusHelperDAO(false));
    }

    /**
     * Validates Input and sets isPasswordInValid MutableLiveData
     *
     * @param Password String
     */
    protected void PasswordInValid(String Password) {
        // Password Validation Check - [EmptyCheck, Password Pattern Check]
        if (validationHelper.isNullOrEmpty(Password)) {
            isPasswordInValid.setValue(new StatusHelperDAO(true, getString(R.string.error_empty_password)));
            return;
        } else if (!validationHelper.isValidPassword(Password, true)) {
            isPasswordInValid.setValue(new StatusHelperDAO(true, getString(R.string.error_invalid_password_format)));
            return;
        } else if (!validationHelper.strLenGreaterThan(Password, 6) || !validationHelper.strLenLesserThan(Password, 20)) {
            isPasswordInValid.setValue(new StatusHelperDAO(true, getString(R.string.error_password_length)));
            return;
        } else isPasswordInValid.setValue(new StatusHelperDAO(false));
    }
}
