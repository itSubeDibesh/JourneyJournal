package com.ismt.dibeshrajsubedi.journeyjournal.domain.models;

import com.ismt.dibeshrajsubedi.journeyjournal.domain.classes.Register;

/**
 * Project JourneyJournal with package com.ismt.dibeshrajsubedi.journeyjournal.domain.models.authentication was
 * Created by Dibesh Raj Subedi on 3/15/2022.
 */
public class RegistrationModel {
    private final boolean isRegistrationSuccess;
    private final String message;
    private Register registration;

    public RegistrationModel(boolean isRegistrationSuccess, String message) {
        this.isRegistrationSuccess = isRegistrationSuccess;
        this.message = message;
    }

    public boolean isRegistrationSuccess() {
        return isRegistrationSuccess;
    }

    public String getMessage() {
        return message;
    }

    public Register getRegistration() {
        return registration;
    }

    public void setRegistration(Register registration) {
        this.registration = registration;
    }
}
