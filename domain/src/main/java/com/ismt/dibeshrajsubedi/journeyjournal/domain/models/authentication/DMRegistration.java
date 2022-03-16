package com.ismt.dibeshrajsubedi.journeyjournal.domain.models.authentication;

import com.ismt.dibeshrajsubedi.journeyjournal.domain.classes.authentication.CRegistration;

/**
 * Project JourneyJournal with package com.ismt.dibeshrajsubedi.journeyjournal.domain.models.authentication was
 * Created by Dibesh Raj Subedi on 3/15/2022.
 */
public class DMRegistration {
    private final boolean isRegistrationSuccess;
    private final String message;
    private CRegistration registration;

    public DMRegistration(boolean isRegistrationSuccess, String message) {
        this.isRegistrationSuccess = isRegistrationSuccess;
        this.message = message;
    }

    public boolean isRegistrationSuccess() {
        return isRegistrationSuccess;
    }

    public String getMessage() {
        return message;
    }

    public CRegistration getRegistration() {
        return registration;
    }

    public void setRegistration(CRegistration registration) {
        this.registration = registration;
    }
}
