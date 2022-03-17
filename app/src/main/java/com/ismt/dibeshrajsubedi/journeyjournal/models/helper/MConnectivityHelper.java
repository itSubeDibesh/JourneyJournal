package com.ismt.dibeshrajsubedi.journeyjournal.models.helper;

/**
 * Project JourneyJournal with package com.ismt.dibeshrajsubedi.journeyjournal.models.helper was
 * Created by Dibesh Raj Subedi on 3/17/2022.
 */
public class MConnectivityHelper {
    private final boolean status;
    private final String message;

    public MConnectivityHelper(boolean status, String message) {
        this.status = status;
        this.message = message;
    }

    public boolean getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}

