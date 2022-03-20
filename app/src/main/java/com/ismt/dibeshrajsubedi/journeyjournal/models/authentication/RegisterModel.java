package com.ismt.dibeshrajsubedi.journeyjournal.models.authentication;

import com.ismt.dibeshrajsubedi.journeyjournal.dao.authentication.register.RegisterDetailsDAO;

/**
 * Project JourneyJournal with package com.ismt.dibeshrajsubedi.journeyjournal.models.authentication.register was
 * Created by Dibesh Raj Subedi on 3/20/2022.
 */
public class RegisterModel {
    private final boolean isRegistrationSuccess;
    private final String message;
    private RegisterDetailsDAO registerDetailsDAO;

    public RegisterModel(boolean isRegistrationSuccess, String message) {
        this.isRegistrationSuccess = isRegistrationSuccess;
        this.message = message;
    }

    public boolean isRegistrationSuccess() {
        return isRegistrationSuccess;
    }

    public String getMessage() {
        return message;
    }

    public RegisterDetailsDAO getRegistration() {
        return registerDetailsDAO;
    }

    public void setRegistration(RegisterDetailsDAO registerDetailsDAO) {
        this.registerDetailsDAO = registerDetailsDAO;
    }
}
