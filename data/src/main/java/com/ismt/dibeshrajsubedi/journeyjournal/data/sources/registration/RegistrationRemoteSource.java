package com.ismt.dibeshrajsubedi.journeyjournal.data.sources.registration;

import com.ismt.dibeshrajsubedi.journeyjournal.domain.classes.Auth;
import com.ismt.dibeshrajsubedi.journeyjournal.domain.classes.Register;
import com.ismt.dibeshrajsubedi.journeyjournal.domain.models.RegistrationModel;

/**
 * Project JourneyJournal with package com.ismt.dibeshrajsubedi.journeyjournal.data.sources.registration was
 * Created by Dibesh Raj Subedi on 3/15/2022.
 */
public interface RegistrationRemoteSource {
    boolean isInternetConnected();

    RegistrationModel registerUserRemote(Register Register, Auth auth);
}
