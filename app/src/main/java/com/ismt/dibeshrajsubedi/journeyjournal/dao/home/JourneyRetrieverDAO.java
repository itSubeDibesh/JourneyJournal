package com.ismt.dibeshrajsubedi.journeyjournal.dao.home;

import java.io.Serializable;

/**
 * Project JourneyJournal with package com.ismt.dibeshrajsubedi.journeyjournal.dao.home was
 * Created by Dibesh Raj Subedi on 3/24/2022.
 */
public class JourneyRetrieverDAO implements Serializable {
    private final String Key;
    private final JourneyDAO journey;

    public JourneyRetrieverDAO(String key, JourneyDAO journey) {
        Key = key;
        this.journey = journey;
    }

    public String getKey() {
        return Key;
    }

    public JourneyDAO getJourney() {
        return journey;
    }
}
