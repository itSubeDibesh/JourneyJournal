package com.ismt.dibeshrajsubedi.journeyjournal.view_models.authentication.register;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ismt.dibeshrajsubedi.journeyjournal.R;
import com.ismt.dibeshrajsubedi.journeyjournal.data.implementations.authentication.RIRegistration;
import com.ismt.dibeshrajsubedi.journeyjournal.domain.classes.authentication.DMCRegistration;
import com.ismt.dibeshrajsubedi.journeyjournal.domain.models.authentication.DMRegistration;
import com.ismt.dibeshrajsubedi.journeyjournal.domain.use_cases.authentication.UCRegistration;
import com.ismt.dibeshrajsubedi.journeyjournal.frameworks.journeyjournal.authentication.registration.LSIRegister;
import com.ismt.dibeshrajsubedi.journeyjournal.frameworks.journeyjournal.authentication.registration.RSIRegister;
import com.ismt.dibeshrajsubedi.journeyjournal.models.authentication.MRegistration;
import com.ismt.dibeshrajsubedi.journeyjournal.models.helper.MStatusHelper;

/**
 * Project JourneyJournal with package com.ismt.dibeshrajsubedi.journeyjournal.view_models.authentication.register was
 * Created by Dibesh Raj Subedi on 3/15/2022.
 */
public class VMRegister extends ViewModel {
    public final MutableLiveData<MStatusHelper> isNameInValid = new MutableLiveData<>();
    public final MutableLiveData<MStatusHelper> isEmailInValid = new MutableLiveData<>();
    public final MutableLiveData<MStatusHelper> isPasswordInValid = new MutableLiveData<>();
    public final MutableLiveData<MStatusHelper> isRetypePasswordNotMatched = new MutableLiveData<>();
    public final MutableLiveData<MStatusHelper> isRegisterSuccess = new MutableLiveData<>();

    public void registrationValidation(MRegistration MRegistration, boolean internetConnected) {
        // Extracting And Passing elements
        UCRegistration UCRegistration = new UCRegistration(new RIRegistration(new RSIRegister(internetConnected), new LSIRegister()));

        // Name Validation Checks - [Empty Check]
        String Name = MRegistration.getName();
        if (MRegistration.isNullOrEmpty(Name))
            isNameInValid.setValue(new MStatusHelper(true, R.string.error_invalid_name));
        else isNameInValid.setValue(new MStatusHelper(false));

        // Email Validation Checks - [Empty Check, Email Pattern Check]
        String Email = MRegistration.getEmail();
        if (MRegistration.isNullOrEmpty(Email))
            isEmailInValid.setValue(new MStatusHelper(true, R.string.error_empty_email));
        else if (!MRegistration.isValidEmail(Email))
            isEmailInValid.setValue(new MStatusHelper(true, R.string.error_invalid_email));
        else isEmailInValid.setValue(new MStatusHelper(false));

        // Password Validation Check - [EmptyCheck, Password Pattern Check]
        String Password = MRegistration.getPassword();
        if (MRegistration.isNullOrEmpty(Password))
            isPasswordInValid.setValue(new MStatusHelper(true, R.string.error_empty_password));
        else if (MRegistration.isValidPassword(Password, false))
            isPasswordInValid.setValue(new MStatusHelper(true, R.string.error_invalid_password_format));
        else if (!MRegistration.strLenGreaterThan(Password, 6) || !MRegistration.strLenLesserThan(Password, 20))
            isPasswordInValid.setValue(new MStatusHelper(true, R.string.error_password_length));
        else isPasswordInValid.setValue(new MStatusHelper(false));

        // Password Matched Check - [EmptyCheck, Password Pattern Check, Match Check]
        String RetypePassword = MRegistration.getRetypePassword();
        if (MRegistration.isNullOrEmpty(RetypePassword))
            isRetypePasswordNotMatched.setValue(new MStatusHelper(true, R.string.error_empty_retype_password));
        else if (!MRegistration.strLenGreaterThan(RetypePassword, 6) || !MRegistration.strLenLesserThan(RetypePassword, 20))
            isRetypePasswordNotMatched.setValue(new MStatusHelper(true, R.string.error_password_length));
        else if (MRegistration.isValidPassword(RetypePassword, false))
            isRetypePasswordNotMatched.setValue(new MStatusHelper(true, R.string.error_invalid_password_format));
        else if (!MRegistration.strIsMatch(Password, RetypePassword))
            isRetypePasswordNotMatched.setValue(new MStatusHelper(true, R.string.error_password_not_match));
        else isRetypePasswordNotMatched.setValue(new MStatusHelper(false));

        // Final Empty Check to Avoid Null Crash
        if (!MRegistration.isNullOrEmpty(Name) && !MRegistration.isNullOrEmpty(Email) && !MRegistration.isNullOrEmpty(Password)) {
            // Trigger Registration UseCase
            DMRegistration DMRegistration = UCRegistration.registerUser(new DMCRegistration(Name, Email, Password));
            // Registration Success trigger
            isRegisterSuccess.setValue(new MStatusHelper(DMRegistration.isRegistrationSuccess(), R.string.success_register));
        }
    }
}

