package com.ismt.dibeshrajsubedi.journeyjournal.dao.home;


import com.google.firebase.database.annotations.Nullable;
import com.ismt.dibeshrajsubedi.journeyjournal.helper.InputValidationHelper;

import java.io.Serializable;

/**
 * Project JourneyJournal with package com.ismt.dibeshrajsubedi.journeyjournal.models was
 * Created by Dibesh Raj Subedi on 3/15/2022.
 */
public class JourneyDAO extends InputValidationHelper implements Serializable{
    private final String journeyAuthor;
    private final String journeyTitle;
    private final String journeyDate;
    @Nullable
    private String imageUri;
    @Nullable
    private LocationDAO locationDAO;
    private final String journeyDescription;

    public JourneyDAO() {
        this(null,null,null,null,null,null);
    }

    public JourneyDAO(String journeyAuthor, String journeyTitle, String journeyDate, String imageUri, LocationDAO locationDAO, String journeyDescription) {
        this.journeyAuthor = journeyAuthor;
        this.journeyTitle = journeyTitle;
        this.journeyDate = journeyDate;
        this.imageUri = imageUri;
        this.locationDAO = locationDAO;
        this.journeyDescription = journeyDescription;
    }

    public String getJourneyAuthor() {
        return journeyAuthor;
    }

    public String getJourneyTitle() {
        return journeyTitle;
    }

    public String getJourneyDate() {
        return journeyDate;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public LocationDAO getLocationDAO() {
        return locationDAO;
    }

    public void setLocationDAO(LocationDAO locationDAO) {
        this.locationDAO = locationDAO;
    }

    public String getJourneyDescription() {
        return journeyDescription;
    }
}
