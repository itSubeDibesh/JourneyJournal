package com.ismt.dibeshrajsubedi.journeyjournal.domain.use_cases;

import com.ismt.dibeshrajsubedi.journeyjournal.domain.classes.Auth;
import com.ismt.dibeshrajsubedi.journeyjournal.domain.classes.Register;
import com.ismt.dibeshrajsubedi.journeyjournal.domain.models.RegistrationModel;
import com.ismt.dibeshrajsubedi.journeyjournal.domain.repository.RegistrationRepo;

/**
 * Project JourneyJournal with package com.ismt.dibeshrajsubedi.journeyjournal.domain.use_cases.authentication was
 * Created by Dibesh Raj Subedi on 3/15/2022.
 */
public class RegistrationUseCase {
    private final RegistrationRepo RegistrationRepo;

    public RegistrationUseCase(RegistrationRepo RegistrationRepo) {
        this.RegistrationRepo = RegistrationRepo;
    }

    public RegistrationModel registerUser(Register Register, Auth Auth) {
        return RegistrationRepo.registerUser(Register, Auth);
    }
}
