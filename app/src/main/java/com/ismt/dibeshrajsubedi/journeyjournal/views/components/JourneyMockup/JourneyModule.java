package com.ismt.dibeshrajsubedi.journeyjournal.views.components.JourneyMockup;

import java.io.Serializable;

/**
 * Project JourneyJournal with package com.ismt.dibeshrajsubedi.journeyjournal.views.components was
 * Created by Dibesh Raj Subedi on 3/9/2022.
 */
public class JourneyModule implements Serializable {
    private final String journeyTitle;
    private final String journeyCreatedDate;
    private final String address;
    private final int journeyImageResId;
    private final int journeyDescription;

    public JourneyModule(String journeyTitle, int journeyDescription, String address, String journeyCreatedDate, int journeyImageResId) {
        this.journeyTitle = journeyTitle;
        this.journeyDescription = journeyDescription;
        this.address = address;
        this.journeyCreatedDate = journeyCreatedDate;
        this.journeyImageResId = journeyImageResId;
    }

    public String getJourneyTitle() {
        return journeyTitle;
    }

    public String getAddress() { return address; }

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
