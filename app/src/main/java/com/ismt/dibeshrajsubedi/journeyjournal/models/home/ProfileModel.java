package com.ismt.dibeshrajsubedi.journeyjournal.models.home;

import com.ismt.dibeshrajsubedi.journeyjournal.dao.authentication.register.RegisterDetailsDAO;
import com.ismt.dibeshrajsubedi.journeyjournal.dao.helper.StatusHelperDAO;

/**
 * Project JourneyJournal with package com.ismt.dibeshrajsubedi.journeyjournal.models.home was
 * Created by Dibesh Raj Subedi on 3/22/2022.
 */
public class ProfileModel extends StatusHelperDAO {
    private RegisterDetailsDAO registerDetailsDAO;

    public ProfileModel(boolean isSuccess) {
        super(isSuccess);
    }

    public ProfileModel(boolean isSuccess, String message) {
        super(isSuccess, message);
    }

    public RegisterDetailsDAO getRegisterDetailsDAO() {
        return registerDetailsDAO;
    }

    public void setRegisterDetailsDAO(RegisterDetailsDAO registerDetailsDAO) {
        this.registerDetailsDAO = registerDetailsDAO;
    }
}

