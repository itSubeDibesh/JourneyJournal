package com.ismt.dibeshrajsubedi.journeyjournal.data.implementations.authentication;

import com.ismt.dibeshrajsubedi.journeyjournal.data.sources.authentication.registration.LSRegistration;
import com.ismt.dibeshrajsubedi.journeyjournal.data.sources.authentication.registration.RSRegistration;
import com.ismt.dibeshrajsubedi.journeyjournal.domain.classes.authentication.DMCRegistration;
import com.ismt.dibeshrajsubedi.journeyjournal.domain.models.authentication.DMRegistration;
import com.ismt.dibeshrajsubedi.journeyjournal.domain.repository.authentication.RRegistration;

/**
 * Project JourneyJournal with package com.ismt.dibeshrajsubedi.journeyjournal.data.implementations.authentication was
 * Created by Dibesh Raj Subedi on 3/15/2022.
 */
public class RIRegistration implements RRegistration {

    private final RSRegistration RSRegistration;
    private final LSRegistration LSRegistration;

    public RIRegistration(RSRegistration RSRegistration, LSRegistration LSRegistration) {
        this.RSRegistration = RSRegistration;
        this.LSRegistration = LSRegistration;
    }

    @Override
    public DMRegistration registerUser(DMCRegistration DMCRegistration) {
        // If Internet is working Remote else Local
        return RSRegistration.isInternetConnected() ? RSRegistration.registerUserRemote(DMCRegistration) : LSRegistration.registerUserLocal(DMCRegistration);
    }
}
