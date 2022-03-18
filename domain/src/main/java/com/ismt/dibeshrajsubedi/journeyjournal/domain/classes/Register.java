package com.ismt.dibeshrajsubedi.journeyjournal.domain.classes;

/**
 * Project JourneyJournal with package com.ismt.dibeshrajsubedi.journeyjournal.domain.classes.authentication was
 * Created by Dibesh Raj Subedi on 3/15/2022.
 */
public class Register {
    private final String email;
    private final String displayName;

    public Register(String email, String displayName) {
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
