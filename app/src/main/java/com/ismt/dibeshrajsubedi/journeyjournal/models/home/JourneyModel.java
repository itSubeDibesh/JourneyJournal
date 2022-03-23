package com.ismt.dibeshrajsubedi.journeyjournal.models.home;

import com.ismt.dibeshrajsubedi.journeyjournal.dao.helper.StatusHelperDAO;
import com.ismt.dibeshrajsubedi.journeyjournal.dao.home.JourneyDAO;

/**
 * Project JourneyJournal with package com.ismt.dibeshrajsubedi.journeyjournal.models.home was
 * Created by Dibesh Raj Subedi on 3/23/2022.
 */
public class JourneyModel extends StatusHelperDAO {
    private JourneyDAO journeyDAO;

    public JourneyModel(boolean status) {
        super(status);
    }

    public JourneyModel(boolean status, String message) {
        super(status, message);
    }

    public JourneyDAO getJourneyDAO() {
        return journeyDAO;
    }

    public void setJourneyDAO(JourneyDAO journeyDAO) {
        this.journeyDAO = journeyDAO;
    }
}
