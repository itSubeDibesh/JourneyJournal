package com.ismt.dibeshrajsubedi.journeyjournal.dao.authentication;

import com.ismt.dibeshrajsubedi.journeyjournal.helper.InputValidationHelper;

/**
 * Project JourneyJournal with package com.ismt.dibeshrajsubedi.journeyjournal.models.authentication was
 * Created by Dibesh Raj Subedi on 3/19/2022.
 */
public class LoginDAO extends InputValidationHelper {
    private final String Email;
    private final String Password;

    public LoginDAO(String email, String password) {
        Email = email;
        Password = password;
    }

    public String getEmail() {
        return Email;
    }

    public String getPassword() {
        return Password;
    }
}
