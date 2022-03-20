package com.ismt.dibeshrajsubedi.journeyjournal.dao.authentication.register;

import com.ismt.dibeshrajsubedi.journeyjournal.helper.InputValidationHelper;

/**
 * Project JourneyJournal with package com.ismt.dibeshrajsubedi.journeyjournal.dao.authentication.register was
 * Created by Dibesh Raj Subedi on 3/20/2022.
 */
public class RegisterDetailsDAO extends InputValidationHelper {
    private final String email;
    private final String displayName;

    public RegisterDetailsDAO(String email, String displayName) {
        this.email = email;
        this.displayName = displayName;
    }

    public String getEmail() {
        return email;
    }

    public String getDisplayName() {
        return displayName;
    }
}
