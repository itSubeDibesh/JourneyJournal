package com.ismt.dibeshrajsubedi.journeyjournal.view_models.authentication.register;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ismt.dibeshrajsubedi.journeyjournal.data.implementations.authentication.RIRegistration;
import com.ismt.dibeshrajsubedi.journeyjournal.domain.classes.authentication.CRegistration;
import com.ismt.dibeshrajsubedi.journeyjournal.domain.models.authentication.DMRegistration;
import com.ismt.dibeshrajsubedi.journeyjournal.domain.use_cases.authentication.UCRegistration;
import com.ismt.dibeshrajsubedi.journeyjournal.frameworks.journeyjournal.authentication.registration.LSIRegister;
import com.ismt.dibeshrajsubedi.journeyjournal.frameworks.journeyjournal.authentication.registration.RSIRegister;
import com.ismt.dibeshrajsubedi.journeyjournal.models.authentication.MRegistration;

/**
 * Project JourneyJournal with package com.ismt.dibeshrajsubedi.journeyjournal.view_models.authentication.register was
 * Created by Dibesh Raj Subedi on 3/15/2022.
 */
public class VMRegister extends ViewModel {

    final MutableLiveData<Boolean> isNameValid = new MutableLiveData<>();
    final MutableLiveData<Boolean> isEmailValid = new MutableLiveData<>();
    final MutableLiveData<Boolean> isPasswordValid = new MutableLiveData<>();
    final MutableLiveData<Boolean> isRetypePasswordMatched = new MutableLiveData<>();
    final MutableLiveData<Boolean> isRegisterSuccess = new MutableLiveData<>();

    private final UCRegistration UCRegistration = new UCRegistration(
            new RIRegistration(
                    new RSIRegister(),
                    new LSIRegister()
            )
    );

    public void registrationValidation(MRegistration MRegistration) {
        // Name Validation Checks - [Empty Check]
        String Name = MRegistration.getName();
        isNameValid.setValue(MRegistration.isNullOrEmpty(Name));
        // Email Validation Checks - [Empty Check, Email Pattern Check]
        String Email = MRegistration.getEmail();
        isEmailValid.setValue(!MRegistration.isNullOrEmpty(Email) && MRegistration.isValidEmail(Email));
        // Password Validation Check - [EmptyCheck, Password Pattern Check]
        String Password = MRegistration.getPassword();
        isPasswordValid.setValue(!MRegistration.isNullOrEmpty(Password) && MRegistration.isValidPassword(Password, false));
        // Password Matched Check - [EmptyCheck, Password Pattern Check, Match Check]
        String RetypePassword = MRegistration.getRetypePassword();
        isRetypePasswordMatched.setValue(!MRegistration.isNullOrEmpty(RetypePassword) && MRegistration.isValidPassword(RetypePassword, false) && MRegistration.strIsMatch(Password, RetypePassword));
        // Trigger Registration UseCase
        DMRegistration DMRegistration = UCRegistration.registerUser(new CRegistration(Name, Email, Password));
        // Registration Success trigger
        isRegisterSuccess.setValue(DMRegistration.isRegistrationSuccess());
    }
}
