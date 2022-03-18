package com.ismt.dibeshrajsubedi.journeyjournal.data.implementations.authentication;

import com.ismt.dibeshrajsubedi.journeyjournal.data.sources.registration.RegistrationLocalSource;
import com.ismt.dibeshrajsubedi.journeyjournal.data.sources.registration.RegistrationRemoteSource;
import com.ismt.dibeshrajsubedi.journeyjournal.domain.classes.Auth;
import com.ismt.dibeshrajsubedi.journeyjournal.domain.classes.Register;
import com.ismt.dibeshrajsubedi.journeyjournal.domain.models.RegistrationModel;
import com.ismt.dibeshrajsubedi.journeyjournal.domain.repository.RegistrationRepo;

/**
 * Project JourneyJournal with package com.ismt.dibeshrajsubedi.journeyjournal.data.implementations.authentication was
 * Created by Dibesh Raj Subedi on 3/15/2022.
 */
public class RegistrationRepoImpl implements RegistrationRepo {
    private final RegistrationRemoteSource RSRegistration;
    private final RegistrationLocalSource RegistrationLocalSource;

    public RegistrationRepoImpl(RegistrationRemoteSource RSRegistration, RegistrationLocalSource RegistrationLocalSource) {
        this.RSRegistration = RSRegistration;
        this.RegistrationLocalSource = RegistrationLocalSource;
    }

    @Override
    public RegistrationModel registerUser(Register Register, Auth Auth) {
        // If Internet is working Remote else Local
        return RSRegistration.isInternetConnected() ? RSRegistration.registerUserRemote(Register, Auth) : RegistrationLocalSource.registerUserLocal(Register);
    }
}
