package com.ismt.dibeshrajsubedi.journeyjournal.dao.home;

/**
 * Project JourneyJournal with package com.ismt.dibeshrajsubedi.journeyjournal.dao.home was
 * Created by Dibesh Raj Subedi on 3/21/2022.
 */
public class LocationDAO {
    private final String latitude;
    private final String longitude;
    private final String address;

    public LocationDAO(String latitude, String longitude, String address) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getAddress() {
        return address;
    }
}
