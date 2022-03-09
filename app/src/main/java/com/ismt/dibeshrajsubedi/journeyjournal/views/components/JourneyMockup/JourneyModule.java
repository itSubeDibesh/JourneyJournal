package com.ismt.dibeshrajsubedi.journeyjournal.views.components.JourneyMockup;

/**
 * Project JourneyJournal with package com.ismt.dibeshrajsubedi.journeyjournal.views.components was
 * Created by Dibesh Raj Subedi on 3/9/2022.
 */
public class JourneyModule {
    private String journeyTitle;
    private String journeyCreatedDate;
    private int journeyImageResId, journeyDescription;

    public JourneyModule(String journeyTitle, int journeyDescription, String journeyCreatedDate, int journeyImageResId) {
        this.journeyTitle = journeyTitle;
        this.journeyDescription = journeyDescription;
        this.journeyCreatedDate = journeyCreatedDate;
        this.journeyImageResId = journeyImageResId;
    }

    public String getJourneyTitle() {
        return journeyTitle;
    }

    public int getJourneyDescription() {
        return journeyDescription;
    }

    public String getJourneyCreatedDate() {
        return journeyCreatedDate;
    }

    public int getJourneyImageResId() {
        return journeyImageResId;
    }

}
