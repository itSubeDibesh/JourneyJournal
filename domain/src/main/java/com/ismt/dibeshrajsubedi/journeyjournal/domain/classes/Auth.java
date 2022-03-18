package com.ismt.dibeshrajsubedi.journeyjournal.domain.classes;

/**
 * Project JourneyJournal with package com.ismt.dibeshrajsubedi.journeyjournal.domain.classes.authentication was
 * Created by Dibesh Raj Subedi on 3/18/2022.
 */
public class Auth {
    private final String email;
    private final String password;

    public Auth(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
