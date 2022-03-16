package com.ismt.dibeshrajsubedi.journeyjournal.domain.classes.authentication;

/**
 * Project JourneyJournal with package com.ismt.dibeshrajsubedi.journeyjournal.domain.classes.authentication was
 * Created by Dibesh Raj Subedi on 3/15/2022.
 */
public class DMCRegistration {
    private final String Name;
    private final String Email;
    private final String Password;

    public DMCRegistration(String name, String email, String password) {
        Name = name;
        Email = email;
        Password = password;
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
}
