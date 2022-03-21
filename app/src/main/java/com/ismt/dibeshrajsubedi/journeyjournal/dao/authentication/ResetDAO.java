package com.ismt.dibeshrajsubedi.journeyjournal.dao.authentication;

import com.ismt.dibeshrajsubedi.journeyjournal.helper.InputValidationHelper;

/**
 * Project JourneyJournal with package com.ismt.dibeshrajsubedi.journeyjournal.dao.authentication was
 * Created by Dibesh Raj Subedi on 3/20/2022.
 */
public class ResetDAO extends InputValidationHelper {
    private final String Email;

    public ResetDAO(String email) {
        Email = email;
    }

    public String getEmail() {
        return Email;
    }
}
