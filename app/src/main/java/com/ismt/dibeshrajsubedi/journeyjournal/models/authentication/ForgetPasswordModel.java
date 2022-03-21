package com.ismt.dibeshrajsubedi.journeyjournal.models.authentication;

import com.ismt.dibeshrajsubedi.journeyjournal.dao.authentication.ResetDAO;

/**
 * Project JourneyJournal with package com.ismt.dibeshrajsubedi.journeyjournal.models.authentication was
 * Created by Dibesh Raj Subedi on 3/21/2022.
 */
public class ForgetPasswordModel {
    private final boolean isSuccess;
    private final String message;
    private ResetDAO resetDAO;

    public ForgetPasswordModel(boolean isSuccess) {
        this.isSuccess = isSuccess;
        this.message = null;
    }
    public ForgetPasswordModel(boolean isSuccess, String message) {
        this.isSuccess = isSuccess;
        this.message = message;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public String getMessage() {
        return message;
    }

    public ResetDAO getResetDAO() {
        return resetDAO;
    }

    public void setResetDAO(ResetDAO resetDAO) {
        this.resetDAO = resetDAO;
    }
}
