package com.ismt.dibeshrajsubedi.journeyjournal.models.helper;

/**
 * Project JourneyJournal with package com.ismt.dibeshrajsubedi.journeyjournal.models.helper was
 * Created by Dibesh Raj Subedi on 3/16/2022.
 */
public class StatusHelperModel {

    private final String message;
    private final boolean status;

    public StatusHelperModel(boolean status) {
        this(status, null);
    }

    public StatusHelperModel(boolean status, String message) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public boolean getStatus() {
        return status;
    }
}
