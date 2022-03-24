package com.ismt.dibeshrajsubedi.journeyjournal.dao.home;

import java.io.Serializable;

/**
 * Project JourneyJournal with package com.ismt.dibeshrajsubedi.journeyjournal.dao.home was
 * Created by Dibesh Raj Subedi on 3/21/2022.
 */
public class LocationDAO implements Serializable {
    private final Double latitude;
    private final Double longitude;
    private final String address;

    public LocationDAO(Double latitude, Double longitude, String address) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
    }

    public LocationDAO() {
        this(null, null, null);
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public String getAddress() {
        return address;
    }
}
