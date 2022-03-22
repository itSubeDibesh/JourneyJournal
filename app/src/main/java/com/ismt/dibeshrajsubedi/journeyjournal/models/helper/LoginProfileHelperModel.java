package com.ismt.dibeshrajsubedi.journeyjournal.models.helper;

import com.ismt.dibeshrajsubedi.journeyjournal.dao.authentication.register.RegisterDetailsDAO;
import com.ismt.dibeshrajsubedi.journeyjournal.models.authentication.LoginModel;

/**
 * Project JourneyJournal with package com.ismt.dibeshrajsubedi.journeyjournal.models.helper was
 * Created by Dibesh Raj Subedi on 3/20/2022.
 */
public class LoginProfileHelperModel extends LoginModel {

    private RegisterDetailsDAO registerDetailsDAO;

    public LoginProfileHelperModel(boolean isSuccess) {
        super(isSuccess);
    }

    public LoginProfileHelperModel(boolean isSuccess, String message) {
        super(isSuccess, message);
    }

    public RegisterDetailsDAO getRegisterDetailsDAO() {
        return registerDetailsDAO;
    }

    public void setRegisterDetailsDAO(RegisterDetailsDAO registerDetailsDAO) {
        this.registerDetailsDAO = registerDetailsDAO;
    }
}
