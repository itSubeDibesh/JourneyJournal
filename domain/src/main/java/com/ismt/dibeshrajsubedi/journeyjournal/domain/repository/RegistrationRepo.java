package com.ismt.dibeshrajsubedi.journeyjournal.domain.repository;

import com.ismt.dibeshrajsubedi.journeyjournal.domain.classes.Auth;
import com.ismt.dibeshrajsubedi.journeyjournal.domain.classes.Register;
import com.ismt.dibeshrajsubedi.journeyjournal.domain.models.RegistrationModel;

/**
 * Project JourneyJournal with package com.ismt.dibeshrajsubedi.journeyjournal.domain.repository.authentication was
 * Created by Dibesh Raj Subedi on 3/15/2022.
 */
public interface RegistrationRepo {
    RegistrationModel registerUser(Register Register, Auth Auth);
}
