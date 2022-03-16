package com.ismt.dibeshrajsubedi.journeyjournal.helper;

/**
 * Project JourneyJournal with package com.ismt.dibeshrajsubedi.journeyjournal.helper was
 * Created by Dibesh Raj Subedi on 3/16/2022.
 */
public class MStatusHelper {

    private final int message;
    private final boolean status;

    public MStatusHelper(boolean status) {
        this(status, 0);
    }

    public MStatusHelper(boolean status, int message) {
        this.message = message;
        this.status = status;
    }

    public int getMessage() {
        return message;
    }

    public boolean getStatus() {
        return status;
    }
}
