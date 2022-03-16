package com.ismt.dibeshrajsubedi.journeyjournal.data.sources.authentication.registration;

import com.ismt.dibeshrajsubedi.journeyjournal.domain.classes.authentication.CRegistration;
import com.ismt.dibeshrajsubedi.journeyjournal.domain.models.authentication.DMRegistration;

/**
 * Project JourneyJournal with package com.ismt.dibeshrajsubedi.journeyjournal.data.sources.authentication.registration was
 * Created by Dibesh Raj Subedi on 3/15/2022.
 */
public interface LSRegistration {
    DMRegistration registerUserLocal(CRegistration CRegistration);
}
