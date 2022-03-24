package com.ismt.dibeshrajsubedi.journeyjournal.dao.home;

/**
 * Project JourneyJournal with package com.ismt.dibeshrajsubedi.journeyjournal.dao.home was
 * Created by Dibesh Raj Subedi on 3/24/2022.
 */
public class JourneyRetrieverDAO {
    private JourneyDAO journey;

    public JourneyRetrieverDAO() {
    }

    public JourneyRetrieverDAO(JourneyDAO journey) {
        this.journey = journey;
    }

    public JourneyDAO getJourney() {
        return journey;
    }

    public void setJourney(JourneyDAO journey) {
        this.journey = journey;
    }
}
