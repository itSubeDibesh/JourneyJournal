package com.ismt.dibeshrajsubedi.journeyjournal.frameworks.journeyjournal.authentication.registration;

import com.ismt.dibeshrajsubedi.journeyjournal.data.sources.authentication.registration.RSRegistration;
import com.ismt.dibeshrajsubedi.journeyjournal.domain.classes.authentication.DMCRegistration;
import com.ismt.dibeshrajsubedi.journeyjournal.domain.models.authentication.DMRegistration;

/**
 * Project JourneyJournal with package com.ismt.dibeshrajsubedi.journeyjournal.frameworks.journeyjournal.authentication.registration was
 * Created by Dibesh Raj Subedi on 3/15/2022.
 */
public class RSIRegister implements RSRegistration {

    @Override
    public boolean isInternetConnected() {
        return false;
    }

    @Override
    public DMRegistration registerUserRemote(DMCRegistration DMCRegistration) {
        return null;
    }
}
