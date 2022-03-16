package com.ismt.dibeshrajsubedi.journeyjournal.domain.use_cases.authentication;

import com.ismt.dibeshrajsubedi.journeyjournal.domain.classes.authentication.CRegistration;
import com.ismt.dibeshrajsubedi.journeyjournal.domain.models.authentication.DMRegistration;
import com.ismt.dibeshrajsubedi.journeyjournal.domain.repository.authentication.RRegistration;

/**
 * Project JourneyJournal with package com.ismt.dibeshrajsubedi.journeyjournal.domain.use_cases.authentication was
 * Created by Dibesh Raj Subedi on 3/15/2022.
 */
public class UCRegistration {
    private final RRegistration RRegistration;

    public UCRegistration(RRegistration RRegistration) {
        this.RRegistration = RRegistration;
    }

    public DMRegistration registerUser(CRegistration CRegistration) {
        return RRegistration.registerUser(CRegistration);
    }
}
