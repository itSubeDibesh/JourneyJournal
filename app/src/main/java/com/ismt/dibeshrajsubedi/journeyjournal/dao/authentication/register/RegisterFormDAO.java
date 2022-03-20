package com.ismt.dibeshrajsubedi.journeyjournal.dao.authentication.register;

import com.ismt.dibeshrajsubedi.journeyjournal.helper.InputValidationHelper;

/**
 * Project JourneyJournal with package com.ismt.dibeshrajsubedi.journeyjournal.models was
 * Created by Dibesh Raj Subedi on 3/15/2022.
 */
public class RegisterFormDAO extends InputValidationHelper {

    private final String Name;
    private final String Email;
    private final String Password;
    private final String RetypePassword;

    public RegisterFormDAO(String name, String email, String password, String retypePassword) {
        Name = name.trim();
        Email = email.trim();
        Password = password.trim();
        RetypePassword = retypePassword.trim();
    }

    public String getName() {
        return Name;
    }

    public String getEmail() {
        return Email;
    }

    public String getPassword() {
        return Password;
    }

    public String getRetypePassword() {
        return RetypePassword;
    }
}
