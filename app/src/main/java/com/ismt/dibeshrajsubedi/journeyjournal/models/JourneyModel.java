package com.ismt.dibeshrajsubedi.journeyjournal.models;

/**
 * Project JourneyJournal with package com.ismt.dibeshrajsubedi.journeyjournal.models was
 * Created by Dibesh Raj Subedi on 3/15/2022.
 */
public class JourneyModel {
    private final String journeyTitle;
    private final String journeyDate;
    private final String journeyAddress;
    private final int journeyImageId;
    private final String journeyDescription;

    public String getJourneyTitle() {
        return journeyTitle;
    }

    public String getJourneyDate() {
        return journeyDate;
    }

    public String getJourneyAddress() {
        return journeyAddress;
    }

    public int getJourneyImageId() {
        return journeyImageId;
    }

    public String getJourneyDescription() {
        return journeyDescription;
    }

    public JourneyModel(String journeyTitle, String journeyDate, String journeyAddress, int journeyImageId, String journeyDescription) {
        this.journeyTitle = journeyTitle;
        this.journeyDate = journeyDate;
        this.journeyAddress = journeyAddress;
        this.journeyImageId = journeyImageId;
        this.journeyDescription = journeyDescription;
    }
}
