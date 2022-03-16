package com.ismt.dibeshrajsubedi.journeyjournal.models.authentication;

import com.ismt.dibeshrajsubedi.journeyjournal.helper.InputValidationHelper;

/**
 * Project JourneyJournal with package com.ismt.dibeshrajsubedi.journeyjournal.models was
 * Created by Dibesh Raj Subedi on 3/15/2022.
 */
public class MRegistration extends InputValidationHelper {

    private final String Name;
    private final String Email;
    private final String Password;
    private final String RetypePassword;

    public MRegistration(String name, String email, String password, String retypePassword) {
        Name = name;
        Email = email;
        Password = password;
        RetypePassword = retypePassword;
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
