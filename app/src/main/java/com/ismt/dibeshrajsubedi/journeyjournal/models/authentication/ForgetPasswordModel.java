package com.ismt.dibeshrajsubedi.journeyjournal.models.authentication;

import com.ismt.dibeshrajsubedi.journeyjournal.dao.authentication.ResetDAO;
import com.ismt.dibeshrajsubedi.journeyjournal.dao.helper.StatusHelperDAO;

/**
 * Project JourneyJournal with package com.ismt.dibeshrajsubedi.journeyjournal.models.authentication was
 * Created by Dibesh Raj Subedi on 3/21/2022.
 */
public class ForgetPasswordModel extends StatusHelperDAO {
    private ResetDAO resetDAO;

    public ForgetPasswordModel(boolean isSuccess) {
        super(isSuccess);
    }

    public ForgetPasswordModel(boolean isSuccess, String message) {
        super(isSuccess, message);
    }

    public ResetDAO getResetDAO() {
        return resetDAO;
    }

    public void setResetDAO(ResetDAO resetDAO) {
        this.resetDAO = resetDAO;
    }
}
